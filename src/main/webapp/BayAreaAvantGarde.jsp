<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles "For Educators" portal, with pointers to resources, etc. --%>

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

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/baag.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Bay Area Avant-Garde</title>
  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />
  
    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>
      
      <div id="main">
        <img src="/cinefiles/images/BayAreaAvantGarde.png" />

        <div id="lmain" name="lmain">
          <h2 class="dark">Introduction</h2>
          <p>
            Pacific Film Archive is renowned for its commitment to exhibiting
            and collecting avant-garde film and video from the San Francisco
            Bay Area. On the occasion of the publication of <i>Radical Light:
            Alternative Film and Video in the San Francisco Bay Area,
            1945-2000</i>, CineFiles launches a new module with expanded
            information about key films and filmmakers featured in the book.
          </p><br />

         <h2 class="dark">
           <a href="/cinefiles/BrowseBayAreaFilms.jsp">Browse Films</a>
         </h2>
         <p>
           Browse a list of Bay Area avant-garde films for which
           documents are available in CineFiles
         </p><br />

         <h2 class="dark">
           <a href="/cinefiles/BrowseBayAreaDirs.jsp">Browse Filmmakers</a>
         </h2>
         <p>
           Browse a list of Bay Area avant-garde filmmakers for whom
           documents are available in CineFiles
         </p><br />

         <h2 class="dark">Related Links</h2>

         <p>The book <i>Radical Light</i> is accompanied by a gallery
           exhibition and a traveling film and video series. Please see
           the links below for more information on these programs.
         </p>
         <h2 class="relnk1"><a href="http://press.bampfa.berkeley.edu/radical/">
              <i>Radical Light</i> Project News Center</a></h2>

         <h2 class="relnk2"><a href="/cinefiles/rlreviews.jsp">Reviews</a></h2>
       </div>
       <div id="rmain" name="rmain">
         <h2 align="center"> Purchase Book</h2>
         <p>
           <a href="http://berkeleyartm710.corecommerce.com/San-Francisco-Bay-Area-Berkeley/Radical-Light-Alternative-Film-and-Video-in-the-San-Francisco-Bay-Area-1945-2000-p137.html">
           <img src="/cinefiles/images/radlightcover.jpg" /></a>
         </p>
         <br />
         Edited by BAM/PFA film and video curators Kathy Geritz and
         Steve Seid, and Steve Anker, dean of the School of
         Film/Video at CalArts.
         <br /> <br />
         Copublished by BAM/PFA and UC Press in 2010<br >
         32 pages paperback
       </div>
      </div>
    </div>
 
    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
