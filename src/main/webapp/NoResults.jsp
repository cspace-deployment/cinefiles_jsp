<?xml version="1.0" encoding="UTF-8" ?>

<%-- Error page returned when a document or film search fails. --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css' href='css/general.css'/>

    <script src="js/cine.js" type="text/javascript" language="JavaScript">
    </script>

    <title>No Query Results</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main">
        <h2>No Search Results</h2>

        <p>Your search returned no results.</p>

        <p>
          New titles and document images are added daily, so if your
          search today brings no results, you may wish to try again
          after a few weeks.
        </p><br />

        <p>
          If you are interested in seeing a title or director not
          currently represented within CineFiles, please send us
          an <a href="mailto:cinefiles@uclink.berkeley.edu">email</a>.
          If we have any materials related to your interest in our
          files, we will prioritize the indexing and scanning of
          these documents.
        </p><br />

        <h2>Hints for improving search results</h2>

        <p>
          Check your spelling.<br />
          Make sure that you have the appropriate category selected
          in the pulldown menu.<br />
          Keep in mind that Subject and Genre terms are indexed
          according to the Library of Congress Authorities. If
          you are unsure of the exact term for a subject,
          refer to our <a href="/cinefiles/BrowseSubjects.jsp">subject</a>
          and <a href="/cinefiles/BrowseGenres.jsp">genre</a> browse lists
          to find the appropriate phrasing for your search term.
        </p><br />

        <h2>Additional Tips for Advanced Search</h2>

        <p>
          Try selecting "contains" in the Search Type field.<br />
          Try selecting "any" in the Document Type field.
        </p><br />

        <p>
          For detailed instructions on searching, go to the
          <a href="/cinefiles/Help.jsp">Help</a> page.
        </p><br />
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
