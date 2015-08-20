<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles "Comments" form, linked to from the standard page footer --%>

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

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/contact.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Send Comments to CineFiles</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main">
        <h2>Contact Us</h2>
        <br />
        <p><b>Mailing Address</b><br />
        CineFiles<br />
        PFA Library and Film Study Center<br />
        Berkeley Art Museum and Pacific Film Archive<br />
        2625 Durant Avenue #2250<br />
        Berkeley, CA 94720-2250</p><br />

        <p><b>Telephone Numbers</b><br />
        PFA Library and Film Study Center: (510) 642-1437<br />
        PFA General Information: (510) 642-1412</p>

        <p><b>TTY</b><br />
        (510) 642-8734</p><br />

        <p><b>Email</b><br />
        General:
        <a href="mailto:cinefiles@berkeley.edu">cinefiles@berkeley.edu</a>
        <br />Nancy Goldman, Project Supervisor:
        <a href="mailto:nlg@berkeley.edu">nlg@berkeley.edu</a><br />

        <p>You can also send us a message through the
        <a href="/cinefiles/Comments.jsp">comments</a>
        form on our website.</p>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
