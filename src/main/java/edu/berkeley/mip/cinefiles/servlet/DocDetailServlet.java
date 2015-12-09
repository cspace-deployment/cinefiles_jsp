package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.berkeley.mip.cinefiles.entity.DocDetail;
import edu.berkeley.mip.cinefiles.entity.DocImages;

public class DocDetailServlet extends CinefilesServlet
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

      int docId;
      String resPage = "/DocDetail.jsp";
      
      try
      {
         docId = ( new Integer( req.getParameter( "docId" ))).intValue();
      }
      catch( Exception e )
      {
        docId = 0;
      }
      
      if( docId > 0 )
      {
         String msg = "Searching for docId: " + docId;
         
         if( debug )
            req.setAttribute( "msg", msg);

         DocDetail dd = new DocDetail( dataSource, dataBase, docId );

         if( dd != null )
         {
            if ( dd.getAccessCode() < userAccess )
            {
              resPage = "/DocRestDetail.jsp";
            }
            
            req.setAttribute( "docdetail", dd );

            DocImages di = new DocImages( docImgDir, docId );
            
            if( di != null)
            {
              req.setAttribute( "docimages", di );
              req.setAttribute( "msg", msg + " - Found some images." );
            }
            else
              req.setAttribute( "msg", msg + " - No images found." );
         }
         else 
            req.setAttribute( "msg", msg + " - No matching records found." );
      }
      else
      {
        resPage = "/DocDetailError.jsp";
      }

      RequestDispatcher page = req.getRequestDispatcher( resPage );
      page.forward(  req, res );
   }   
}
