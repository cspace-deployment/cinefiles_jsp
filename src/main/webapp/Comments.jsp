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
          href='/cinefiles/css/comments.css' />

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
        <h4>Send your comments to CineFiles</h4>

        <br /><hr />
        We would like hear from you.  Please send us your comments using
        the from below.  For those using browsers that do not support forms,
        send email directly to <i>cinefiles@berkeley.edu</i>
        <hr /><br />

        <form method="post" action="/cinefiles/Comments">
          <table>
           <tr><td colspan="2"><b>Please enter:</b>  (optional)</td></tr>
           <tr><td></td><td></td></tr>

           <tr>
             <td>Name</td><td><input type="text" name="userName" /></td>
           </tr>

           <tr>
             <td>Phone</td><td><input type="text" name="phoneNum" /></td>
           </tr>

           <tr>
             <td>Fax</td><td><input type="text" name="faxNum" /></td>
           </tr>

           <tr>
             <td>E-Mail</td><td><input type="text" name="userEmail" /></td>
           </tr>
          </table>


          <br /><p>Please enter your comments in the space below:<p />
          <textarea name="comments" rows="10" cols="75">&#160;</textarea>
          <p />

          <input type="hidden" name="src" value="dev" />
          <input type="submit" value="Send comments" />
          <input type="reset" value="Erase Form" />
        </form>
      </div>
    </div>

    <%-- <jsp:include page="/include/pgfooter.jspf" /> --%>
    <%@ include file="/include/pgfooter.jspf" %>
  </body>
</html>
