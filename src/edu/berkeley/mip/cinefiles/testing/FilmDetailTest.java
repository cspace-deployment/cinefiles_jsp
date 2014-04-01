package edu.berkeley.mip.cinefiles.testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import edu.berkeley.mip.cinefiles.entity.FilmDetail;
import edu.berkeley.mip.cinefiles.entity.FilmDoc;
import edu.berkeley.mip.cinefiles.entity.FilmDocs;

/*
 * FilmDetailTest instantiates a new FilmDetail class and calls all of its
 * getter methods, printing out the returned values.
 * 
 * Run it as a java application with the instance variable "filmId" set 
 * to some valid film id.
 */

public class FilmDetailTest {
   
   String filmId = "pfafilm95";
   FilmDetail filmDetail = null;
   DataSource dataSource = null;
   
   public FilmDetailTest() {
   }

   public static void main(String[] args) {
      FilmDetailTest filmDetailTest = new FilmDetailTest();

      //System.out.println("Testing the DataSource, querying allfilmtitles_view, using pfafilm95.");
      //filmDetailTest.testDataSource();
      //System.out.println("Done testing the DataSource\n");

      //System.out.println("Testing the procedure call, using pfafilm95.");
      //filmDetailTest.testProcCall();
      //System.out.println("Done testing the procedure call\n");

      System.out.println("Testing FilmDetail");
      filmDetailTest.testFilmDetail();
      System.out.println("Done testing FilmDetail\n");
   }

   private DataSource getDataSource() {
      if (dataSource == null)
         dataSource = new PGDataSource().getDataSource();
      return dataSource;
   }

   public void testFilmDetail() {
      if ( dataSource == null ) {
         getDataSource();
      }
   
      if (dataSource == null)
          return;
      
      filmDetail = new FilmDetail(dataSource, filmId);

      System.out.print("filmDetail:"
            + ((filmDetail == null) ? " is NULL." : " instance of "
                  + filmDetail.getClass().getName()));
      System.out.println(filmDetail.getId());
      
      System.out.println("Found title: " + filmDetail.getTitle());

      System.out.println("Found " + filmDetail.getDirectorCount() + " directors." );
      if( filmDetail.getDirectorCount() > 0 ) {
         ArrayList <String[]> directors = filmDetail.getDirectors();
         for( int i = 0; i < directors.size(); i++ ) {
            String[] d = (String[])directors.get(i);
            System.out.println( "Found director: " + d[1] );
         }
      }
            
      System.out.println("Found " + filmDetail.getCountryCount() + " countries." );
      if( filmDetail.getCountryCount() > 0 ) {
         ArrayList <String> countries = filmDetail.getCountries();
         for( int i = 0; i < countries.size(); i++ ) {
            System.out.println( "Found country: " + (String)countries.get(i) );
         }
      }
       
      System.out.println("Found " + filmDetail.getGenreCount() + " genres." );
      if( filmDetail.getGenreCount() > 0 ) {
         ArrayList <String> genres = filmDetail.getGenres();
         for( int i = 0; i < genres.size(); i++ ) {
            System.out.println( "Found genre: " + (String)genres.get(i) );
         }
      }
      
      System.out.println("Found " + filmDetail.getYearCount() + " years.");
      if( filmDetail.getYearCount() > 0 ) {
         ArrayList <String> years = filmDetail.getYears();
         for( int i = 0; i < years.size(); i++ ) {
            System.out.println( "Found year: " + (String)years.get(i) );
         }
      }
      
      System.out.println("Found " + filmDetail.getLanguageCount() + " languages.");
      if( filmDetail.getLanguageCount() > 0 ) {
         ArrayList <String> languages = filmDetail.getLanguages();
         for( int i = 0; i < languages.size(); i++ ) {
            System.out.println( "Found language: " + (String)languages.get(i) );
         }
      }
      
      System.out.println("Found " + filmDetail.getProdCoCount() + " prodcos.");
      if( filmDetail.getProdCoCount() > 0 ) {
         ArrayList<String[]>prodcos = filmDetail.getProdCos();
         for( int i = 0; i < prodcos.size(); i++ ) {
            System.out.println( "Found prodco: " + prodcos.get(i)[1] );
         }
      }
      
      System.out.println("Found " + filmDetail.getSubjectCount() + " subjects.");
      if( filmDetail.getSubjectCount() > 0 ) {
         ArrayList<String[]>subjects = filmDetail.getSubjects();
         for( int i = 0; i < subjects.size(); i++ ) {
            System.out.println( "Found subject: " + subjects.get(i)[1] );
         }
      }

      FilmDocs reldocs = filmDetail.getRelatedDocs();
      if( reldocs != null ) {
         ArrayList <FilmDoc> filmdocs = reldocs.getFilmDocs();
         for( int i = 0; i < filmdocs.size(); i++ ) {
            System.out.println( "FilmDoc Title: " + filmdocs.get(i).getTitle() );
         }
      }
   }

   public void testDataSource() {
      DataSource dataSource = null;
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;

      String query = "select film_id, title"
            + "  from cinefiles_denorm.allfilmtitles_view"
            + "  where film_id = 'pfafilm95'";
      
      try {
         dataSource = getDataSource();
         conn = dataSource.getConnection();
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            System.out.print("ID = " + rs.getString("film_id"));
            System.out.println(" Title = " + rs.getString("title"));
         }
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getMessage());
         System.exit(1);
      } finally {
         if (rs != null)
            try {
               rs.close();
               rs = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         if (stmt != null)
            try {
               stmt.close();
               //"{ call cinefiles_denorm.film_detail_summary(?, 'a','b','c','
               stmt = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         if (conn != null)
            try {
               conn.close();
               conn = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
      }
   }

   public void testProcCall() {
      DataSource dataSource = getDataSource();
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      String query = "select * from cinefiles_denorm.film_detail_summary(?,"
          + " 'title', 'directors', 'countries', 'years', 'languages', " 
          + " 'prodco', 'genres', 'subjects', 'relatedocs')";
      
      try {
         conn = dataSource.getConnection();
         conn.setAutoCommit(false);

         stmt = conn.prepareStatement(query);
         stmt.setString(1, "pfafilm95");

         rs = stmt.executeQuery();

         while (rs.next()) {
            System.out.println("Got " + rs.getString(1));
            ResultSet cursor = (ResultSet) rs.getObject(1);
            int colcount = cursor.getMetaData().getColumnCount();
            while (cursor.next()) {
               System.out.print(cursor.getString(1) + ", " + colcount
                     + " columns");
               for (int i = 2; i <= colcount; i++)
                  System.out.print(" : " + cursor.getString(i));
               System.out.println("");
            }
         }
         conn.setAutoCommit(true);
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getMessage());
         System.exit(1);
      } finally {
         if (rs != null)
            try {
               rs.close();
               rs = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         if (stmt != null)
            try {
               stmt.close();
               stmt = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         if (conn != null)
            try {
               conn.close();
               conn = null;
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
      }
   }
}
