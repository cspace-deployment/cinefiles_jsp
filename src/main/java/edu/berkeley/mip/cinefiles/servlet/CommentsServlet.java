package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Properties;

public class CommentsServlet extends HttpServlet
{
   static final long serialVersionUID = 1L;

   boolean debug = false;
   String adminEmail = null;

   public void init() throws ServletException
   {
      super.init();
      if( getServletConfig().getInitParameter( "debug" ).equals( "true" ) )
         debug = true;

      if( getServletConfig().getInitParameter( "debug" ).equals( "true" ) )
         debug = true;

      adminEmail = getServletContext().getInitParameter( "adminEmail" );

      if(( adminEmail == null ) || ( adminEmail.equals( "" )))
         adminEmail = "cinefiles@berkeley.edu";
   }

   public void doGet( HttpServletRequest req, HttpServletResponse res )
                      throws ServletException, IOException
   {
      doPost( req, res );
   }
   
   public void doPost( HttpServletRequest req, HttpServletResponse res )
                       throws ServletException, IOException
   {
      if( req.getMethod().equals( "HEAD" )) return;
      
      String comments = makeMailMessage( req );

      if( comments == null )
      {
         RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher( "/Comments.jsp" );
         dispatcher.forward( req,res );
         return;
      }

      if( debug )
      {
         PrintWriter out = res.getWriter();
         out.println( "<h2>This is the Cinefiles the Comments Servlet</h2>" );
         out.println( "We are in debug mode." );
         out.println( "Sending mail to " +
                      (((adminEmail == null)||(adminEmail.equals(  "" ))) ?
                      "Nobody" : adminEmail ));
         out.println( "<br />" );
         out.println( comments );
      }
      else
      {
        
        String responsepage;
        String subject;

        if( comments.indexOf( "Request for PDF Document:" ) > 0 )
        {
           responsepage = "/PdfResponse.jsp";
           subject = "PDF Request";
        }
        else
        {
           responsepage = "/CommentsResponse.jsp";
           subject = "Cinefiles Comments";
        }
        
        sendMail( comments, subject );

        RequestDispatcher dispatcher =
           getServletContext().getRequestDispatcher( responsepage );
        dispatcher.forward( req,res );
      }
   }

   private void sendMail( String comments, String subject )
   {
      Properties props = new Properties();
      props.put("mail.from", "cinefiles@berkeley.edu" );
      props.put("mail.debug", "true" );

      Session session = Session.getInstance(props, null);

      try
      {
          MimeMessage msg = new MimeMessage(session);
          msg.setFrom();
          msg.setRecipients( Message.RecipientType.TO, adminEmail );
          msg.setSubject( subject );
          msg.setSentDate( new Date() );
          msg.setText( comments );
          Transport.send( msg );
      }
      catch( MessagingException ex )
      {
          System.err.println( "Send mail exception: " + ex.getMessage() );
      }
   }

   private String getParam( HttpServletRequest req, String param )
   {
      if(( req.getParameter( param )) == null )
         return "";
      else
         return req.getParameter(  param  );
   }

   private String makeMailMessage( HttpServletRequest request )
   {
      String src = getParam( request, "src" );
      
      if( src.equals( "" ))
         return null;

      StringBuffer msgBuf = new StringBuffer();
      
      if( src.equals( "restdoc" ))
      {
        msgBuf.append( "\nRequest for PDF Document:\n" );
        msgBuf.append( "Doc ID: " + getParam( request, "pdfdocid" )+ "\n" );
        msgBuf.append( "E-mail: " + getParam( request, "userEmail" ) + "\n" );
      }
      else
      {
        msgBuf.append( "\nUser Comments:\n" );
        msgBuf.append( "From: " + getParam( request, "userName" ) + "\n" );
        msgBuf.append( "Phone: " + getParam( request, "phoneNum" ) + "\n" );
        msgBuf.append( "Fax: " + getParam( request, "faxNum" ) + "\n" );
        msgBuf.append( "E-mail: " + getParam( request, "userEmail" ) + "\n" );
        msgBuf.append( "Comments: " + getParam( request, "comments" ) + "\n" );
      }
      
      return msgBuf.toString();
   }
}
