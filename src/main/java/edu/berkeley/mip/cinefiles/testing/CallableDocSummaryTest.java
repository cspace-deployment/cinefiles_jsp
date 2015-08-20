package edu.berkeley.mip.cinefiles.testing;

/*
 * CallableDocSummaryTest connects to a PostgreSQL server, using a simple
 * DataSource and calls the "doc_detail_summary" procedure, returning a set
 * of refcursors. It then loops through all of the refcursors, casting the
 * refcursor to a ResultSet and printing out each row in the ResultSet.  
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import edu.berkeley.mip.cinefiles.entity.FilmDetail;

public class CallableDocSummaryTest {

   FilmDetail filmDetail = null;

   public CallableDocSummaryTest() {
   }

   public static void main(String[] args) {
      CallableDocSummaryTest summaryTest = new CallableDocSummaryTest();
      if (summaryTest != null)
         System.out.println("Created summaryTest");

      DataSource dataSource = new PGDataSource().getDataSource();

      Connection conn = null;
      CallableStatement stmt = null;
      ResultSet cursors = null;
      ResultSet rs = null;

      String query = "{ call cinefiles_denorm.doc_detail_summary(?, "
            + "'document', 'doctitle', 'authors','source','doctype', "
            + "'lang','docsubj','docnamesubj','docfilmsubj','docurl')}";

      try {
         conn = dataSource.getConnection();
         conn.setAutoCommit(false);

         stmt = conn.prepareCall(query);
         stmt.setInt(1, 6099);
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
