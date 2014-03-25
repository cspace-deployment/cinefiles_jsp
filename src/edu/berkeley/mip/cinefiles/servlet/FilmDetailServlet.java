package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.berkeley.mip.cinefiles.entity.FilmDetail;

public class FilmDetailServlet extends CinefilesServlet
{
   static final long serialVersionUID =  1L;

   public void doGet( HttpServletRequest req, HttpServletResponse res )
                      throws ServletException, IOException
   {
      doPost( req, res );
   }
   
   public void doPost( HttpServletRequest req, HttpServletResponse res )
                       throws ServletException, IOException
   {
      if( req.getMethod().equals( "HEAD" )) return;
      
      String remoteHost = req.getHeader( "X-Forwarded-For" );
      
      if( remoteHost == null )
         remoteHost = req.getRemoteHost();
      
      int userAccess = getUserAccess( remoteHost );

      int filmId;

      try
      {
         filmId = ( new Integer( req.getParameter( "filmId" ))).intValue();
      }
      catch( Exception e )
      {
         filmId = 0;
      }
      
      if( filmId > 0 )
      {
         String msg = "Searching for filmId: " + filmId;
         
         if( debug )
            req.setAttribute( "msg", msg);

         FilmDetail fd = new FilmDetail( dataSource, filmId );
         
         if( fd != null )
         {
            req.setAttribute( "msg", "Matching records found." );
            req.setAttribute( "filmdetail", fd );
            req.setAttribute( "useraccess", userAccess );
            req.setAttribute( "docreflist", fd.getRelatedDocs().getDocRefs() );
         }
         else
         {
            req.setAttribute( "doclist", new String[7] );  
            req.setAttribute( "msg", msg + " - No matching records found." );
         }
         
         RequestDispatcher page = req.getRequestDispatcher( "/FilmDetail.jsp" );
         page.forward(  req, res );
      }
      else
      {
         RequestDispatcher errpage = req.getRequestDispatcher( "/FilmDetailError.jsp" );
         errpage.forward( req, res );
      }
   }   
}
