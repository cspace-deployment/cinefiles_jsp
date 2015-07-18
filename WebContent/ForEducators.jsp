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
          href='/cinefiles/css/educators.css' />

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/ua.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script>
        ga('set', 'page', '/ForEducators.jsp');
        ga('send', 'pageview');
    </script>

    <title>Portal for Educators</title>
  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />
  
    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>
      
      <div id="main">
        
        <img src="/cinefiles/cine_img/icons/forEds.gif" />
        <div id="lmain">
          <h2><a href="/cinefiles/ThemesTopics.jsp">Themes and Topics</a></h2>
          <p>Recommendations for films organized by themes culled from
          the California Content Standards. The themes have been
          adapted to a broader framework, so that the list can be useful
          to all educators.</p><br />

          <h2>
            <a href="/cinefiles/BayAreaAvantGarde.jsp">Bay Area Avant-garde</a>
          </h2>
          <p>
            <b>New Module!</b> Expanded information on key films and filmmakers
            featured in the book <i>Radical Light: Alternative Film and Video
            In the San Francisco Bay Area, 1945-2000</i>, an overview of 
            avant-garde cinema in the Bay Area.
          </p><br />

          <h2 class="dark">Useful Links</h2>
          <p><a href="http://www.bampfa.berkeley.edu/copyright_project/">
          Copyright Resources Project</a><br />
          <a href="http://www.medialit.org">
            Media Literacy Resources
          </a><br />
          <a href="http://memory.loc.gov/learn/lessons/primary.html">
            Using Primary Sources in the Classroom
          </a><br />
          <a href="http://www.ala.org/ala/mgrps/divs/rusa/sections/history/resources/pubs/usingprimarysources/index.cfm">
            Using Primary Sources on the Web
          </a><br />
        </div>
        
        <div id="rmain">
          <h2 class="dark">Resource Guides</h2>
          <p>The guides in this section are intended as
          models of how the resources on CineFiles
          might be used to enrich the classroom viewing
          experience. We hope to expand this section in
          the future.</p><br />

          <a href="/cinefiles/cine_img/pdfs/Guide_BirthOfANation.pdf">
          Birth of a Nation</a><br />
          <a href="/cinefiles/cine_img/pdfs/Guide_ControlRoom_Tanner88.pdf">
          Control Room/Tanner &apos;88</a><br />
          <a href="/cinefiles/cine_img/pdfs/Guide_BowlingForColumbine_Elephant.pdf">
          Bowling for Columbine/Elephant</a><br />
        </div>
              
      </div>
    </div>
 
    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
