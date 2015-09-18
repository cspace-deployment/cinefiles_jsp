package edu.berkeley.mip.cinefiles.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import edu.berkeley.mip.cinefiles.entity.DocDetail;
import edu.berkeley.mip.cinefiles.entity.DocImages;

public class DocPdfServlet extends CinefilesServlet
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
      
      int docId;
      boolean includeImages = false;
      
      String pdfTitle = null;
      String page = "/NoPdf.jsp";
      String copymsg = "WARNING: This material may be protected " +
      		             "by copyright law  (Title 17 U.S. Code)";
      
      String srcdir = null;
      
      DocDetail docDetail = null;
      DocImages docImages = null;
      Document document = null;
      
      try
      {
         docId = ( new Integer( req.getParameter( "docId" ))).intValue();
      }
      catch( Exception e )
      {
         docId = 0;
      }
      
      try
      {
         includeImages = "all".equals( req.getParameter( "pgs" ));
      }
      catch( Exception e )
      {
         includeImages = false;
      }

      if( docId > 0 )
      {
         docDetail = new DocDetail( dataSource, dataBase, docId );

         if( includeImages )
         {
           docImages = new DocImages( docImgDir, docId );
           srcdir = docImages.getSrcDir();
           pdfTitle = "Cinefiles_" + docId + ".pdf";
         }
         else
           pdfTitle = "Citation_" + docId + ".pdf";
      }
      
      if(( docId > 0 ) && ( docDetail != null ))
      {  
         document = new Document( PageSize.LETTER );       
         
         try
         {
           res.setContentType( "application/pdf" );
           res.setHeader( "Content-Disposition", "attachment; filename=\"" + 
                           pdfTitle + "\"" );
           
           String doctitle = docDetail.getTitle();
           if(( doctitle == null ) || doctitle.equals("" ))
             doctitle = "Document title not available";

           String docpub = docDetail.getSource();
           if(( docpub == null ) || docpub.equals( "" ))
             docpub = "Publisher name not available";
           
           PdfWriter writer = PdfWriter.getInstance( document, res.getOutputStream() );
           document.addTitle("CineFiles Document #" + docId );
           document.addCreationDate();
           document.open();     

           Font ifont = new Font( Font.HELVETICA, 14, Font.ITALIC );
           Font bfont = new Font( Font.HELVETICA, 14, Font.BOLD );
           Font font = new Font( Font.HELVETICA, 14, Font.NORMAL );

           BaseFont ibf = ifont.getCalculatedBaseFont( false );
           BaseFont bbf = bfont.getCalculatedBaseFont( false );
           BaseFont bf = font.getCalculatedBaseFont( false );
           
           PdfContentByte cb = writer.getDirectContent();
           
           int top = 715;
           int bottom = 35;
           int left = 72;
           int mid = 300;
           
           int x = 36;
           int x2 = 600;
           int y = top - 45;
           int y2 = 10;
           
           int textsize = 14;
           int citesize = 12;
           int footersize = 9;
          
           int lineoffset = 30;
           int recoffset = 20;
           
           Image logo = Image.getInstance( cineImgDir + "/cinepdflogo.png" );
           logo.setAbsolutePosition( x, y );

           document.add( logo );
           
           x = left;
           x2 = mid;
           y = y - 80;
           
           cb.beginText();
           
           if( includeImages )
           {
             cb.setFontAndSize( bf, citesize );
             cb.showTextAligned( PdfContentByte.ALIGN_CENTER, copymsg, mid, bottom - 15, 0 );
           }

           cb.setFontAndSize( bf, footersize );
           String pdfdate = new java.util.Date().toString();
           //cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, pdfdate, 610, y2, 0 );
           
           cb.setFontAndSize( bbf, textsize+2 );
           cb.showTextAligned( PdfContentByte.ALIGN_CENTER, 
                              "Document Citation", x2, y, 0 );
           
           x2 = x + 125; 
           y = y - 50;

           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                              "Title", x, y, 0 );

           cb.setFontAndSize( bbf, citesize );
           y = printLongLine( doctitle, cb, x2, y );
           
           y = checkPage( document, cb, bf, citesize, y, lineoffset );
           
           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Author(s)", x, y, 0 );           
           
           List<String> authors = docDetail.getAuthors();
           Enumeration <String> ae = Collections.enumeration(authors);
           
           int offset = 0;
           
           while( ae.hasMoreElements() )
           {
             y = checkPage( document, cb, bf, citesize, y, offset );

             offset = recoffset;
             
             String nextauth = ae.nextElement();
             y = printLongLine( nextauth, cb, x2, y );
           }
                      
           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 
           
           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Source", x, y, 0 );
           
           cb.setFontAndSize( ibf, citesize );
           y = printLongLine( docpub, cb, x2, y );
 
           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 
           
           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Date", x, y, 0 );
           
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               docDetail.getDate(), x2, y, 0 );         
           
           y = checkPage( document, cb, bf, citesize, y, lineoffset );
           
           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Type", x, y, 0 );
           
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
               docDetail.getType(), x2, y, 0 );
           
           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 
             
           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Language", x, y, 0 );
           
           ArrayList<String> langs = docDetail.getLanguages();
           
           if( langs.size() > 0 )
           {
              cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                                  langs.get(0), x2, y, 0 );           

             for( int i = 1; i < langs.size(); i++ )
             {
               y = checkPage( document, cb, bf, citesize, y, recoffset ); 
 
               cb.showTextAligned( PdfContentByte.ALIGN_LEFT,
                                   langs.get(i), x2, y, 0 );
             }
           }

           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 

           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Pagination", x, y, 0 );
           
           String pginfo = docDetail.getPagination();
           y = printLongLine( pginfo, cb, x2, y );
           
           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 

           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "No. of Pages", x, y, 0 );

           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "" + docDetail.getPages(), x2, y, 0 );

           y = checkPage( document, cb, bf, citesize, y, lineoffset ); 

           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Subjects", x, y, 0 );

           Hashtable<Integer, String> namesubjs = docDetail.getNameSubjects();
           Enumeration <String> nse = namesubjs.elements();
           
           offset = 0;
           
           while( nse.hasMoreElements() )
           {
             y = checkPage( document, cb, bf, citesize, y, offset ); 

             offset = recoffset;
             
             String nsubj = nse.nextElement();
             y = printLongLine( nsubj, cb, x2, y );
           }
                                 
           Hashtable<Integer, String> subjs = docDetail.getSubjects();
           Enumeration <String> se = subjs.elements();
           
           while( se.hasMoreElements() )
           {
             y = checkPage( document, cb, bf, citesize, y, offset );

             offset = recoffset;

             String subj = se.nextElement();
             y = printLongLine( subj, cb, x2, y );
           }
           
           y = checkPage( document, cb, bf, citesize, y, lineoffset );

           cb.setFontAndSize( bf, citesize );
           cb.showTextAligned( PdfContentByte.ALIGN_LEFT, 
                               "Film Subjects", x, y, 0 );
           
           Hashtable<String, String> filmsubjs = docDetail.getFilmSubjects();
           Enumeration <String> fse = filmsubjs.elements();

           offset = 0;
           
           while( fse.hasMoreElements() )
           {
             y = checkPage( document, cb, bf, citesize, y, offset );
             
             offset = recoffset;
             
             String fsubj = fse.nextElement();
             
             y = printLongLine( fsubj, cb, x2, y );
           }
                      
           cb.endText();
           cb.sanityCheck();
           
           document.newPage();
           
           int l = 36;
           int r = 36;
           int t = 36;
           int b = 75;
           
           if( docImages != null )
           {
             String[] imgs = docImages.getImageList();
             
             for( int i=0; i < imgs.length; i++ )
             {
               Image nextimg = null;
               nextimg = Image.getInstance( srcdir + "/" + imgs[i] );
             
               int w = (int)nextimg.getPlainWidth();
               int h = (int)nextimg.getPlainHeight();
             
							 if( w < 540 )
								 w = 540;

               Rectangle picrect = new Rectangle( w + l + r, h + t + b );
               document.setPageSize( picrect );
               document.newPage();
             
               nextimg.setAbsolutePosition( l, b );
               document.add( nextimg );
             
               cb.beginText();
               cb.setFontAndSize( bf, citesize );
               cb.showTextAligned( PdfContentByte.ALIGN_CENTER, copymsg, (w+l+r)/2, bottom, 0 );
               cb.endText();
               cb.sanityCheck();
             }
           }
           document.close();
         }
         catch( DocumentException de )
         {
           System.err.println( "Document: " + de.getMessage() );
           RequestDispatcher respage = req.getRequestDispatcher( page );
           respage.forward( req, res );
         }
      }
      else
      {
         RequestDispatcher respage = req.getRequestDispatcher( page );
         respage.forward( req, res );
      }
   }
   
   private int printLongLine( String str, PdfContentByte cb,
                                int x, int yarg )
   {
     String s = str;
     int y = yarg;
     int offset = 12;
     
     while( s.length() > 65 )
     {
        int pt = s.lastIndexOf( ' ', 65 );
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
            s.substring( 0, pt ), x, y, 0 );
        s = s.substring( pt+1 );
        y -= offset;
     }
     cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
         s, x, y, 0 );
     
     return y;
   }
   
   private int checkPage( Document d, PdfContentByte cb,
                          BaseFont bf, int size, int y, int off )
   {
     if( y < 80 )
     {
       cb.endText();
       d.newPage();
       cb.beginText();
       cb.setFontAndSize( bf, size );
       return 715;
     }
     
     return y - off;
   }
}
