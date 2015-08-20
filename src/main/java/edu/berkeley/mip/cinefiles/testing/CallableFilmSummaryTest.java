package edu.berkeley.mip.cinefiles.testing;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import edu.berkeley.mip.cinefiles.entity.FilmDetail;

/*
 * CallableFilmSummaryTest connects to a PostgreSQL server, using a simple
 * DataSource and calls the "film_detail_summary" procedure, returning a set
 * of refcursors. It then loops through all of the refcursors, casting the
 * cursor to a ResultSet and then printing out each row in the ResultSet.  
 */

public class CallableFilmSummaryTest {

   FilmDetail filmDetail = null;
   String filmId = "pfafilm95";

   public CallableFilmSummaryTest() {
   }

   public static void main(String[] args) {
      
      try {
          CallableFilmSummaryTest summaryTest = new CallableFilmSummaryTest();
          System.out.println( "Created new CallableFilmSummaryTest" );
          summaryTest.testCallable();
      } catch (Exception e ) {
         System.out.println( "Failed to create new CallableFilmSummaryTest");
         System.out.println( e.getMessage() );
         return;
      }
   }
   
   
   public void testCallable() {
      DataSource dataSource = new PGDataSource().getDataSource();

      Connection conn = null;
      CallableStatement stmt = null;
      ResultSet cursors = null;
      ResultSet rs = null;

//      String query = "{ call cinefiles_denorm.film_detail_summary(?, "
//            + "'title', 'directors', 'countries', 'years', 'languages', "
//            + "'prodco', 'genres', 'subjects', 'relatedocs')}";
      
      String query = "{ call cinefiles_denorm.film_detail_summary(?, "
            + "'title', 'directors', 'countries', 'years', 'languages', "
            + "'prodco', 'genres', 'subjects', 'relatedocs')}";
      
      try {
         conn = dataSource.getConnection();
         conn.setAutoCommit(false);

         stmt = conn.prepareCall(query);
         stmt.setString(1, filmId );
         stmt.execute();
         cursors = (ResultSet) stmt.getResultSet();

         while (cursors.next()) {
            System.out.println("Got " + cursors.getString(1));
            rs = (ResultSet) cursors.getObject(1);
            int colcount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
               System.out.print(rs.getString(1) + ", " + colcount + " columns");
               for (int i = 2; i <= colcount; i++)
                  System.out.print(" " + rs.getString(i));

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
