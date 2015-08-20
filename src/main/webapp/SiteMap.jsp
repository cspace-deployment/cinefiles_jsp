<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles "Help" page, hints for improved searches.  --%>

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

    <link rel='stylesheet' type='text/css' href='/cinefiles/css/general.css'/>
    <link rel='stylesheet' type='text/css' href='/cinefiles/css/sitemap.css'/>

    <script src="js/cine.js" type="text/javascript" language="JavaScript">
    </script>

    <title>CineFiles Site Map</title>
  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main">
        <h2>Site Map for CineFiles</h2>
        <ul id="sitelist">
          <br />
          <li>Information</li>

          <li>
            <ul>
              <li><a href="/cinefiles/Welcome.jsp">Home Page</a></li>
              <li><a href="/cinefiles/AboutUs.jsp">About Us</a></li>
              <li><a href="/cinefiles/Copyright.jsp">CineFiles and copyright</a>
              <li><a href="/cinefiles/Donors.jsp">Donors</a></li>
              <li><a href="http://www.surveymonkey.com/s.aspx?sm=awo8xoQFfSFquCm7S7hXAQ_3d_3d">User Survey</a></li>
            </ul>
          </li>
          <br />
          <li>Seaching</li>
          <li>
            <ul>
              <li><a href="/cinefiles/Help.jsp">Search Tips</a></li>
              <li><a href="/cinefiles/DocSearch.jsp">Document Search</a></li>
              <li><a href="/cinefiles/FilmSearch.jsp">Filmographic Search</a></li>
            </ul>
          </li>
          <br />
          <li>Browse Lists</li>
          <li>
            <ul>
              <li><a href="/cinefiles/BrowseDirs.jsp">Directors</a></li>
              <li><a href="/cinefiles/BrowseBayAreaDirs.jsp">Bay Area Avant-garde Directors</a></li>
              <li><a href="/cinefiles/BrowseFilmTitles.jsp">Film Titles</a></li>
              <li><a href="/cinefiles/BrowseBayAreaFilms.jsp">Bay Area Avant-garde Film Titles</a></li>
              <li><a href="/cinefiles/BrowseSubjects.jsp">Subjects</a></li>
              <li><a href="/cinefiles/BrowseGenres.jsp">Genres</a></li>
              <li><a href="/cinefiles/BrowseFeatured.jsp">Featured Documents</a></li>
            </ul>
          </li>
          <br />
          <li>For Educators</li>
          <li>
            <ul>
               <li><a href="/cinefiles/ForEducators.jsp">Portal for Educators</a></li>
               <li><a href="/cinefiles/ThemesTopics.jsp">Themes and Topics</a></li>
            </ul>
          </li>
          <br />
          <li>Special Interest Items</li>
          <li>
            <ul>
               <li><a href="/cinefiles/FilmSearch?country=Soviet_Union&year=<1931">Soviet Cinema</a></li>
               <li><a href="/cinefiles/DocSearch?doctype=26">exhibitor manuals</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
