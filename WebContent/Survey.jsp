<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles  "Survey" form, hrefed from the standard page footer --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css' href='/cinefiles/css/general.css'/>

    <script src="js/cine.js" type="text/javascript" language="JavaScript">
    </script>
    <title>User Survey</title>
  </head>

  <body>
    <jsp:include page="include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>
      
      <div id="main">
         The User Survey not yet available. Please try again later.
        
      </div>
    </div>

    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>
