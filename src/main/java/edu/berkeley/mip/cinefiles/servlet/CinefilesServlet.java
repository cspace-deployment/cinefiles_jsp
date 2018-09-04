package edu.berkeley.mip.cinefiles.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class CinefilesServlet extends HttpServlet
{
   /* CinefilesServlet is a super class for Cinefiles servlets that use one of
    * several globally defined JNDI DataSources. The DataSources are defined in
    * src/main/webapp/META-INF/context.xml.
    * 
    * This class provides the init method and instance variables containing the
    * name of the DataSource, the path to the image directory, and a debug flag
    * value (a boolean). Those three items are obtained as context params from 
    * the context file - "web.xml".
    */
   
   static final long serialVersionUID =  1L;
   
   public boolean debug = false;
   
   // The default dataBase for SQL queries
   protected String dataBase = null;
   
   // A JNDI DataSource, looked up by name
   protected DataSource dataSource = null;
   
   // Name of the JNDI DataSource
   protected String jndiDataSource = null;
   
   // Location of the scanned document image files
   protected String docImgDir = null;
   
   // Location of the apps image files
   protected String cineImgDir = null;
   
   public void init() throws ServletException
   {
      super.init();
      ServletContext cntx = getServletContext();
      
      dataBase = cntx.getInitParameter( "db" );

      if( dataBase == null )
          dataBase = "cinefiles";

      jndiDataSource = cntx.getInitParameter( "jndiDataSource" );
      docImgDir = cntx.getInitParameter( "docImgDir" );
      cineImgDir = cntx.getRealPath("/images");
      
      String d = cntx.getInitParameter( "debug" );
      debug = ( d != null ) && d.equals( "true" );
      

      if( ! debug )
      {
         d = getServletConfig().getInitParameter( "debug" );
         debug = ( d != null ) && d.equals( "true" );
      }
      
      if( jndiDataSource != null )
      {
         try
         {  Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env" );
      
            if( envContext != null )
            {
               dataSource = (DataSource) envContext.lookup( jndiDataSource );
            }
         }
         catch( Exception e )
         {
            throw new ServletException( e.getMessage() );
         }
      
         if( dataSource == null )
            throw new ServletException( "Unable to create DataSource" );
      }
   }
   
   protected int getUserAccess(HttpServletRequest req)
   {
      String remoteHost = req.getHeader( "X-Forwarded-For" );
      
      if( remoteHost == null )
         remoteHost = req.getRemoteHost();
      
      return getUserAccess( remoteHost );
   }
   
   // getUserAccess checks the requesting IP address to determine access level.
   // Many of the rules are redundant, preserving the distinction between the
   // different categories - even if they currently have equal access rights.
   protected int getUserAccess( String remoteHost )
   {
     final String anybyte = "\\.([0-9]|[1-9][0-9]|[12][0-9][0-9])";
  
     if( remoteHost != null )
     {
       // MIP access level
       if( remoteHost.matches( "^169\\.229\\.199\\.([1-6][0-9]|[1-9])$" ) ||
           remoteHost.matches( "^169\\.229\\.32\\.(10|15)$" ))
       {
         // Using 169.229.199.49 as a test value
         if( remoteHost.matches( "^169\\.229\\.199\\.49$" ))
         {
            return 4;
         }
         return 0;
       }
       // PFA access level
       else if( remoteHost.matches( "^169\\.229\\.5\\.([1-9]|[1-5][0-9]|60)$" ) ||
                remoteHost.matches( "^128\\.32\\.51\\.([1-9]|[1-9][0-9])$" ) ||
                remoteHost.matches( "^128\\.32\\.51\\.1(0[0-9]|1[04])$" ))
       {
         return 0;
       }
       // UC access level
       else if( remoteHost.matches( "^128\\.32" + anybyte + anybyte + "$" ) ||
                remoteHost.matches( "^136\\.152" + anybyte + anybyte + "$" ) ||
                remoteHost.matches( "^169\\.229" + anybyte + anybyte + "$" ) ||
                remoteHost.matches( "^10\\.142" + anybyte + anybyte + "$" ) ||
                remoteHost.matches( "^131\\.243\\.52" + anybyte + "$" ) ||
                remoteHost.matches( "^192\\.101\\.42" + anybyte + "$" ) ||
                remoteHost.matches( "^10\\.136" + anybyte + anybyte + "$" ))
       {
         return 0;
       }
     }
     
     // World access level
     return 4;
   }
}
