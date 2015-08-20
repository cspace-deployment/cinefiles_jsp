<?xml version="1.0" encoding="UTF-8" ?>

<%-- Display list of film records returned from FilmSearch.jsp --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/general.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/results.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/filmsorter.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

   <script src="/cinefiles/js/filmsorter.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/filmresults.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Film Search Results</title>
  </head>

  <body>
   <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main">
        <h2>Filmographic Search Results</h2>
        <%@ include file="/include/filmlist.jspf" %>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
