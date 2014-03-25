/*
 * A set of functions used for sorting the document list in DocResults.jsp.
 * This file needs to be edited in a UTF-8 environment. If edited in a Latin-1
 * environment it must be converted from to UTF-8 with a conversion tool such
 * as "iconv".
 */

/* lowercase accented characters to be converted to ascii for sorting */
var lowerChars = ["à","á","â","ã","ä","å","æ","ç","è","é","ê","ë",
      "ì","í","î","ï","ð","ñ","ò","ó","ô","õ","ö","ø","ù","ú"];

/* lowercase ascii replacement characters for the accented characters. */
var replaceChars = ["a","a","a","a","a","a","ae","c","e","e","e","e",
       "i","i","i","i","d","n","o","o","o","o","o","oe","u","u"];

/* an array of compiled regular expressions constructed from "lowerChars" */
var regexArr = []
                
for( var i=0; i<lowerChars.length; i++ )
{
   regexArr[i] = new RegExp( lowerChars[i], 'gi' );
}

/* a hashtable used to calculate a date number, an integer used for sorting */
var monthNumbers = { jan: 0, feb: 1, mar: 2, apr: 3, may: 4, jun: 5,
                     jul: 6, aug: 7, sep: 8, oct: 9, nov: 10, dec: 11 };

/* a compiled regexp that trims a string front and back */
var regexTrim  = new RegExp( /^\W+|\s+$/gi );

/* a compiled regexp that removes everything but the "Type" part */
var regexType  = new RegExp( /^.*-\s*|\s+$/gi );

/* a compiled regexp that removes everything up to the "Date" part */
var regexDate  = new RegExp( /^[^-]*-\s*/gi );

/* a compiled regexp that removes from a "-" character to end of string*/
var regexEnd   = new RegExp( /\s*-.*\s*$/mg );

/* a compiled regexp that selects the "Day" part of a date */
var regexDay   = new RegExp( /^c?\d{4}\s+[a-z]{3}\s+(\d{1,2})(\s|$)/ );

/* a compiled regexp that selects the "Month" part of a date */
var regexMonth = new RegExp( /^c?\d{4}\s+([a-z]{3})(\s|$)/ );

/* a compiled regexp that selects the "Year" part of a date */
var regexYear  = new RegExp( /^c?(\d{4})(\s|$)/ );

/* a compiled regexp that trims off leading articles + white space */
var regexTrimPref = new RegExp( /^n\s+|^t\s+|^a\s+|^al\s+|^an\s+|^as\s+|^az\s+|^bir\s+|^das\s+|^de\s+|^dei\s+|^den\s+|^der\s+|^det\s+|^di\s+|^die\s+|^dos\s+|^een\s+|^eene\s+|^egy\s+|^ei\s+|^ein\s+|^eine\s+|^eit\s+|^el\s+|^el-\s*|^els\s+|^en\s+|^et\s+|^ett\s+|^gl'\s*|^gli\s+|^ha-\s*|^hai\s+|^he\s+|^he-\s*|^heis-\s*|^hen\s+|^hena\s+|^henas\s+|^het\s+|^hin\s+|^hinar\s+|^hinn\s+|^hio\s+|^ho\s+|^hoi\s+|^i\s+|^il\s+|^ka\s+|^ke\s+|^l'|^la\s+|^las\s+|^le\s+|^les\s+|^lo\s+|^los\s+|^mia\s+|^na\s+|^nje\s+|^nji\s+|^o\s+|^os\s+|^ta\s+|^the\s+|^to\s+|^um\s+|^uma\s+|^un\s+|^un'\s*|^una\s+|^une\s+|^uno\s+|^y\s+|^yr\s+/i );

/* normalize a string: lowercase, no prefix, no accented characters */
function normalize( str )
{
  /* remove leading and trailing whitespace, convert to lowercase */
  var s1 = str.replace( regexTrim, '' ).toLowerCase();

  /* remove any leading articles */
  s1 = s1.replace( regexTrimPref, '' );

  /* replace accented characters with unaccented equivalents */
  for( var i=0; i<lowerChars.length; i++ )
  {
    if(  s1.indexOf( lowerChars[i] ) >= 0 )
    {
      s1 = s1.replace( regexArr[i], replaceChars[i] );
    }
  }
  return s1;
};

/* convert a date string to an integer for sorting */
function dateNumber( str )
{
  if(( str == null ) || ( str.nodeValue == null ))
  {
	  return 0;
  }
    
  var a = 0;
  var y = 0;
  var m = 0;
  var d = 0;
  var date;
  var arr = [];
  
  date = str.nodeValue.replace( regexDate, '' );  
  date = date.replace( regexEnd, '' ).toLowerCase();

  arr = date.match( regexYear );
  
  if( arr != null )
  {
    y = arr[1];

    arr = date.match( regexMonth );

    if( arr != null )
    {
      m = monthNumbers[arr[1]];

      if( isNaN( m ))
      {
        m = 0;
      }

      arr = date.match( regexDay );

      if( arr != null )
      {
        d = arr[1];
      }
    }
  }

  var a = ((d*1)+(m*32)+(y*512));
  return isNaN(a) ? 0 : a;
};

/* Date comparator function passed to Array.sort for sorting dates */
function compDocListDate( arg_a, arg_b )
{
  var a = arg_a;
  var b = arg_b;

  try
  {
    a = dateNumber( a.childNodes[3] );
  }
  catch (e)
  {
	a = 0;
  }
  
  try
  {
    b = dateNumber( b.childNodes[3] );
  }
  catch (e)
  {
	b = 0;
  }
  
  if( a < b )
    return 1
 
  if( a > b )
    return -1;

  return 0;
};

/* Title comparator function passed to Array.sort for sorting titles */
function compDocListTitle( arg_a, arg_b )
{
  var a = arg_a;
  var b = arg_b;
  
  try
  {
    a = normalize( a.firstChild.firstChild.nodeValue );
  }
  catch (e)
  {
    a = "";
  }
  
  try
  {
    b = normalize( b.firstChild.firstChild.nodeValue );
  }
  catch (e)
  {
    b = "";
  }

  if( a > b )
    return 1;
  if( a < b )
    return -1;
 
  return 0;
};

/* Type comparator function passed to Array.sort for sorting doc types */
function compDocListType( arg_a, arg_b )
{
  var a = arg_a;
  var b = arg_b;
  
  try
  {
    a = a.childNodes[3];
    a = a.nodeValue.replace( regexType, '' ).toLowerCase(); 
  }
  finally
  {
    if( a == null )
    {
  	  a = "";
    }
  }
  
  
  try
  {
    b = b.childNodes[3];
    b = b.nodeValue.replace( regexType, '' ).toLowerCase();
  }
  finally
  {
    if( b == null )
	{
		b = "";
	}
  }
  
  if( a > b )
    return 1;

  if( a < b )
    return -1;

  return 0;
};

/* main function called for sorting a document list */
function sortDocList( select, ol )
{
  if( select.selectedIndex >= 0 )
  {
	/* get the object to be sorted */
    var olist = document.getElementById( ol );
    
    /* get the items in the object to be sorted */
    var items = olist.getElementsByTagName( "li" );

    /* save the items in an array */
    var arr = [];

    for( var i = 0; i < items.length; i++ )
    {
      var fc = items[i].firstChild;

      /* remove the leading empty text nodes */
      while(( fc.nodeType == 3 ) && ( ! /\S/.test( fc.nodeValue )))
      {
        items[i].removeChild(fc);
        fc = items[i].firstChild;
      }

      arr.push( items[i] );
    }

    var value = select.value;

    /* call Array.sort with the proper comparator function */
    if( value == "date" )
    {
      arr.sort( compDocListDate );
    }
    else if( value == "title" )
    {
      arr.sort( compDocListTitle );
    }
    else if( value == "type" )
    {
      arr.sort( compDocListType );
    }

    /* remove and re-insert the sorted items back into the document */
    for( var i = 0; i < arr.length; i++ )
    {
      var c = olist.removeChild( arr[i] );
      olist.appendChild( c );
    }
  }
};
