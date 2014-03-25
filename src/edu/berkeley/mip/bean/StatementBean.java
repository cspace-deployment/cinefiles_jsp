package edu.berkeley.mip.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class StatementBean extends GenericBean implements Serializable
{
  /*
   * StatementBean extends GenericBean using a java.sql.Statement
   * 
   * This is the superclass for Entity Beans that use a Statement object
   * to execute database lookups. See also: CallableBean and PreparedBean
   * 
   * It creates the necessary connection components and then calls the 
   * method "runQuery" to do a database query, returning 1 or more ResultSets.
   * Then it calls "processResults to handle the returned data.
   * "processResults" must be overridden by each StatementBean subclass.
   * 
   * Methods
   * public StatementBean()
   * The mandatory no-arg Constructor, calls super();
   * 
   * public StatementBean( DataSource dataSource )
   * The only useful Constructor, calls super( dataSource );
   * 
   * public runQuery( String sql )
   * Calls runQuery( sql, true )
   * 
   * public runQuery( String sql, boolean singleResultSet )
   * Sends the sql query string to the DBMS. If singleResultSet is true,
   * it uses Statement.executeQuery( sql ) and returns a single ResultSet,
   * else it uses Statement.execute( sql ) returning multiple ResultSets.
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
 
 private static final long serialVersionUID = 1L;
 
 protected Statement stmt = null;
 
 public StatementBean()
 {
   super();  
 }
 
 public StatementBean( DataSource dataSource )
 {
    super( dataSource );
 }

 public void runQuery( String sql )
 {
    runQuery( sql, true );
 }
  
 public void runQuery( String sql, boolean singleResultSet )
 {
    connection = null;
    stmt = null;
    
    try
    {
      connection = dataSource.getConnection();

      if( connection == null )
          errorMsg( "runQuery: no Connection" );
      else if( sql == null )
          errorMsg( "runQuery: null SQL query string" );
      else
      {
        stmt = connection.createStatement();
        useDatabase();

        if( singleResultSet )
           rs = stmt.executeQuery( sql );
        else
           stmt.execute( sql );

        processResults();
      }
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
         errorMsg( "runQuery - finally: Statment not closed" );
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

