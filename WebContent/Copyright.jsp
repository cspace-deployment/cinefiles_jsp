<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles "Copyright" page, linked by href from "Welcome" page.  --%>

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

  <title>CineFiles and Copyright</title>
</head>
<body>
  <jsp:include page="/include/pgheader.jspf" />

  <div id="container" name="container">

    <div id="searchside"> name="searchside">
      <%@ include file="/include/search.jspf" %>
    </div>

    <div id="main" name="main">
      <h2>CineFiles and Copyright</h2>

      <p>The materials gathered and made accessible through the CineFiles
         database are to be used solely for the purposes of education and
         scholarship. The Pacific Film Archive does not hold rights to the
         documents in its collection. Therefore, it does not charge permission
         fees for use of such material, and neither can it give or deny
         permission to publish or distribute these documents.
      </p><br />

      <p>Some materials in the database may be protected by the U.S.
         Copyright Law (Title 17, U.S. Code) and/or by the copyright or
         neighboring-rights laws of other nations. It is the user's
         obligation to determine and satisfy copyright restrictions when
         publishing or otherwise distributing materials from the Pacific
         Film Archive's collection. Transmission or reproduction of
         protected items beyond that allowed by fair use usually requires
         the written permission of copyright owners.
      </p><br />

      <p>The Pacific Film Archive has made every effort to identify the
         copyright owners of documents in its collections, and has only
         posted reproductions of documents with the permission of known
         copyright holders. However, the nature of archival collections
         is such that copyright may often be difficult to determine.
         Information on copyright owners is included in the indexing for
         each document, if known. This information is intended to assist
         users of the CineFiles database to determine appropriate use of
         an item, but that determination ultimately rests with the patron.
      </p><br />

      <p><b>Note to Copyright Holders</b><br />
         The Pacific Film Archive is eager to hear from any copyright owners
         who are not properly identified so that the appropriate information
         may be provided in the future. You can contact the PFA Library at
         <a href="mailto:cinefiles@berkeley.edu">cinefiles@berkeley.edu.</a>
      </p><br />

      <p><b>Other Resources</b><br />
         More information about copyright is available from the United States
         Copyright Office. You can also visit the PFA Library's website on the
         IMLS-funded <a href="http://www.bampfa.berkeley.edu/copyright_project">
         Copyright Resources Project</a>, for information on working with
         copyright-protected materials in a digital-environment.
      </p>
    </div>
  </div>

  <jsp:include page="/include/pgfooter.jspf" />
</body>
</html>
