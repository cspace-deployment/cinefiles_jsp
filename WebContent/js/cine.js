/*
 * This script is loaded by every JSP, it contains the most common functions.
 */

/*
 * Prevents embedding of Cinefiles in other web-sites.
 */
if( top.location != self.location )
{
  top.location.replace( self.location );
}

/*
 * There is a Select object on every page that uses this function
 * to navigate to one of the Detailed Search pages.
 */
function goToSearch( select )
{
  if( select.selectedIndex > 0 )
  {
    var value = select.value;

    /* Make sure the Select widget gets reset to its default value */
    select.selectedIndex = 0;

    if( value == "doc" )
    {
      document.location.href="/cinefiles/DocSearch.jsp";
    }
    else if( value == "film" )
    {
      document.location.href="/cinefiles/FilmSearch.jsp";
    }
  }
}

/*
 * There is a Select object on every page that uses this function
 * to navigate to one of the Browse pages.
 */
function goToBrowse( select )
{
  if( select.selectedIndex > 0 )
  {
    var value = select.value;
    select.selectedIndex = 0;
  }

  if( value == "director" )
  {
    document.location.href="/cinefiles/BrowseDirs.jsp";
  }
  else if( value == "baagdirector" )
  {
    document.location.href="/cinefiles/BrowseBayAreaDirs.jsp";
  }
  else if( value == "film" )
  {
    document.location.href="/cinefiles/BrowseFilmTitles.jsp";
  }
  else if( value == "baagfilm" )
  {
    document.location.href="/cinefiles/BrowseBayAreaFilms.jsp";
  }
  else if( value == "genre" )
  {
    document.location.href="/cinefiles/BrowseGenres.jsp";
  }
  else if( value == "subject" )
  {
    document.location.href="/cinefiles/BrowseSubjects.jsp";
  }
  else if( value == "featured" )
  {
	 document.location.href="/cinefiles/BrowseFeatured.jsp";
  }
}

/*
 * Verify that the required form fields have searchable values.
 */
function checkFormInput( f )
{
  var v = false;

  for( var i = 0; i < f.length; i++ )
  {
    var e = f.elements[i];

    if((e.type == "text") || ( e.type == "textarea" ))
    {
      if( e.value.match( /\S+/ ))
      {
        v = true;
      }
      else if( e.required )
      {
        return false;
      }
    }
  }

  return v;
}

var _gaq =  _gaq || [];
_gaq.push(['_setAccount', 'UA-8226360-6']);
_gaq.push(['_setDomainName', 'bampfa.berkeley.edu']);
_gaq.push(['_trackPageview']);

(function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

