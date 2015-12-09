package edu.berkeley.mip.cinefiles.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

public class DocDetail extends edu.berkeley.mip.bean.CallableBean {
   /*
    * DocDetail A sub-class of CallableBean, it wraps a logical PFA document
    * record, representing the contents of a full Cinefiles document citation.
    * 
    * A useful instance of DocDetail can be created with a document record id
    * and a javax.sql.DataSource.
    * 
    * Methods: public DocDetail( DataSource dataSource, int docId ) Constructor:
    * builds a class using the DataSource to retrieve the database record for
    * the document indicated by "docId".
    * 
    * protected void prepareCall() Overrides the prepareCall method in
    * CallableBean to set the expression that creates the CallableStatement
    * which will do the database lookup. For this bean the CallableStatement
    * should be defined to execute the stored procedure "doc_detail_summary.
    * 
    * An SQLException will be thrown if this method is not overridden.
    * 
    * protected void processResults() Overrides the processResults method in
    * CallableBean. It does all the work of populating the attributes of
    * DocDetail.
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
    * private void processDocument() private void processTitle() private void
    * processSource() private void processType() private void processDocUrl()
    * private void processAuthors() private void processLanguages() private void
    * processSubjects() private void processNameSubjects() private void
    * processFilmSubjects()
    * 
    * DocDetail's public accessor methods.
    * 
    * public int getDocId() public int getPages() public String getPagination()
    * public String getTitle() public String getDocUrl() public String
    * getSource() public String getType() public String getNote() public int
    * getSrcId() public String getSrcUrl() public String getDate() public
    * boolean[] getIncludes() public boolean getInclude( String key ) public
    * ArrayList<String> getLanguages() public Hashtable<Integer, String>
    * getAuthors() public Hashtable<Integer, String> getSubjects() public
    * Hashtable<Integer, String> getNameSubjects() public Hashtable<Integer,
    * String> getFilmSubjects()
    */

   private static final long serialVersionUID = 1L;

   private int docId;
   private String srcId;
   private int pages;
   private int accessCode;
   private String title;
   private String source;
   private String srcUrl;
   private String date;
   private String docType;
   private String pagination;
   private String note;
   private String docUrl;
   private boolean[] includes = new boolean[8];
   private ArrayList<String> languages = new ArrayList<String>();
   private List<String> authors = new ArrayList<String>();
   private Hashtable<Integer, String> subjects = new Hashtable<Integer, String>();
   private Hashtable<Integer, String> nameSubjects = new Hashtable<Integer, String>();
   private Hashtable<String, String> filmSubjects = new Hashtable<String, String>();
   private static final String query = "{ "
         + "call cinefiles_denorm.doc_detail_summary(?, "
         + "'document', 'doctitle', 'authors','source','doctype', "
         + "'lang','docsubj','docnamesubj','docfilmsubj','docurl')}";

   public DocDetail() {
      super();
   }

   // The only Constructor that will ever be used - A DataSource and a document
   // Id
   // are required to do anything useful.
   public DocDetail(DataSource dataSource, String db, int docId) {
      super(dataSource);
      this.docId = docId;

      runQuery();
   }

   // Overrides the method in CallableBean (which only throws an SQLException).
   protected void prepareCall() throws SQLException {
      connection.setAutoCommit(false);
      stmt = connection.prepareCall(query);
      stmt.setInt(1, docId);
      errorMsg(query + " : " + docId);
   }

   // Overrides the method in CallableBean (which only throws an SQLException).
   protected void processResults() throws SQLException {
      ResultSet cursors = (ResultSet) stmt.getResultSet();
      rs = null;

      while (cursors.next()) {
         String content = cursors.getString(1);
         rs = (ResultSet) cursors.getObject(1);

         if (content.equals("doctitle"))
            processTitle();
         else if (content.equals("source"))
            processSource();
         else if (content.equals("doctype"))
            processType();
         else if (content.equals("docurl"))
            processDocUrl();
         else if (content.equals("lang"))
            processLanguages();
         else if (content.equals("authors"))
            processAuthors();
         else if (content.equals("docsubj"))
            processSubjects();
         else if (content.equals("docnamesubj"))
            processNameSubjects();
         else if (content.equals("docfilmsubj"))
            processFilmSubjects();
         else if (content.equals("document"))
            processDocument();
      }
      connection.setAutoCommit(true);
   }

   // Called by processResultSet when the next ResultSet is doc set.
   private void processDocument() throws SQLException {
      while( rs.next() ){
      pagination = getResultSetString("pg_info");
      pages = getResultSetInt("pages");
      accessCode = getResultSetInt("code");
      date = getResultSetString("date");
      note = getResultSetString("note");

      // Process includes
      includes[0] = (getResultSetInt("cast_cr") == 1);
      includes[1] = (getResultSetInt("tech_cr") == 1);
      includes[2] = (getResultSetInt("bx_info") == 1);
      includes[3] = (getResultSetInt("filmog") == 1);
      includes[4] = (getResultSetInt("dist_co") == 1);
      includes[5] = (getResultSetInt("prod_co") == 1);
      includes[6] = (getResultSetInt("cost") == 1);
      includes[7] = (getResultSetInt("illust") == 1);
      }
   }

   // Called by processResultSet when the next ResultSet is a formatted title.
   private void processTitle() throws SQLException {
      while (rs.next())
         title = getResultSetString("title", "Untitled");
   }

   // Called by processResultSet when the next ResultSet is a document Source.
   private void processSource() throws SQLException {
      while (rs.next()) {
         srcId = getResultSetString("src_id");
         source = getResultSetString("source");
         srcUrl = getResultSetString("srcUrl");
      }
   }

   // Called by processResultSet when the next ResultSet is a document Type.
   private void processType() throws SQLException {
      while (rs.next())
         docType = getResultSetString("type");
   }

   // Called by processResultSet when the next ResultSet is a document URL.
   private void processDocUrl() throws SQLException {
      while (rs.next())
         docUrl = getResultSetString("docurl");
   }

   // Called by processResultSet when the next ResultSet is a list of authors.
   private void processAuthors() throws SQLException {
      while (rs.next()) {
         String author = getResultSetString("author");

         if (author.length() > 0) {
            authors.add(author);
         }
      }
   }

   // Called by processResultSet when the next ResultSet is a list of languages.
   private void processLanguages() throws SQLException {
      while (rs.next()) {
         String lang = getResultSetString("lang");
         if (lang.length() > 1)
            languages.add(lang);
      }
   }

   // Called by processResultSet when the next ResultSet is a list of Subjects.
   private void processSubjects() throws SQLException {
      int id = 0;
      while (rs.next()) {
         id += getResultSetInt("subj_id");
         String subject = getResultSetString("subj");

         if (subject.length() > 0)
            subjects.put(new Integer(id), subject);
      }
   }

   // Called by processResultSet when the next ResultSet is a list of Name
   // Subjects.
   private void processNameSubjects() throws SQLException {
      int id = 0;
      while (rs.next()) {
         id += getResultSetInt("name_id");
         String subject = getResultSetString("namesubj");

         if (subject.length() > 0)
            nameSubjects.put(new Integer(id), subject);
      }
   }

   // Called by processResultSet when the next ResultSet is a list of Film
   // Subjects.
   private void processFilmSubjects() throws SQLException {
      while (rs.next()) {
         String id = getResultSetString("film_id");
         String subject = getResultSetString("filmsubj");

         if (subject.length() > 0)
            filmSubjects.put(id, subject);
      }
   }

   public int getDocId() {
      return docId;
   }

   public String getTitle() {
      return title;
   }

   public String getSource() {
      return source;
   }

   public String getDocUrl() {
      return docUrl;
   }

   public String getSrcId() {
      return srcId;
   }

   public String getSrcUrl() {
      return srcUrl;
   }

   public String getDate() {
      return date;
   }

   public boolean[] getIncludes() {
      return includes;
   }

   public boolean getInclude(String k) {
      if (k.equals("cast_cr"))
         return includes[0];
      else if (k.equals("tech_cr"))
         return includes[1];
      else if (k.equals("bx_info"))
         return includes[2];
      else if (k.equals("filmog"))
         return includes[3];
      else if (k.equals("dist_co"))
         return includes[4];
      else if (k.equals("prod_co"))
         return includes[5];
      else if (k.equals("cost"))
         return includes[6];
      else if (k.equals("illust"))
         return includes[7];

      return false;
   }

   public List<String> getAuthors() {
      return authors;
   }

   public String getAuthorString() {
      String authorString = "";
      Enumeration<String> a = Collections.enumeration(authors);

      int c = 0;

      while (a.hasMoreElements() && (c < 3)) {
         if (c == 0)
            authorString = a.nextElement();
         if (c == 1)
            authorString += "; " + a.nextElement();
         if (c == 2)
            authorString += "; <i>et al.</i>";
         c++;
      }

      return authorString;
   }

   public int getAuthorCount() {
      return authors.size();
   }

   public Hashtable<Integer, String> getSubjects() {
      return subjects;
   }

   public int getSubjectCount() {
      return subjects.size();
   }

   public Hashtable<Integer, String> getNameSubjects() {
      return nameSubjects;
   }

   public int getNameSubjectCount() {
      return nameSubjects.size();
   }

   public Hashtable<String, String> getFilmSubjects() {
      return filmSubjects;
   }

   public int getFilmSubjectCount() {
      return filmSubjects.size();
   }

   public ArrayList<String> getLanguages() {
      return languages;
   }

   public int getLanguageCount() {
      return languages.size();
   }

   public int getPages() {
      return pages;
   }
   
   public int getAccessCode() {
      return accessCode;
   }

   public String getPagination() {
      return pagination;
   }

   public String getType() {
      return docType;
   }

   public String getNote() {
      return note;
   }
}
