package edu.berkeley.mip.bean;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


public class PreparedBean extends GenericBean implements Serializable
{
  /*
   * PreparedBean extends GenericBean using a java.sql.PreparedStatement
   * 
   * This is the superclass for Entity Beans using PreparedStatement objects
   * to execute database lookups. See also: CallableBean and StatementBean
   * 
   * It creates the necessary connection components, using a JNDI DataSource,
   * calls the "prepareStatement" method to initialize a PreparedStatment and
   * then calls "runQuery" to perform a database query, returning 1 or more
   * ResultSets.
   * 
   * "prepareStatement" must be overridden by each PreparedBean subclass.
   * 
   * Then it calls "processResults to handle the returned data.

   * "processResults" must be overridden by each PreparedBean subclass.
   * 
   * And finally, it closes all connection components.
   * 
   * Methods
   * public CallableBean()
   * The mandatory no-arg Constructor, calls super();
   * 
   * public CallableBean( DataSource dataSource )
   * The only useful Constructor, calls super( dataSource );
   * 
   * public runQuery()
   * calls runQuery( true )
   * 
   * public runQuery( boolean multipleResultSets )
   * Sends an SQL query to the DBMS. If multipleResultSets is true, it will use
   * PreparedStatement.execute() which returns multiple ResultSets, else it will
   * use PreparedStatement.executeQuery(), returning a single ResultSet.
   * 
   * The overloaded method "processResults" will need to deal with the two
   * scenarios appropriately.
   * 
   * Methods inherited from GenericBean:
   * public void errorMessage( String msg )
   * public ArrayList<String> getErrorMsgs() 
   * public void setDatabase( String dataBase )
   * protected void useDatabase()
   * protected void prepareStatement()
   * protected void prepareCall()
   * protected void processResults() - must be overridden
   * public String getFormattedTimestamp()
   * public int getResultSetInt( String key )
   * public int getResultSetInt( String key, int defvalue )
   * public String getResultSetString( String key )
   * public String getResultSetString( String key, String defvalue )
   */

   static final long serialVersionUID =  1L;

   protected PreparedStatement stmt = null;
   
   public PreparedBean()
   {
      super();
   }

   public PreparedBean( DataSource dataSource )
   {
      super( dataSource );
   }
   
   public void runQuery()
   {
      runQuery( true );
   }
   
   public void runQuery( boolean multipleResultSets )
   {
      connection = null;
      stmt = null;
      
      try
      {
        connection = dataSource.getConnection();

        if( connection != null )
        {
          useDatabase();
          prepareStatement();
          
          if( multipleResultSets )
             stmt.execute();
          else
             stmt.executeQuery();
          
          processResults();
        }
        else
           errorMsg( "runQuery: no Connection" );
      }
      catch( SQLException e )
      {
        errorMsg( "runQuery Exception: " + e.getMessage() );
      }
      finally
      {
        try
        { if( rs != null ) rs.close(); }
        catch( SQLException e )
        {
           errorMsg( "runQuery - finally: ResultSet not closed");
        }
        rs = null;

        try
        { if( stmt != null ) stmt.close(); }
        catch( SQLException e )
        {
           errorMsg( "runQuery - finally: PreparedStatment not closed" );
        }
        stmt = null;

        try
        { if( connection != null ) connection.close(); }
        catch( SQLException e )
        {
           errorMsg( "runQuery - finally: Connection not closed" );
        }
        connection = null;
      }
   }
}
