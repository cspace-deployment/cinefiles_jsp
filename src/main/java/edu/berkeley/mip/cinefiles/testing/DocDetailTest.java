package edu.berkeley.mip.cinefiles.testing;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.sql.DataSource;

import edu.berkeley.mip.cinefiles.entity.DocDetail;

/*
 * DocDetailTest instantiates a new DocDetail class and calls all of its
 * getter methods, printing out the returned values.
 * 
 * Run it as a java application with the instance variable "docId" set 
 * to some valid document id number.
 */
public class DocDetailTest {

   int docId = 18;
   DocDetail docDetail = null;
   DataSource dataSource = null;
   
   public DocDetailTest() {
   }

   public static void main(String[] args) {
      DocDetailTest docDetailTest = new DocDetailTest();
      System.out.println("Testing DocDetail");
      docDetailTest.testDocDetail();
      System.out.println("Done testing DocDetail\n");
   }

   private DataSource getDataSource() {
      if (dataSource == null)
         dataSource = new PGDataSource().getDataSource();
      return dataSource;
   }

   public void testDocDetail() {
      if (dataSource == null) {
         getDataSource();
      }

      if (dataSource == null)
         return;

      docDetail = new DocDetail(dataSource, "cinefiles_domain", docId);

      if (docDetail == null) {
         System.out.println("Failed to get new DocDetail");
         return;
      } else {
         System.out.println("Success!");
      }

      System.out.println("docDetail:"
            + ((docDetail == null) ? " is NULL." : " instance of "
                  + docDetail.getClass().getName()));

      System.out.println("Doc ID: " + docDetail.getDocId());
      System.out.println("Title: " + docDetail.getTitle());
      System.out.println("DocType: " + docDetail.getType());
      System.out.println("Source: " + docDetail.getSource());
      System.out.println("Source ID: " + docDetail.getSrcId());
      System.out.println("Source URL: " + docDetail.getSrcUrl());

      // getAuthorCount()
      System.out.println("Author Count: " + docDetail.getAuthorCount());
 
      // getAuthorString()
      System.out.println("Author String: " + docDetail.getAuthorString());
 
      //getAuthors()
      if( docDetail.getAuthorCount() > 0 ){
         Hashtable <String, String>authors = docDetail.getAuthors();
         Enumeration <String>values = authors.keys();
         while(values.hasMoreElements()) {
            String key = values.nextElement();
            System.out.println( "" + key + ": " + authors.get(key));
         }
      }
      
      //getSubjectCount()
      System.out.println("Subject Count: " + docDetail.getSubjectCount());

      //getSubjects()
      if( docDetail.getSubjectCount() > 0 ){
         Hashtable <Integer, String>docSubjects = docDetail.getSubjects();
         Enumeration <Integer>values = docSubjects.keys();
         while(values.hasMoreElements()) {
            int key = (Integer)values.nextElement();
            System.out.println( "" + key + ": " + docSubjects.get(key));
         }
      }
 
      //getFilmSubjectCount()
      System.out.println("FilmSubj Count: " + docDetail.getFilmSubjectCount());

      //getFilmSubjects()
      if( docDetail.getFilmSubjectCount() > 0 ){
         Hashtable <String, String>docFilmSubjects = docDetail.getFilmSubjects();
         Enumeration <String>values = docFilmSubjects.keys();
         while(values.hasMoreElements()) {
            String key = values.nextElement();
            System.out.println( "" + key + ": " + docFilmSubjects.get(key));
         }
      }

      //getNameSubjectCount()
      System.out.println("NameSubj Count: " + docDetail.getNameSubjectCount());

      //getNameSubjects()
      if( docDetail.getNameSubjectCount() > 0 ){
         Hashtable <Integer, String>nameSubjects = docDetail.getNameSubjects();
         Enumeration <Integer>values = nameSubjects.keys();
         while(values.hasMoreElements()) {
            int key = (Integer)values.nextElement();
            System.out.println( "" + key + ": " + nameSubjects.get(key));
         }
      }
      
      // getLanguageCount()
      System.out.println("Language Count: " + docDetail.getLanguageCount());
      
      // getLanguages()
      if( docDetail.getLanguageCount() > 0 ){
         ArrayList<String> languages = docDetail.getLanguages();
         for( int i = 0; i < languages.size(); i++ ) {
            System.out.println( "Language, " + languages.get(i) );
         }
      }
      
      // getPages()
      System.out.println("Pages: " + docDetail.getPages());
      
      // getPagination()
      System.out.println("Pagination: " + docDetail.getPagination());
      
      // getDocUrl()
      System.out.println("URL: " + docDetail.getDocUrl());
      
      // getDate()
      System.out.println("Date: " + docDetail.getDate());
      
      // getNote()
      System.out.println("Note: " + docDetail.getNote());
      
      // getInclude(String)
      System.out.println("Includes cast_cr: " + docDetail.getInclude("cast_cr"));
      System.out.println("Includes tech_cr: " + docDetail.getInclude("tech_cr"));
      System.out.println("Includes bx_info: " + docDetail.getInclude("bx_info"));
      System.out.println("Includes filmog: " + docDetail.getInclude("filmog"));
      System.out.println("Includes dist_coq: " + docDetail.getInclude("dist_co"));
      System.out.println("Includes prod_co: " + docDetail.getInclude("prod_co"));
      System.out.println("Includes cost: " + docDetail.getInclude("cost"));
      System.out.println("Includes illust: " + docDetail.getInclude("illust"));
      
      // getIncludes()
      boolean[] includes = docDetail.getIncludes();
      System.out.println( "cast_cr: " + includes[0] );
      System.out.println( "tech_cr: " + includes[1] );
      System.out.println( "bx_info: " + includes[2] );
      System.out.println( "filmog: " + includes[3] );
      System.out.println( "dist_co: " + includes[4] );
      System.out.println( "prod_co: " + includes[5] );
      System.out.println( "cost: " + includes[6] );
      System.out.println( "illust: " + includes[7] );
   }
}
