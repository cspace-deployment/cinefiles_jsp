<?xml version="1.0" encoding="UTF-8" ?>

<%-- Error page from the DocDetail page for an invalid doc_id --%>

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

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/search.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Document Citation Error</title>
  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container" name="container">

      <div id="searchside" name="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main" name="main">
        <h2>There was an error</h2>
        <br />
        <p>We are sorry, but an error occured while looking up
        the citation information details for this document.</p>

        <p>Please send us <a href="/cinefiles/Comments.jsp">email</a>,
        briefly describing the situation so that we may attempt to
        identify and fix the problem.</p>
        <br />
      </div>
    </div>

    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>
