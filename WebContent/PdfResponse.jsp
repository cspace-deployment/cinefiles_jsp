<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles Comments Response page. Returned by the Comments servlet --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/general.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>
    <title>Thank You</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container" name="container">

      <div id="searchside" name="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main" name="main">
        <h4>Thank you.</h4>
        <br />
        Your request has been received.  The document you requested will
        be E-mailed to the address you provided, usually within one or two
        business days.
      </div>
    </div>

    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>
