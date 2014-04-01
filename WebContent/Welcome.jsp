<?xml version="1.0" encoding="UTF-8" ?>

<%-- The CineFiles homepage. --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC
          "-//W3C//DTD XHTML 1.0 Transitional//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel='icon' href='cine.ico' type='image/x-icon'>
    <link rel='shortcut icon' href='cine.ico' type='image/x-icon'>

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/general.css' />

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/homepage.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Welcome to CineFiles</title>
  </head>

  <body>
    <jsp:include page="/include/wpgheader.jspf" />

    <div id="container" name="container">

      <%@ include file="/include/wsearch.jspf" %>

      <div id="main" name="main">
        <div id="bleft" name="bleft">
          <h2>CineFiles</h2>
          <p>
            contains scanned images of reviews, press
            kits, festival and showcase program notes,
            newspaper articles, and other documents from
            the PFA Library's extensive collection covering
            world cinema, past and present. Citations are
            available for all documents, and page images
            are available for documents with
            <a href="/cinefiles/Copyright.jsp">copyright</a>
            clearance. New titles and document images
            are added daily.
          </p>

          <p>
            CineFiles currently includes documents
            on the films of more than 150 major
            international <a href="/cinefiles/Directors.jsp">directors</a>,
            materials describing
            <a href="/cinefiles/FilmSearch?country=Soviet_Union&year=<1931">
            silent Soviet cinema</a>, and PFA's unique collection of
            <a href="/cinefiles/DocSearch?doctype=26">exhibitor manuals</a>,
            among other documents.
          </p>

        </div>

        <div id="bcenter" name="bcenter">
          <h2>
            <a href="/cinefiles/ForEducators.jsp">Portal for Educators</a>
          </h2>

          <p>
            Film recommendations for a variety of
            themes and topics; resource guides for
            select films and links to other resources.
          </p><br /><br />

          <h2>
            <a href="http://www.bampfa.berkeley.edu/copyright_project/">
              Copyright<br />Resources Project</a>
          </h2>

          <p>information on working with copyright-protected
             materials in a digital environment.</p><br /><br />

          <h2>
            <a href="https://givetocal.berkeley.edu/egiving/index.cfm?Org=%20BAM%2FPFA%20CineFiles%20Project%20Fund&Fund=FU0968000" />
            Support CineFiles</a>
          </h2>

          <p>
            All gifts, large and small, help us grow this
            relevant and important project. Thanks!
          </p>

          <map name="donmap">
          	<area shape="rect" coords="1, 1, 60, 18"
          	      href="https://givetocal.berkeley.edu/egiving/index.cfm?Org=%20BAM%2FPFA%20CineFiles%20Project%20Fund&Fund=FU0968000" />
          </map>
          <img src="/cinefiles/cine_img/icons/donate.png"
               usemap="#donmap" border="0" />

        </div>

        <%@ include file="/include/featured.jspf" %>

        <div id="credits" name="credits">
          <div id="clogos" name="clogos">
            <img src="/cinefiles/cine_img/icons/logos.png" height="50" />
          </div>

          <div id="ctext" name="ctext">
            UC Berkeley's <a href="http://www.mip.berkeley.edu">
            Museum Informatics Project</a> provides database design,
            database management support and software maintenance for
            CineFiles. This project is supported in part by grants from
            the <a href="http://www.neh.gov/">National Endowment for the
            Humanities</a>; the <a href="http://www.imls.gov/">Institute
            of Museum and Library Services</a>; the Library Services and
            Technology Act; and the <a href="http://www.packhum.org/">
            Packard Humanities Institute</a>, as well as by individual
            <a href="/cinefiles/Donors.jsp">donors</a>.
          </div>
        </div>
      </div>
    </div>

    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>

