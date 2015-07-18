<?xml version="1.0" encoding="UTF-8" ?>

<%-- Search Form for Filmographic database queries.  --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/general.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/filmsearch.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/search.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/ua.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script>
        ga('set', 'page', '/FilmSearch.jsp');
        ga('send', 'pageview');
    </script>

    <title>Advanced Film Search</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>

        <div id="tips">
          <h2>Search Tips</h2>

          <p>Conduct a search using just one field, or a
             combination of fields.</p>

          <p>Subject terms are indexed according to the
             Library of Congress Authorities.
             Refer to our <a href="/cinefiles/BrowseSubjects.jsp">subject</a>
             and <a href="/cinefiles/BrowseGenres.jsp">genre</a> browse
             lists to find the appropriate phrasing for your search
             term.</p>

          <p>Broaden your search results by picking 'contains'
             from the 'Search type' drop-down menu.</p>

          <p>Enter a single year (1990) or a range of years
             (1990-1999) in the 'Year' field.</p>

          <p>For more detailed instructions, please refer
             to the <a href="/cinefiles/Help.jsp">Help</a> page.</p>
         </div>
      </div>

      <div id="main">
        <h2>Advanced Film Search</h2>

        <p>Use the Film Search form to look for information about specific
           films; to find films by a certain director, from a particular
           country or time period, or associated with a subject or genre;
           or any combination of the above.</p>

        <%@ include file="/include/filmsearch.jspf" %>
      </div> 
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>

