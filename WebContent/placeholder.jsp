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
    
    <title>Place Holder</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />
 
    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>
 
      <div id="main">
        <h2>Nothing to show</h2>

        <p>
          This is a placeholder for future pages, pages under construction
           or pages temporarily unavailable due to maintenance work.
        <p>
      </div>
    </div>
 
    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
