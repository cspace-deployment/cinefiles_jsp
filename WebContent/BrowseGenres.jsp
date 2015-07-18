<?xml version="1.0" encoding="UTF-8" ?>

<%-- Film genres browser, with navigation links to alphabetical groups. --%>

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
          href='/cinefiles/css/general.css'/>

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/directors.css'/>

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/browse.css'/>

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/ua.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script>
        ga('set', 'page', '/BrowseGenres.jsp');
        ga('send', 'pageview');
    </script>

    <script src="/cinefiles/js/browselist.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/search.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Browse Film Genres</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
        <%@ include file="/include/browseopts.jspf" %>
      </div>

      <div id="main">
        <%@ include file="/include/browsegenres.jspf" %>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
