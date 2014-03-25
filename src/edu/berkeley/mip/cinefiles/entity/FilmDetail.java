package edu.berkeley.mip.cinefiles.entity;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import edu.berkeley.mip.bean.CallableBean;

public class FilmDetail extends CallableBean
{
   /*
    * FilmDetail A sub-class of CallableBean, it wraps a logical PFA film
    * record, representing the contents of a full Cinefiles film citation.
    * 
    * A useful instance of FilmDetail can be created with a film record id and a
    * javax.sql.DataSource.
    * 
    * Methods: public FilmDetail( DataSource dataSource, int filmId )
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

   private int filmId;
   private String title;
   private ArrayList <String> years = new ArrayList <String>();

   private ArrayList <String> genres = new ArrayList <String>();
   private ArrayList <String> languages = new ArrayList <String>();
   private ArrayList <String> countries = new ArrayList <String>();

   private ArrayList <String[]> directors = new ArrayList <String[]>();
   private ArrayList <String[]> subjects = new ArrayList <String[]>();
   private ArrayList <String[]> prodcos = new ArrayList <String[]>();

   private FilmDocs relatedDocs = new FilmDocs();

   private static final String query = "{call film_detail_summary (?)}";
   private static final String db = "cinefiles";

   public FilmDetail()
   {
      super();
   }

   public FilmDetail( DataSource dataSource, int filmId )
   {
      super( dataSource );
      this.filmId = filmId;
      this.dataBase = db;

      runQuery();
   }

   // Overrides the method in CallableBean (which only throws an SQLException).
   protected void prepareCall() throws SQLException
   {
      stmt = connection.prepareCall( query );
      stmt.setInt( 1, filmId );
      errorMsg( query + " : " + filmId );
   }

   // Since there are multiple ResultSets, there are two methods
   // One loops through all of the ResultSets and the other gets
   // passsed the individual ResultSets.
   protected void processResults() throws SQLException
   {
      rs = stmt.getResultSet();

      while( ( rs == null )
            && ( stmt.getMoreResults() || ( stmt.getUpdateCount() != -1 ) ) )
      {
         rs = stmt.getResultSet();
      }

      if( rs == null )
         this.filmId = -1;

      while( rs != null )
      {
         processResultSet();
         rs.close();
         rs = null;

         while( rs == null
               && ( stmt.getMoreResults() || ( stmt.getUpdateCount() != -1 ) ) )
         {
            rs = stmt.getResultSet();
         }
      }
   }

   // Called by processResults for each ResultSet returned.
   // It acts as a switch, sending the ResultSet onto its
   // correct handler.
   private void processResultSet()
   {
      try
      {
         while( rs.next() )
         {
            String content = getResultSetString( "Content" );
            errorMsg( "processResultSet: Content == " + content );

            if( content.equals( "Title" ))
               processTitle();
            else if( content.equals( "Directors" ))
               processDirectors();
            else if( content.equals( "Countries" ))
               processCountries();
            else if( content.equals( "Years" ))
               processYears();
            else if( content.equals( "Languages" ))
               processLanguages();
            else if( content.equals( "Production Co" ))
               processProdCos();
            else if( content.equals( "Genres" ))
               processGenres();
            else if( content.equals( "Subjects" ))
               processSubjects();
            else if( content.equals( "Related Docs" ))
               processRelatedDocs();
         }
      }
      catch( SQLException e )
      {
         errorMsg( "processResltSet: " + e.getMessage() );
      }
   }
   
   private void processTitle()
   {
      title = getResultSetString( "title", "Untitled" ); 
   }
   
   private void processDirectors() throws SQLException
   {
      do
      {
         int id = getResultSetInt( "id" );
         String director = getResultSetString( "director" );
         
         if(( id > 1 ) && ( director.length() > 0 ))
         {
            String[] newdirector = new String[2];
            newdirector[0] = "" + id + "";
            newdirector[1] = director;
            directors.add( newdirector );
         }
      } while( rs.next() );
   }
  
   private void processCountries() throws SQLException
   {
      do
      {
         String c = getResultSetString( "country" );         
         if( c.length() > 1 )
            countries.add( c );
      } while( rs.next() );
   }
   
   private void processYears() throws SQLException
   {      
      do
      {
        int y = getResultSetInt( "year" );        

        if( y > 1900 )
           years.add( "" + y );        
      } while( rs.next() );
   }
   
   private void processLanguages() throws SQLException
   {
      do
      {
         String lang = getResultSetString( "lang" );
         if( lang.length() > 1 )
            languages.add( lang );      
      } while( rs.next() );
   }
   
   private void processProdCos() throws SQLException
   {
      do
      {
         int id = getResultSetInt( "id" );
         String prodco = getResultSetString( "prodco" );
         
         if(( id > 1 ) && ( prodco.length() > 0 ))
         {
            String[] newprodco = new String[2];
            newprodco[0] = "" + id;
            newprodco[1] = prodco;
            prodcos.add( newprodco );
         }
      } while( rs.next() );
   }
   
   private void processGenres() throws SQLException
   {      
      do
      {
         String genre = getResultSetString( "genre" );
         if( genre.length() > 1 )
            genres.add( genre );      
      } while( rs.next() );
   }
   
   private void processSubjects() throws SQLException
   {
     do
     {
        int id = getResultSetInt( "id" );
        String subject = getResultSetString( "subject" );
        
        if(( id > 1 ) && ( subject.length() > 0 ))
        {
           String[] newsubj = new String[2];
           newsubj[0] = "" + id + "";
           newsubj[1] = subject;
           subjects.add( newsubj );
        }
     } while( rs.next() );
   }
   
   private void processRelatedDocs() throws SQLException
   {
			int id = 0;
			int name_id = 0;
			
      ArrayList<String[]> authors = new ArrayList<String[]>();
      
      do
      {
    	   int next_id = getResultSetInt( "id" );
         int nextname_id = getResultSetInt( "name_id" );
         
         String author = getResultSetString( "author" );
         String[] nextauthor = { "" + next_id, author };
          
         if( next_id == id )
         {
       	   authors.add( nextauthor );
       	   continue;
         }
         
         id = next_id;
         name_id = nextname_id;
         
         String title = getResultSetString( "title" );
         String type = getResultSetString( "type" );
                  
         int pages = getResultSetInt( "pages" );
         String pg_info = getResultSetString( "pg_info" );
         String source = getResultSetString( "source" );
    
         String pubdate = getResultSetString( "pubdate" );
         int juliandate = getResultSetInt( "juliandate" );
         int access_code = getResultSetInt( "code" );
         String docurl = getResultSetString( "docurl" );
   		
         authors = new ArrayList<String[]>();
         authors.add( nextauthor );
         
         relatedDocs.addFilmDoc( new FilmDoc( id, title, type, pages, pg_info,
         source, authors, pubdate, juliandate, access_code, docurl ));
      } while( rs.next() );
   }
   
   public int getId()
   {
      return filmId;
   }
   
   public String getTitle()
   {
      return title;
   }
   
   public ArrayList <String> getYears()
   {
      return years;
   }
   
   public int getYearCount()
   {
     return years.size();
   }

   public ArrayList <String> getGenres()
   {
      return genres;
   }
   
   public int getGenreCount()
   {
     return genres.size();
   }
   
   public ArrayList <String> getLanguages()
   {
      return languages;
   }
  
   public int getLanguageCount()
   {
     return languages.size();
   }
   
   public ArrayList <String> getCountries()
   {
      return countries;
   }
   
   public int getCountryCount()
   {
     return countries.size();
   }

   public ArrayList <String[]> getDirectors()
   {
      return directors;
   }
   
   public int getDirectorCount()
   {
     return directors.size();
   }
   
   public ArrayList <String[]> getSubjects()
   {
      return subjects;
   }
   
   public int getSubjectCount()
   {
     return subjects.size();
   }
   
   public ArrayList <String[]> getProdCos()
   {
      return prodcos;
   }
   
   public int getProdCoCount()
   {
     return prodcos.size();
   }
   
   public FilmDocs getRelatedDocs()
   {
      return relatedDocs;
   }
}
