<?xml version="1.0" encoding="UTF-8" ?>

<%-- Displays a detailed document citation, called from a document ref --%>

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
          href='/cinefiles/css/docrestpage.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/docpage.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Document Citation</title>
  </head>

  <body>
    <c:set var="docauthors" value="${docdetail.authorString}" />
    <c:set var="doctitle" value="${docdetail.title}" />
    <c:set var="docdate" value="${docdetail.date}" />
    <c:set var="docsource" value="${docdetail.source}" />
    <c:set var="aposchar" value="'" />
    <c:set var="dqchar" value='"' />

    <c:set var="qauthors" value="${fn:replace(docauthors, aposchar, '%27')}" />
    <c:set var="qauthors" value="${fn:replace(qauthors, dqchar, '%22')}" />
    <c:set var="qtitle" value="${fn:replace(doctitle, aposchar, '%27')}" />
    <c:set var="qtitle" value="${fn:replace(qtitle, dqchar, '%22')}" />
    <c:set var="qsource" value="${fn:replace(docsource, aposchar, '%27')}" />
    <c:set var="qsource" value="${fn:replace(qsource, dqchar, '%22')}" />
    <c:set var="qdate" value="${fn:replace(docdate, aposchar, '%27')}" />
    <c:set var="qdate" value="${fn:replace(qdate, dqchar, '%22')}" />

    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>

        <h2>Document Citation
        <a href="/cinefiles/DocPdf?docId=${docdetail.docId}">
        <img id="prncite" src="/cinefiles/images/download.gif"/>
        </a></h2>
        <%@ include file="/include/doccite.jspf" %>
        <%@ include file="/include/docrestpages.jspf" %>
      </div>

      <div id="main">
        <h2>Page Image</h2>
        <div id="imgPane">
          <br /><br />
          <p>
            This document cannot be displayed here due to copyright
            restrictions.
          </p><br />

          <p>
            If you would like to receive a PDF version of this document,
            please send us your
            <br /><br />

            <form method="post" action="/cinefiles/Comments">
                <input type="hidden" name="src" value="restdoc" />
            	<input type="hidden" name="pdfdocid" value="${docdetail.docId}" />
            	Email Address: <input type="text" name="userEmail" length="45" />
            	<input type="submit" value="Request Document" />
            </form>
          </p>

        </div>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
