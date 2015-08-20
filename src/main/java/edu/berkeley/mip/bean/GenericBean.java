package edu.berkeley.mip.bean;

import java.io.FileWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.sql.DataSource;

public class GenericBean implements Serializable
{   
   /*
    * This class defines the basic common components for different types of
    * of database entity beans. The known subtypes include "CallableBean",
    * "PreparedBean" and "StatementBean".
    *
    * There is a no-arg constructor, but to be useful the bean would have to
    * be created with a JNDI DataSource passed to it in the Constructor.
    *
    * Methods:
    * public GenericBean()
    * Initializes an array of messages, for logging and debugging purposes.
    *   
    * public GenericBean( DataSource dataSource )
    * Calls the no-arg Constructor and saves the DataSource
    * 
    * public void errorMessage( String msg )
    * This a convenience method for adding message to an ArrayList<String> of 
    * error and/or debug messages. It is public so classes using GenericBeans
    * can add their own trace messages.
    * 
    * public ArrayList<String> getErrorMsgs()
    * returns the ArrayList of error messages;
    * 
    * public void setDatabase( String dataBase )
    * Lets the sub-class modify the dataBase instance variable.
    *
    * protected void useDatabase()
    * Sends the "use database" command to the DBMS, with the value of the
    * "dataBase" instance variable
    *
    * protected void prepareCall()
    * Must be overridden, if used, to provide the correct "prepareCall" 
    * statements.  Throws and SQLException if it is not overridden.
    * 
    * protected void prepareStatement()
    * Must be overridden, if used, to provide the correct "prepareStatement"
    * statements. Throws an SQLException if it is not overridden.
    * 
    * protected void processResults()
    * Must be overridden in the sub-class to perform the required processing
    * of the returned ResultSet. Throws an SQLException if not overridden.
    * 
    * public String getFormattedTimestamp()
    * A convenience method for getting timestamps.
    * 
    * public int getResultSetInt( String key )
    * Calls getResultSetInt( key, -1 ), only useful if -1 works for 
    * the default value. (alternatively, 0 might be a better choice)
    * 
    * public int getResultSetInt( String key, int defvalue )
    * Looks up and returns an int value from the current ResultSet
    * row. If null, it returns the default value.
    * 
    * public String getResultSetString( String key )
    * Calls getResultSetString( key, "" )
    * 
    * public String getResultSetString( String key, String defvalue )
    * Looks up and returns a String value from the current ResultSet, 
    * row. If that is null, it returns the default value.
    */
   
   private static final long serialVersionUID = 1L;
   
   protected DataSource dataSource = null;
   protected String dataBase = null;
   protected Connection connection = null;
   protected ResultSet rs = null;
   protected ArrayList <String> errors = null;
   
   protected String beanlog = null;
   private FileWriter log = null;
   protected boolean traceFlag = false;
   
   public GenericBean()
   {
      errors = new ArrayList <String>();   
      errorMsg( "Error list initialized: " + getFormattedTimestamp() );
   }
   public GenericBean( DataSource dataSource )
   {
      this();
      this.dataSource = dataSource;
   }

   public void errorMsg( String msg )
   {
      errors.add( msg );
   }

   protected void useDatabase()
   {
      if( dataBase != null )
      {  
         errorMsg( "useDatabase: " + dataBase );
         
         Statement s = null;
         try
         {
            s = connection.createStatement();
            s.executeUpdate( "use " + dataBase );
         }
         catch( SQLException e )
         {
            errorMsg( "setDatabase: " + e.getMessage() );
         }
         finally
         {
            errorMsg( "useDatabase: closing Statement" );
            try
            {
               s.close();
            }
            catch( SQLException e )
            { 
               errorMsg( "useDatabase: failed to close Statement" );
            }
         }    
      }
      else
         errorMsg( "useDatabase: dataBase is null" );
   }
   
   public void setDatabase( String db )
   {
      this.dataBase = db;
   }
   
   // How subclasses should provide the proper prepareCall expression
   protected void prepareCall() throws SQLException
   {
      // To be overridden
      throw new SQLException( "prepareCall: must be overridden" );
   }

   // How subclasses should provide the proper prepareStatement expresssion
   protected void prepareStatement() throws SQLException
   {
      // To be overridden
      throw new SQLException( "prepareStatement: must be overridden" );
   }
   
   // How subclasses process the ResultSet returned by a CallableStatement
   protected void processResults() throws SQLException
   {
      // To be overridden
      throw new SQLException( "processResults : must be overridden" );
   }
      
   public String getFormattedTimestamp()
   {
      SimpleDateFormat formatter = new SimpleDateFormat(
            "MM-dd-yy 'at' kk:mm:ss" );
      formatter.setTimeZone( TimeZone.getTimeZone( "PST" ) );
      return formatter.format( new Date() );
   }
   
   // Debugging information that is saved by this class and possibly by its subclasses
   public ArrayList<String>getErrorMsgs()
   {
      return errors;
   }
   
   // Only useful when "-1" can be used to indicate a non-valid value
   public int getResultSetInt( String key )
   {
      return getResultSetInt( key, -1 );
   }
   
   public String getResultSetIntString( String key )
   {
     return getResultSetIntString( key, -1 );
   }
   
   // Returns ResultSet int with a specified default value
   public int getResultSetInt( String key, int defvalue )
   {
      int val;
      try
      {
         val = rs.getInt( key );
      }
      catch( SQLException e )
      {
        errorMsg( "getResultSetInt for " + key + ": " + e.getMessage() );
        val = defvalue;
      }
      return val;
   }
   
   // Returns ResultSet int cast to a String
   public String getResultSetIntString( String key, int defvalue )
   {
      int val;
      try
      {
        val = rs.getInt( key );
      }
      catch( SQLException e )
      {
        errorMsg( "getResultSetIntString for " + key + ": " + e.getMessage() );
        val = defvalue;        
      }
      return "" + val + "";
   }
   
   // Returns ResultSet String with a default of "" (never returns null)
   public String getResultSetString( String key )
   {
      return getResultSetString( key, "" );
   }

   // Returns ResultSet String with a specified default (never returns null)
   public String getResultSetString( String key, String defvalue )
   {
      String result = null;
      try
      {
        result = rs.getString( key );
      }
      catch( SQLException e )
      {
         errorMsg( "getResultSetString for " + key + ": " + e.getMessage() );
      }
      return ( result == null) ? defvalue : result;
   } 

   // OPEN LOG 
   protected void openLog( String logname )
   {
     if(( log != null ) || ( logname == null ) || ( traceFlag == false ))
       return;
       
     DateFormat df = new SimpleDateFormat( "yyMMdd_hhmmss" );
     String ext = df.format( new Date() );
     
     try
     {
       log = new FileWriter( logname + ext + ".log", true );
     }
     catch( Exception e )
     {
       log = null;
       traceFlag = false;
       System.err.println( "Unable to open log: " + logname );
     }
   }
   
   // CLOSE LOG
   protected void closeLog()
   {
     if( log != null )
     {
       try
       {
         log.close();
       }
       catch( Exception e )
       {}
     }
   }
   
   // TRACE MSG  
   protected void traceMsg( String msg )
   {
     if(( log == null ) && traceFlag )
     {
       openLog( beanlog );
     }

     if( ! traceFlag )       
       return;
       
     try
     {
        log.write( msg + "\n" );
     }
     catch( Exception e )
     {
       System.err.println( "Unable to write trace message to log file" );
     }
   }
   
   // END TRACE
   protected void endTrace( String msg )
   {
      traceMsg( msg );
      closeLog();
   }
}

