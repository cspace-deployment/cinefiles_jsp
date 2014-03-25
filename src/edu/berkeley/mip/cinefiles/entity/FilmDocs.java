package edu.berkeley.mip.cinefiles.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmDocs implements Serializable
{
   /*
    * FilmDocs wraps the list of related document presented by a
    * FilmDetail bean. It is a list of FilmDoc objects, each of
    * which contains the information for a document HREF in a
    * FilmDetail screen.
    */
   private static final long serialVersionUID = 1L;
   
   ArrayList<FilmDoc> filmDocs;
   
   public FilmDocs()
   {
      filmDocs = new ArrayList<FilmDoc>();
   }
   
   public FilmDocs( FilmDoc doc )
   {
	   filmDocs = new ArrayList<FilmDoc>();
	   filmDocs.add( doc );
   }
   
   public void addFilmDoc( FilmDoc fd )
   {
      filmDocs.add( fd );
   }
   
   public ArrayList<FilmDoc> getFilmDocs()
   {
      return filmDocs;
   }
   
   public ArrayList<String[]> getDocRefs()
   {
     int recsize = 7;
     
     ArrayList<String[]> docRecs = new ArrayList<String[]>();
     
     if( filmDocs == null )
     {
       return docRecs;
     }
     
     for( int i = 0; i < filmDocs.size(); i++ )
     {
       String[] docrec = new String[recsize];
       String author = "";
       String name_id = "";
       
       FilmDoc doc = filmDocs.get(i);
        
       ArrayList<String[]> authors = doc.getAuthors();
       
       for( int a = 0; a < authors.size(); a++ )
       {
         String[] nextauthor = authors.get( a );
         
         if( a == 0 )
         {
           name_id = nextauthor[0]; 
           author = nextauthor[1];
         }
         else if(( a == 1 ) && ( ! author.equals( "" )))
           author += ", et. al";
       }
                
       docrec[0] = "" + doc.getId() + "";
       docrec[1] = doc.getTitle();          
       docrec[2] = " - " + author + " - <i>" +
                   doc.getSource() + "</i> - " + doc.getPubdate() +
                   " - " + doc.getPages() +
                   ((doc.getPages() == 1) ? " page" : " pages" ) +
                   " - " + doc.getPginfo() + " - " + doc.getType();
       docrec[3] = doc.getSource();
       docrec[4] = "";
       docrec[5] = "" + doc.getAccess_code() + "";
       docrec[6] = doc.getDocurl();
       docRecs.add( docrec );
     }
     
     return docRecs;
   }
}

