/*
 * This script defines functions used to handle the document images in
 * the DocDetail page.  It also creates variables  with the size of the
 * loaded image, information that can be used when the images is re-sized.
 */

var lstat = false  // boolean flag to make init() a singleton operation

var pImg;          // the current image
var pFile;         // the src for current page iamges
var imgHt;         // the current image height
var imgWd;         // the current image width
var imgDw;         // the current display width
var selTn;         // the current selected thumbnail
var inpts;         // the array of input elements
var cndx;          // counter for the array of input elements
var nlnk;          // ref to the document's nextlnk button
var nlnk2;         // ref to the document's nextlnk2 button
var plnk;          // ref to the document's prevlnk button
var plnk2;         // ref to the document's prevlnk2 button

var copyMsg = "WARNING: This material may be protected " +
              "by copyright law  (Title 17 U.S. Code)";

/* The default init function called on load. */
function init()
{
  if( lstat ) return;
  lstat = true;

  var tn = document.getElementById( "tnails" );
  this.inpts = tn.getElementsByTagName( "input" );

  this.nlnk = document.getElementById( "nextlnk" );
  this.nlnk2 = document.getElementById( "nextlnk2" );

  this.plnk = document.getElementById( "prevlnk" );
  this.plnk2 = document.getElementById( "prevlnk2" );

  cndx = 0;
  inpts[cndx].click();
}

/*
 * This function increases the display width (imgDw) by 150 pixels,
 * up to the actual size to the image.
 */
function sizeImgPane()
{
  var paneImage = document.getElementById( 'paneImg' );
  imgDw = Math.min( paneImage.width + 150, imgWd );
  paneImage.width = imgDw;
};

/*
 * This function sets the contents of the image panel (imgPane) according
 * to which thumbnail image has been clicked by the user.  At load time
 * the first thumbnail is automatically selected and loaded.
 */
function setImgPane( el, imgfile )
{
  this.pFile = imgfile;

  if( selTn != null )
  {
    selTn.setAttribute( 'class', 'tdunsel' );
  }

  selTn = el;
  selTn.setAttribute( 'class', 'tdsel' );

  pImg = new Image();
  pImg.onload = function()
  {
    imgWd = pImg.width;
    imgHt = pImg.height;
    imgDw = Math.min( 600, imgWd );

    document.getElementById( 'imgPane' ).innerHTML =
    '<button id="imgButton" type="button" onclick="sizeImgPane()">' +
             '<img id="paneImg" width="' + imgDw + '"' +
             ' src="' + pFile + '" />' +
             '</button>';
  };
  pImg.src = pFile;

  for( var i = 0; i < inpts.length; i++ )
  {
    if( inpts[i] == selTn )
    {
      cndx = i;
      break;
    }
  }
  
  if( cndx > 0 )
  {
    plnk.style.visibility = "visible";
    plnk2.style.visibility = "visible";
  }
  else
  {
    plnk.style.visibility = "hidden";
    plnk2.style.visibility = "hidden";
  }
  
  if( cndx < (inpts.length - 1 ))
  {
    nlnk.style.visibility = "visible";
    nlnk2.style.visibility = "visible";
  }
  else
  {
    nlnk.style.visibility = "hidden";
    nlnk2.style.visibility = "hidden";
  }
};

 /*
  * This function opens a new window used for viewing the full-size image.
  * The current contents of the popup window will be saved in a local variable
  * so the window can be updated when the imgPane changes.
  */

function openImgWindow( title, authors, source, date )
{
  imgWin = window.open( '', 'fullImgWin',
		                    'width=400,height=400,resizable=yes,'+
		                    'scrollbars=yes,menubar=yes,location=no' );
  
  var imgtitle = title.replace( /%27/g, "'" );
  var imgauthors = authors.replace( /%27/g, "'" );
  var imgsource  = source.replace( /%27/g, "'" );
  var imgdate = date.replace( /%27/g, "'" );
  imgtitle = imgtitle.replace( /%22/g, '"' );
  imgauthors = imgauthors.replace( /%22/g, '"' );
  imgsource = imgsource.replace( /%22/g, '"' );
  imgdate = imgdate.replace( /%22/g, '"' );

  docRef = imgWin.document.open("text/html","replace");
  docRef.location.visible=false;
  imgWin.location.visible=false;
  
  imgWin.document.writeln(
		  '<html><head>' +
		  '<title>Console</title>' +
		  '<link rel="stylesheet" type="text/css" href="/cinefiles/css/docpage.css"/>' +
		  '</head>' +
		  '<body bgcolor="white" onLoad="self.focus()">' +
		  '<p>' +
		  '<img id="fullImgWinSrc" src="' + pFile + '" />' +
		  '</p>' +
		  '<div id="imgWinFooter"><div id="imgWinCite">' +
		   ((imgtitle.length > 0) ? '<citelabel>Title</citelabel>: ' + imgtitle + '<br />' : '' ) + 
		   ((imgauthors.length > 0) ? '<citelabel>Author</citelabel>: ' + imgauthors + '<br />' : '' ) +
		   ((imgsource.length > 0) ? '<citelabel>Source</citelabel>: ' + imgsource + '<br />' : '' ) + 
		   ((imgdate.length > 0) ? '<citelabel>Date</citelabel>: ' + imgdate + '<br />' : '' )+
		   '</div>' + copyMsg + '</div>' +
		  '</body></html>' );
  
  var b = imgWin.document.getElementsByTagName( "body" );
  
  imgWin.document.close();
  
  return false;
};

/* This function is called when the prevlnk button is clicked */
function prevTn()
{
  if( cndx > 0 )
  {
    cndx--;
    inpts[cndx].click();
  }
};

/* This function is called when the nextlnk button is clicked */
function nextTn()
{
  if( cndx < ( inpts.length - 1 ))
  {
    cndx++;
    inpts[cndx].click();
  }
}

/*
 *This function appends additional actions to the window.onload function 
*/
function addLoadEvent( func )
{
  var oldfunc = window.onload;
  
  if( typeof window.onload != 'function' )
  {
    window.onload = func;
  }
  else
  {
    window.onload = function()
    {
       oldfunc();
       func();
    };
  }
};

addLoadEvent( init );
