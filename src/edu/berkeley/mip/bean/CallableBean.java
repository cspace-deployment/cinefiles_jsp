package edu.berkeley.mip.bean;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class CallableBean extends GenericBean implements Serializable
{   
  /*
   * CallableBean extends GenericBean using a java.sql.CallableStatement
   * 
   * This is the superclass for Entity Beans using CallableStatement objects
   * to execute database lookups. See also: PreparedBean and StatementBean
   * 
   * It creates the necessary connection components, using a JNDI DataSource,
   * calls the "prepareCall" method to initialize a CallableStatment and then
   * calls "runQuery" to perform a database query, returning 1 or more
   * ResultSets.
   * 
   * "prepareCall" must be overridden by each CallableBean subclass.
   * 
   * Then it calls "processResults to handle the returned data.
   * "processResults" must be overridden by each CallableBean subclass.
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
   * Sends an sql query to the DBMS. If multipleResultSets is true, it will
   * use CallableStatement.execute() and return multipleResultSets, else it
   * uses CallableStatement.executeQuery() returning a single ResultSet
   * .
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
   
   protected CallableStatement stmt = null;
   
   public CallableBean()
   {
      super();
   }
   
   public CallableBean( DataSource dataSource )
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
          prepareCall();
          
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
           errorMsg( "runQuery - finally: CallableStatment not closed" );
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

