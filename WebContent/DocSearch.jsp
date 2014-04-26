<?xml version="1.0" encoding="UTF-8" ?>

<%-- Search Form for Document record database queries.  --%>

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
          href='/cinefiles/css/docsearch.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/search.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Advanced Document Search</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>

        <div id="tips">
          <h2>Search Tips</h2>

          <p>Conduct a search using just one field,
             or a combination of fields.</p>

          <p>'Subject of document' can either be a topic
             (e.g., box office) or a personality (e.g., Chaplin).</p>

          <p>Subject terms are indexed according to the
             Library of Congress Authorities.

             Refer to our <a href="/cinefiles/BrowseSubjects.jsp">subject</a>
             and <a href="/cinefiles/BrowseGenres.jsp">genre</a> browse lists
             to find the appropriate phrasing for your search term.</p>

          <p>Broaden your search results by picking 'contains'
             from the 'Search type' drop-down menu.</p>

          <p>Limit your search using the 'Document type'
             drop-down menu.</p>

          <p>For more detailed instructions, please refer
             to the <a href="/cinefiles/Help.jsp">Help</a> page.</p>
         </div>
      </div>

      <div id="main">
        <h2>Advanced Document Search</h2>

        <p>Use this Document Search form to look for documents by title,
           author, date or publication; documents about specific films,
           people or subjects; documents containing technical credits,
           productions costs, or other categories of information pertaining
           to a film; or any combination of these.</p>

        <%@ include file="/include/docsearch.jspf" %> 
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
