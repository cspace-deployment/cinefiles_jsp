package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.berkeley.mip.cinefiles.entity.DocList;
import edu.berkeley.mip.cinefiles.entity.DocSearchArgs;

public class DocSearchServlet extends CinefilesServlet
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

    String resultPage = "/DocResults.jsp";
    String remoteHost = req.getHeader( "X-Forwarded-For" );
    
    if( remoteHost == null )
       remoteHost = req.getRemoteHost();
    
    int userAccess = getUserAccess( remoteHost );

    DocSearchArgs args = new DocSearchArgs( req );   
    DocList docList = new DocList( dataSource, dataBase, args );

    if(( docList == null ) || ( docList.getListSize() == 0 ))
    {
      req.setAttribute( "msg", "Document Search - No matching records found." );
      resultPage = "/NoResults.jsp";
    }
    else
    {
      req.setAttribute( "doclist", docList );
      req.setAttribute( "docreflist", docList.getDocRefs() );
      req.setAttribute( "useraccess", userAccess );
    }
    
    RequestDispatcher page = req.getRequestDispatcher( resultPage );
    page.forward(  req, res );
  }
}
