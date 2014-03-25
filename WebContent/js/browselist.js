/*
 * Javascript functions used in the Browse pages.
 * 
 * Uses an Ajax request object to update the innerHTML contents of the
 * "browsePane" div, with the contents of a selected browse list.
 */

var htmlreq = false;

function openHttpRequest()
{
  try
  {
    if( window.XMLHttpRequest )
    {
      htmlreq = new XMLHttpRequest();
    }
    else if( window.ActiveXObject )
    {
      htmlreq = new ActiveXObject( "Microsoft.XMLHTTP" );
    }
  }
  catch(e)
  {
    htmlreq = false;
  }

  if( ! htmlreq )
  {
    alert( "Javascript was unable to create a request object.." );
  }
};

function setBrowsePane( infile )
{
  if( ! htmlreq )
  {
    openHttpRequest();
  }

  if( ! htmlreq )
  {
	  alert( "Javascript was unable to create a request object.." );
  }
  else
  {
    htmlreq.open( "GET", "/cinefiles/include/" + infile, true );
    htmlreq.send( null );

    htmlreq.onreadystatechange = function()
    {
      if( htmlreq.readyState == 4 )
      {
        document.getElementById( 'browsePane' ).innerHTML=htmlreq.responseText;
      }
    }
  }
};

