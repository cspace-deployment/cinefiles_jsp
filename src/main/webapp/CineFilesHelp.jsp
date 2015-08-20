<?xml version="1.0" encoding="UTF-8" ?>

<%-- The Old-CineFiles "Help" page, with hints for improved searches. --%>

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
    <link rel='stylesheet' type='text/css' href='/cinefiles/css/help.css'/>

    <script src="/cinefiles/js/cine.js"
            type="text/javascript" language="JavaScript">
    </script>

    <script src="/cinefiles/js/search.js"
            type="text/javascript" language="JavaScript">
    </script>

    <title>Tips for Searching CineFiles</title>
  </head>
  <body>
    <jsp:include page="/include/pgheader.jspf" />

    <div id="container" name="container">

      <div id="searchside" name="searchside">
        <%@ include file="/include/search.jspf" %>
      </div>

      <div id="main" name="main">
        <h2>General notes on searching the database</h2>

        <p>You may enter information in as many fields as you wish;
       	 multiple entries will be searched as AND queries. For example,
        	 in the Filmographic Search form, entering "Czechoslovakia"
        	 in the Country field and "comedies" in the Genre field would
        	 retrieve a list of Czech comedies.
        </p><br />

        <p>Use the Search Type picklist to specify whether you would like
           the database to retrieve only records which exactly match the
           terms you enter, or a potentially broader list of records which
           contain a character string corresponding to the one you supply.
        </p><br />

        <p>You may also broaden your search by using the wildcard symbol
           within a particular field; the wildcard or truncation symbol is %.
           For example, typing "Smith%" in the Director field on the
           Filmographic Search form would retrieve films directed by
           Smith, Smithee, or Smithson, whereas an exact search on "Smith"
           (without the wildcard symbol) would only retrieve films directed
           by Smith. Exact searches are generally faster, but "contains" or
           wildcard searches may achieve more results.
        </p><br />

        <p>The database is case insensitive: you may type words or names
           in upper or lower case, and searching will not be affected.
        </p><br />

        <h2>About the Query Forms</h2>
        <p> Use the Filmographic Search  form to search for information
            about specific films; to find films by a certain director,
            from a particular country or time period, or associated with
            a subject or genre; or any combination of the above.
        </p><br />

        <p>Use the Document Search form to look for documents by title,
           author, date or publication; documents about specific films,
           people, or subjects; documents containing technical credits,
           production costs, or other categories of infomation pertaining
           to a film; or any combination of these.
        </p><br />

        <h2>Subject and genre terms</h2>
        <p>Genre terms in this database are from the Library of Congress
           publication "Moving Image Materials: Genre Terms," and include
           terms such as "personal/independent works," "animation,"
           "horror drama," and"comedies."
        </p><br />

        <p>Subject terms are taken from the Library of Congress Subject
           Headings. Using a "contains" or wildcard search to look for
           subjects will generally return better results, because otherwise
           the search term you enter must exactly match the Library of
           Congress heading.

           For example, an exact search for "United States -- History" will
           NOT retrieve records with the subject heading
           "United States -- History -- Civil War, 1861-1865 -- Drama,"
           whereas a "contains" or wildcard search would retrieve all records
           with subject headings containing the phrase "United States -- History."
        </p><br />

        <p>Note that a subject search in the Document Search form will only
           return documents which discuss that subject and NOT documents
           relating to all films about that subject. For example, a subject
           search for "United States -- History%" in the Filmographic Search
           form will return a list of films about U.S. history. A similar
           search in the Document Search form will only find documents which
           have U.S. history as a subject, and will NOT retrieve all reviews
           of films about U.S. history.
        </p><br />
      </div>
    </div>

    <jsp:include page="/include/pgfooter.jspf" />
  </body>
</html>
