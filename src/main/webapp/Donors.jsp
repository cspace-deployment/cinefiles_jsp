<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	     pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

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
          href='/cinefiles/css/donors.css' />

    <script src="cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/search.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Donors</title>
  </head>

  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container">

      <div id="searchside">
        <%@ include file="/include/search.jspf" %>

        <div id="donation">
          <h2>
            <a href="https://givetocal.berkeley.edu/egiving/index.cfm?Org=%20BAM%2FPFA%20CineFiles%20Project%20Fund&Fund=FU0968000">
            Support CineFiles</a>
          </h2>

          <p>All gifts, large and small, help us grow this
             relevant and important project. Thanks!</p>
          <br />
          <map name="donmap">
            <area shape="rect" coords="1, 1, 60, 18"
              href="https://givetocal.berkeley.edu/egiving/index.cfm?Org=%20BAM%2FPFA%20CineFiles%20Project%20Fund&Fund=FU0968000" />
          </map>

          <img src="/cinefiles/images/donate.png"
               usemap="#donmap" border="0" />
        </div>
      </div>

      <div id="main">
        <h2>Donors</h2>

        <br /><p>The CineFiles project has received support from the
        following donors over the years. BAM/PFA could not continue
        developing this database without such support, and it is
        highly appreciated. Thank you! If you are interested in
        supporting the CineFiles project, please click the "Give Now"
        button in the left-hand column on this page, or please write to
        <a href="mailto:cinefiles@berkeley.edu">cinefiles@berkeley.edu</a>.</p>

        <br /><p><b>$1,000 and above</b><br />
        Institute of Museum and Library Services<br />
        California State Library, Library Services and Technology Act<br />
        National Endowment for the Humanities<br />
        The Louis B. Mayer Foundation<br />
        Packard Humanities Institute<br />
        Margaret Parsons<br />
        Janet and David Peoples</p>

        <br /><p><b>$500 - $999</b><br />
        Malou Babelonie<br />
        Al Garren<br />
        Saul Zaentz</p>

        <br /><p><b>$200 - $499</b><br />
        Elizabeth Fleming<br />
        Leon Goldman<br />
        Carol Huang<br />
        Lindsay and Peter Joost<br />
        Richard Kwietniowski<br />
        Gregory C. Moore<br />
        Richard Pena<br />
        Susan Marinoff and Thomas F. Schrag<br />
        Elizabeth Shippey and Andrew Johnson<br />
        Evelyn W. and Gordon J. Wozniak</p>

        <br /><p><b>$100 - $199</b><br />
        Giovanna Ames Lilica and Kinsey Anderson<br />
        Gale Bailey<br />
        Eric Bertellotti<br />
        Tod Booth<br />
        Kevin Cassady<br />
        Camille A. Celluci<br />
        Marian Chapman and Sandra Springs<br />
        Carol Christ and Paul Alpers<br />
        Sarah Clark<br />
        Carol Clover<br />
        Greta G. and Ray A. de Groat<br />
        Francesca Eastman<br />
        David Thomson<br />
        Ralph Guggenheim<br />
        Liz Keim<br />
        Virginia King<br />
        Freda Kirkham<br />
        Ruby She Liu<br />
        Karen and Russell Merritt<br />
        Doris Morrison<br />
        Anne Nesbet and Eric Naiman<br />
        Jean E. and Guy T. Saperstein<br />
        Gail Silva<br />
        Scott Simmon<br />
        Frances and Randolph Starn<br />
        Cindy W. Yan</p>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
