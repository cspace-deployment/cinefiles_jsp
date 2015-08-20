package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.berkeley.mip.cinefiles.entity.FilmList;
import edu.berkeley.mip.cinefiles.entity.FilmSearchArgs;

public class FilmSearchServlet extends CinefilesServlet
{
  static final long serialVersionUID =  1L;
  
  public void doGet( HttpServletRequest req, HttpServletResponse res )
      throws ServletException, IOException
  {
    doPost(req, res);
  }

  public void doPost( HttpServletRequest req, HttpServletResponse res )
      throws ServletException, IOException
  {
    if( req.getMethod().equals( "HEAD" ))
      return; 
    
    String resultPage = "/FilmResults.jsp";
    String remoteHost = req.getHeader( "X-Forwarded-For" );
    
    if( remoteHost == null )
       remoteHost = req.getRemoteHost();
    
    int userAccess = getUserAccess( remoteHost );

    FilmSearchArgs args = new FilmSearchArgs( req );
    FilmList filmList = new FilmList( dataSource, dataBase, args );
    
    if(( filmList == null ) || (filmList.getListSize() == 0 ))
    {
      req.setAttribute( "msg", "Film Search - No matching records found." );
      resultPage = "/NoResults.jsp";
    }
    else 
    {
      req.setAttribute( "filmlist", filmList );
      req.setAttribute( "useraccess", userAccess );
    }
      
    RequestDispatcher page = req.getRequestDispatcher( resultPage );
    page.forward(  req, res );
  }
}
