package edu.berkeley.mip.cinefiles.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmDoc implements Serializable
{
   /*
    * FilmDoc wraps a single ResultSet row of a film document citation.
    * This is the information needed to create an HREF for the document
    * in a pick list. The "FilmDocs" bean is just an array of "FilmDoc"
    * items.
    */
  
   static final long serialVersionUID =  1L;

   private String id;
   private String title;
   private String type;
   private int pages;
   private String pg_info;
   private String source;
   private ArrayList<String[]> authors;
   private String pubdate;
   private int juliandate;
   private int access_code;
   private String docUrl;
   
   public FilmDoc( String id, String title, String type, int pages,
		             String pg_info, String source,
		             ArrayList<String[]>authors, String pubdate,
                   int juliandate, int access_code, String docUrl )
   {
     this.id = id;
     this.title = title;
     this.type = type;
     this.pages = pages;
     this.pg_info = pg_info;
     this.source = source;
     this.authors = authors;
     this.pubdate = pubdate;
     this.juliandate = juliandate;
     this.access_code = access_code;
     this.docUrl = docUrl;
   }
   
   public String getId()
   {
      return id;
   }
   
   public String getTitle()
   {
      return title;
   }
   
   public String getType()
   {
      return type;
   }
   
   public int getPages()
   {
      return pages;
   }
   
   public String getPginfo()
   {
      return pg_info;
   }
   
   public String getSource()
   {
      return source;
   }
   
   public ArrayList<String[]> getAuthors()
   {
      return authors;
   }
   
   public String getPubdate()
   {
      return pubdate;
   }
   
   public int getJuliandate()
   {
	   return juliandate;
   }
   
   public int getAccess_code()
   {
      return access_code;
   }
   
   public String getDocurl()
   {
      return docUrl;
   }
}