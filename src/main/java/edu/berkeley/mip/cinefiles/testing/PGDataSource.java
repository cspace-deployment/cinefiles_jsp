package edu.berkeley.mip.cinefiles.testing;

import java.sql.Connection;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
/*
 * A simple DataSource provider. Can be used to emulate a servlet container's DataSource 
 * when testing bean classes outside of the container.
 */
 
public class PGDataSource {

   private static final String dbserver = "cinefiles-dev.cspace.berkeley.edu";
   private static final int port = 5432;
   private static final String db = "cinefiles_domain";
   private static final String passwd = "csR2p4rt2r";
   private static final String user = "reporter";
   private PGSimpleDataSource dataSource = null;
   private Connection conn = null;

   public PGDataSource() {
      try {
         dataSource = new org.postgresql.ds.PGSimpleDataSource();
         dataSource.setServerName(dbserver);
         dataSource.setDatabaseName(db);
         dataSource.setPortNumber(port);
         dataSource.setPassword(passwd);
         dataSource.setUser(user);
      } catch (Exception e) {
         System.err.println("PGDataSource: Failed to create Connection");
         System.err.println(e.getMessage());
         if (conn != null)
            try {
               conn.close();
               conn = null;
            } catch (Exception e2) {
            }
      }
   }

   public DataSource getDataSource() {
      return dataSource;
   }
   
   public Connection getConnection() {
      try {
         return dataSource.getConnection();
      }catch(Exception e){
         return null;
      }
   }
}
