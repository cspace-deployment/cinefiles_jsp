package edu.berkeley.mip.cinefiles.entity;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import edu.berkeley.mip.bean.CallableBean;

public class FilmDetail extends CallableBean {
   /*
    * FilmDetail A sub-class of CallableBean, it wraps a logical PFA film
    * record, representing the contents of a full Cinefiles film citation.
    * 
    * A useful instance of FilmDetail can be created with a film record id and a
    * javax.sql.DataSource.
    * 
    * Methods: public FilmDetail( DataSource dataSource, String filmId )
    * Constructor: builds a class using the DataSource to retrieve the database
    * record for the film indicated by "filmId".
    * 
    * protected void prepareCall() Overrides the prepareCall method in
    * CallableBean to set the expression that creates the CallableStatement
    * which will do the database lookup. For this bean the CallableStatement
    * should be defined to execute the stored procedure "film_detail_summary.
    * 
    * An SQLException will be thrown if this method is not overridden.
    * 
    * protected void processResults() Overrides the processResults method in
    * CallableBean. It does all the work of populating the attributes of
    * FilmDetail.
    * 
    * An SQLException will be thrown if this method is not overridden.
    * 
    * private void processResultSet() The CallableStatment returns multiple
    * ResultSets, so processResult() will will loop through the ResultSets and
    * call processResultSet for each one.
    * 
    * processResultSet will call one of the following, depending on which of the
    * ResultSets is the next to be processed.
    * 
    * private void processTitle() private void processDirectors() private void
    * processCountrys() private void processYears() private void
    * processLanguages() private void processProductionCo() private void
    * processGenres() private void processSubjects() private void
    * processRelatedDocs()
    * 
    * FilmDetail's public accessor methods. public int getId() public String
    * getTitle() public int[] getYears() public FilmDocs getRelatedDocs() public
    * ArrayList<String> getGenres() public ArrayList<String> getLanguages()
    * public ArrayList<String> getCountrys() public Hashtable<Integer, String>
    * getDirectors() public Hashtable<Integer, String> getProductionCos() public
    * Hashtable<Integer, String> getSubjects()
    */

   private static final long serialVersionUID = 1L;

   private String filmId;
   private String title;
   private ArrayList<String> years = new ArrayList<String>();

   private ArrayList<String> genres = new ArrayList<String>();
   private ArrayList<String> languages = new ArrayList<String>();
   private ArrayList<String> countries = new ArrayList<String>();

   private ArrayList<String[]> directors = new ArrayList<String[]>();
   private ArrayList<String[]> subjects = new ArrayList<String[]>();
   private ArrayList<String[]> prodcos = new ArrayList<String[]>();

   private FilmDocs relatedDocs = new FilmDocs();

   private static final String query = "{ call cinefiles_denorm.film_detail_summary(?, "
         + "'title', 'directors', 'countries', 'years', 'languages', "
         + "'prodco', 'genres', 'subjects', 'relatedocs')}";

   public FilmDetail() {
      super();
   }

   public FilmDetail(DataSource dataSource, String filmId) {
      super(dataSource);
      this.filmId = filmId;

      runQuery();
   }

   // Overrides the method in CallableBean (which only throws an SQLException).
   protected void prepareCall() throws SQLException {
      connection.setAutoCommit(false);
      stmt = connection.prepareCall(query);
      stmt.setString(1, filmId);
      errorMsg(query + " : " + filmId);
   }

   // Since there are multiple ResultSets we loop through the set of
   // ResultSets and pass each one off to a ResultSet handler
   protected void processResults() throws SQLException {
      ResultSet cursors = (ResultSet) stmt.getResultSet();
      rs = null;

      while (cursors.next()) {
         String content = cursors.getString(1);
         rs = (ResultSet) cursors.getObject(1);

         if (content.equals("title"))
            processTitle();
         else if (content.equals("directors"))
            processDirectors();
         else if (content.equals("countries"))
            processCountries();
         else if (content.equals("years"))
            processYears();
         else if (content.equals("languages"))
            processLanguages();
         else if (content.equals("genres"))
            processGenres();
         else if (content.equals("prodco"))
            processProdCos();
         else if (content.equals("subjects"))
            processSubjects();
         else if (content.equals("relatedocs"))
            processRelatedDocs();
         rs.close();
      }
      connection.setAutoCommit(true);
   }

   private void processTitle() throws SQLException {
      while (rs.next()) {
         title = getResultSetString("title", "Untitled");
      }
   }

   private void processDirectors() throws SQLException {
      while (rs.next()) {
         String id = getResultSetString("id");
         String director = getResultSetString("director");

         if (director.length() > 0) {
            String[] newdirector = new String[2];
            newdirector[0] = "" + id + "";
            newdirector[1] = director;
            directors.add(newdirector);
         }
      }
   }

   private void processCountries() throws SQLException {
      while (rs.next()) {
         String c = getResultSetString("country");
         if (c.length() > 1)
            countries.add(c);
      }
   }

   private void processYears() throws SQLException {
      while (rs.next()) {
         int y = getResultSetInt("year");
         if (y > 1900)
            years.add("" + y);
      }
   }

   private void processLanguages() throws SQLException {
      while (rs.next()) {
         String lang = getResultSetString("lang");
         if (lang.length() > 1)
            languages.add(lang);
      }
   }

   private void processProdCos() throws SQLException {
      while (rs.next()) {
         String id = getResultSetString("id");
         String prodco = getResultSetString("prodco");

         if (prodco.length() > 0) {
            String[] newprodco = new String[2];
            newprodco[0] = id;
            newprodco[1] = prodco;
            prodcos.add(newprodco);
         }
      }
   }

   private void processGenres() throws SQLException {
      while (rs.next()) {
         String genre = getResultSetString("genre");
         if (genre.length() > 1)
            genres.add(genre);
      }
   }

   private void processSubjects() throws SQLException {
      while (rs.next()) {
         int id = getResultSetInt("id");
         String subject = getResultSetString("subject");

         if ((id > 1) && (subject.length() > 0)) {
            String[] newsubj = new String[2];
            newsubj[0] = "" + id + "";
            newsubj[1] = subject;
            subjects.add(newsubj);
         }
      }
   }

   private void processRelatedDocs() throws SQLException {
      String id = null;
      String name_id = null;

      ArrayList<String[]> authors = new ArrayList<String[]>();

      while (rs.next()) {
         String next_id = getResultSetString("id");
         String nextname_id = getResultSetString("name_id");

         String author = getResultSetString("author");
         String[] nextauthor = { next_id, author };

         if (next_id == id) {
            authors.add(nextauthor);
            continue;
         }

         id = next_id;
         name_id = nextname_id;

         String title = getResultSetString("title");
         String type = getResultSetString("type");

         int pages = getResultSetInt("pages");
         String pg_info = getResultSetString("pg_info");
         String source = getResultSetString("source");

         String pubdate = getResultSetString("pubdate");
         int juliandate = getResultSetInt("juliandate");
         int access_code = getResultSetInt("code");
         String docurl = getResultSetString("docurl");

         authors = new ArrayList<String[]>();
         authors.add(nextauthor);

         relatedDocs.addFilmDoc(new FilmDoc(id, title, type, pages, pg_info,
               source, authors, pubdate, juliandate, access_code, docurl));
      }
   }

   public String getId() {
      return filmId;
   }

   public String getTitle() {
      return title;
   }

   public ArrayList<String> getYears() {
      return years;
   }

   public int getYearCount() {
      return years.size();
   }

   public ArrayList<String> getGenres() {
      return genres;
   }

   public int getGenreCount() {
      return genres.size();
   }

   public ArrayList<String> getLanguages() {
      return languages;
   }

   public int getLanguageCount() {
      return languages.size();
   }

   public ArrayList<String> getCountries() {
      return countries;
   }

   public int getCountryCount() {
      return countries.size();
   }

   public ArrayList<String[]> getDirectors() {
      return directors;
   }

   public int getDirectorCount() {
      return directors.size();
   }

   public ArrayList<String[]> getSubjects() {
      return subjects;
   }

   public int getSubjectCount() {
      return subjects.size();
   }

   public ArrayList<String[]> getProdCos() {
      return prodcos;
   }

   public int getProdCoCount() {
      return prodcos.size();
   }

   public FilmDocs getRelatedDocs() {
      return relatedDocs;
   }
}
