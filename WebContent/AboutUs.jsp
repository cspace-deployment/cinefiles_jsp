<?xml version="1.0" encoding="UTF-8" ?>

<%-- CineFiles "About" page, linked to in the standard page footer.  --%>

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
          href='/cinefiles/css/general.css'/>

    <link rel='stylesheet' type='text/css'
          href='/cinefiles/css/aboutus.css'/>

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/search.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>About Us</title>

  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container" name="container">

      <div id="searchside" name="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main" name="main">
        <h2>About Us</h2>

        <p><b>The University of California, Berkeley Art Museum
          and Pacific Film Archive (BAM/PFA)</b><br/>
          is the visual arts center of the University of California, Berkeley.
          One of the largest university art museums in the United States,
          in both size and attendance, BAM/PFA's diverse exhibition programs
          and collections are characterized by themes of artistic innovation,
          intellectual exploration, and social commentary, and reflect the
          central role of education in BAM/PFA's mission.</p><br />

        <p><b>The Pacific Film Archive Library and Film Study Center</b><br />
          is one of the major film reference services in the country.
          In addition to providing access to the more than 10,000 films
          and videos in the PFA collection, the library also makes a wide
          range of film-related materials available to the public for
          research purposes. Its collections include over 7,600 books,
          150 journal titles, 7,500 posters, 35,000 stills, and 1,500
          audiotapes of filmmakers who have appeared at the Pacific Film
          Archive, as well as screenplays, international film festival
          programs, and distributors' catalogs. The library's largest and
          most heavily used collection comprises some 95,000 documentation
          files (with over 230,000 individual items) on filmmakers,
          performers, national cinemas, genres, and other topics.</p><br />

        <p><b>CineFiles</b><br />
          In 1996, with support from the National Endowment for the Humanities,
          PFA initiated the CineFiles project to index and digitize materials
          from its documentation collection and make them freely available,
          with copyright holders' permissions, on the Internet.</p>

        <p>Types of documents available on CineFiles include film reviews,
          interviews, popular and scholarly articles, publicity materials,
          program notes, book excerpts, pamphlets, filmmakers' texts and
          correspondence, and many other rare archival documents dating from
          the early 1900s to the present. Grant support has enabled the PFA
          Library to prioritize the indexing and scanning of documents on the
          films of more than 150 major international
          <a href="Directors.jsp">directors</a>, materials describing

          <a href="/cinefiles/FilmSearch?country=Soviet_Union&year=<1931">
             silent Soviet cinema</a>, and PFA's unique collection of
             <a href="/cinefiles/DocSearch?doctype=26">exhibitor manuals.</a>
        </p><br />

        <p>The CineFiles film document image database now holds more than
          50,000 documents on film history. In-depth indexing is available
          on the Web for most documents, and page images or links are freely
          available for over 90% of them.  Page images for all documents can
          be viewed on site at the PFA Library or e-mailed on request; Please
          contact the PFA Library at
          <a href="/cinefiles/Contact.jsp">cinefiles@berkeley.edu</a>
          for more information.</p><br />

        <p><b>Current Project</b><br />
          In 2006, the PFA Library received a three-year grant from IMLS to
          expand the scope of CineFiles. To date, CineFiles has mostly been
          used by students and scholars in Film Studies, but with IMLS support
          PFA is expanding the range of users to include students and educators
          in History and the Social Sciences, since these are the areas where
          audio-visual aids are most often used to enrich learning. The goal
          is to add another 15,000 documents that have direct relevance to
          History/Social Science education to the database, and to develop a
          portal on the site for educators. The redesign of the CineFiles
          website was also made possible through the IMLS grant.</p><br />

        <p>If you are interested in seeing a title or director not
          currently represented within CineFiles, please send us an
          <a href="/cinefiles/Contact.jsp">email</a>. If we have
          any materials related to your interest in our files, we will
          prioritize the indexing and scanning of these documents.</p>
      </div>
    </div>
    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>
