<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/general.css'/>

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/results.css'/>

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/docresults.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/docsorter.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Document Search Results</title>
  </head>

  <body>
    <!-- CineFiles page header with banner image -->
    <div id="header">
      <map name="pgheadermap">
        <area shape="rect" coords="2, 10, 250, 48"
              href="/cinefiles/Welcome.jsp" />

        <area shape="rect" coords="285, 30, 885, 47"
              href="http://bampfa.berkeley.edu" />
      </map>

      <img src="/cinefiles/images/cinefiles_p.png"
           usemap="#pgheadermap" border="0" />
    </div>

    <div id="container">
      <div id="searchside">
        <div id="search">
          <h1>Search CineFiles Database</h1>
  
          <form id="quickform" method="post" action="/cinefiles/FilmSearch"
                onsubmit="this.quickterm.required = true;
                return checkFormInput( this );">
  
            <input id="quickterm" type="text" name="quickterm" /><br />
  
            <select id="quicktype" name="quicktype" size="1">
              <option value="title">Film Title</option>
              <option value="director">Film Director</option>
              <option value="subject">Film Subject</option>
              <option value="genre">Film Genre</option>
            </select>
  
            <input class="fbutton" type="submit" value="Search" />
          </form>
  
          <br />
          <label class="sellabel">Advanced Search</label>
          <select id="advancedType" name="advancedType" size="1"
                  onchange="goToSearch(this);">
            <option value="" selected="selected">Search by</option>
            <option value="doc">Document</option>
            <option value="film">Film</option>
          </select>
  
          <label class="sellabel">Browse Lists</label><br />
          <select id="browseBy" name="browseBy" size="1"
                  onchange="goToBrowse(this);">
            <option value="" selected="selected">Browse by</option>
            <option value="director">Director</option>
            <option value="baagdirector">Bay Area Avant-Garde Filmmaker</option>
            <option value="film">Film Title</option>
            <option value="baagfilm">Bay Area Avant-Garde Film Title</option>
            <option value="genre">Genre</option>
            <option value="subject">Subject</option>
            <option value="featured">Featured Documents</option>
          </select>
        </div>
      </div>

      <div id="main">
        <h2>Document Search Results</h2>
        <p>2 related documents.
        Click on a title for a detailed  citation and document access.
        <br />

         Sort results by
         <select id="sorttype" name="sorttype" size="1"                  
                 onchange="sortDocList(this, 'docol');">
           <option value="title">Document Title</option>
           <option value="type">Document Type</option>
           <option value="date" selected="selected">Publication Date</option>
         </select></p>

         <div id="revresults">
           <ol id="docol">
             <li>
               <a href="/cinefiles/DocDetail?docId=54955">
                 Bay watch
               </a>
               - Halter, Ed - <i>Artforum</i>
               - 2010 Nov - 2 pages -  - book review
            </li>

            <li>
              <a href="/cinefiles/DocDetail?docId=54954">
                 Threading through the Golden Gate : San Francisco's
                 50-year run as the worlds experimental-cinema leader
              </a>
              - McElhatten, Mark - <i>Film Comment</i>
              - 2010 Nov - 1 page -  - book review
            </li>
          </ol>
        </div>
      </div>
    </div>

    <!-- The standard CineFiles footer, included on every page -->

    <div id="footer">
      <map name="footermap">
        <area shape="rect" coords="1, 1, 140, 24"
              href="http://bampfa.berkeley.edu" />
        <area shape="rect" coords="290, 5, 325, 18"
              href="/cinefiles/Welcome.jsp" />
        <area shape="rect" coords="352, 7, 412, 18"
              href="/cinefiles/AboutUs.jsp" />
        <area shape="rect" coords="443, 7, 472, 18"
              href="/cinefiles/Help.jsp" />
        <area shape="rect" coords="505, 7, 575, 18"
              href="http://www.surveymonkey.com/s.aspx?sm=awo8xoQFfSFquCm7S7hXAQ_3d_3d" />
        <area shape="rect" coords="612, 7, 680, 18"
              href="/cinefiles/Comments.jsp" />
        <area shape="rect" coords="715, 7, 765 18"
              href="/cinefiles/SiteMap.jsp" />
        <area shape="rect" coords="795, 7, 865, 18"
              href="/cinefiles/Contact.jsp" />
      </map>
      <img src="/cinefiles/images/pgfooter.gif" usemap="#footermap" border="0" />
    </div>
  </body>
</html>
