package edu.berkeley.mip.cinefiles.entity;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import edu.berkeley.mip.bean.StatementBean;

public class DocList extends StatementBean
{
  /*
   * March 2014: Changes made for postgres data source from CSpace.
   * Not using temp tables.  All searching done in runMainQuery, like FilmList.java.
   * Corresponding changes made to DocSearchArgs.java.
   * 
   * DocList A sub-class of StatementBean, it wraps an ArrayList of document
   * records, each of which can be accessed as a full document citation.
   * 
   * An instance of DocList is created using the "new" operator with an argument
   * that is an SQL query string. The query is constructed by the DocSearch
   * servlet from arguments passed from the Document Search form.
   * 
   * Methods: public DocLIst( String query ) Constructor: builds a class using
   * the DataSource to retrieve the database records for a list of document
   * references.
   * 
   * protected void prepareStatment() Overrides the prepareStatement method in
   * StatementBean to create a PreparedStatement which will perform the database
   * lookup.
   * 
   * An SQLException will be thrown if this method is not overridden.
   * 
   * protected void processResults() Overrides the processResults method in
   * StatementBean. It does all the work of populating the DocList array.
   * 
   * An SQLException will be thrown if this method is not overridden.
   * 
   * DocDetail's public accessor methods.
   */

  private static final long serialVersionUID = 1L;
  
  private static final String tmptable1 = "#docidlist1";
  private static final String tmptable2 = "#docidlist2";
  private static final String logname = "/www/tomcat/logs/apps/cine/DocList.";
  
  private int count = 0;
  private boolean queryFailed = false;
  int queryFlag = 0;

  private ArrayList<String[]> docList = null;

  public DocList()
  {
    super();
  }

  public DocList( DataSource dataSource, String db, DocSearchArgs args )
  {
     super( dataSource );
     this.dataBase = db; 
     this.beanlog = logname;
     this.traceFlag = false;
     
     traceMsg( "Opening DocList:" );

//     createTempTables();
     
//     runDocTitleQuery( args );

//     runFilmTitleQuery( args );

//     runDocSubjectsQuery( args );

     runMainQuery( args );
     
     traceMsg( "Final Results == " + getResultCount() );
     
     endTrace( "End of DocList:" );
  }

  protected void processResults() throws SQLException
  {
    traceMsg( "processResults - queryFlag == " + queryFlag );
    
    if( queryFlag == 0 )
    {
      rs.close();
      rs = null;
      return;
    }

    else if( queryFlag == 1 )
    {
      count = getResultCount();
      
      if( count == 0 )
        queryFailed = true;

      rs.close();
      rs = null;
      return;
    }

    if( queryFlag > 1 )
    {
      processResultSet();
      rs.close();
      rs = null;
      return;
    }
  }

  public int getResultCount()
  {
    int cnt = 0;

    try
    {
      while( rs.next() )
      {
        cnt = getResultSetInt("cnt");
      }
    }
    catch( Exception e )
    {
      cnt = 0;
    }
    
    return cnt;
  }

  protected void processResultSet()
  {
    try
    {
      docList = new ArrayList<String[]>();

      while( rs.next() )
      {
        String[] docrec = new String[12];

        docrec[0] = getResultSetIntString("doc_id");
        docrec[1] = getResultSetString("doctitle");
        docrec[2] = getResultSetString("author").replaceAll("\\|", "; ");
        docrec[3] = getResultSetString("source");
        docrec[4] = getResultSetString("pubdate");
        docrec[5] = getResultSetIntString("pages");
        docrec[6] = getResultSetString("page_info");
        docrec[7] = getResultSetString("doctype");
        docrec[8] = getResultSetIntString("src_id");
        docrec[9] = getResultSetIntString("name_id");
        docrec[10] = getResultSetIntString("code");
        docrec[11] = getResultSetString("docurl");

        docList.add(docrec);
        traceMsg( "Added new docrec to doclist" );
      }
    }
    catch( SQLException e )
    {
      traceMsg("processResultSet exception: " + e.getMessage());
      errorMsg("processResultSet: " + e.getMessage());
      queryFailed = true;
    }
  }

  public ArrayList<String[]> getDocList()
  {
    return docList;
  }

  public ArrayList<String[]> getDocRefs()
  {
    int recsize = 7;

    ArrayList<String[]> docRecs = new ArrayList<String[]>();
    
    if( docList == null )
    {
      docRecs.add(new String[recsize]);
      return docRecs;
    }

    for( int i = 0; i < docList.size(); i++ )
    {
      String[] docrec = new String[recsize];

      String[] doclist = docList.get(i);

      docrec[0] = doclist[0];                         // doc_id
      docrec[1] = doclist[1];                         // title
      docrec[2] = " - " + doclist[2] +                // author
                  " - <i>" + doclist[3] + "</i> - " + // source
                  doclist[4] + " - " + doclist[5] +   // date, pages
                  ((doclist[5].equals("1")) ? " page" : " pages") + 
                  " - " + doclist[6] +                // pagination
                  " - " + doclist[7];                 // doctype
      docrec[3] = doclist[8];                         // src name id
      docrec[4] = doclist[9];                         // author name id
      docrec[5] = doclist[10];                        // access code
      docrec[6] = doclist[11];                        // doc url
      docRecs.add(docrec);
    }
    return docRecs;
  }

  public int getListSize()
  {
    if( docList == null )
      return 0;
    else
      return docList.size();
  }

  // CREATE TEMP
  private void createTempTables()
  {
    traceMsg( "Setting up temp tables " + tmptable1 + " and " + tmptable2  );
    
    count = 0;
    queryFlag = 0;
    
    runQuery( "if OBJECT_ID('" + tmptable1 + "') is not null" +
              " drop table " + tmptable1 );
 
    runQuery( "select doc_id=0 into " + tmptable1 );
    
    runQuery( "if OBJECT_ID('" + tmptable2 + "') is not null" + 
              " drop table " + tmptable2 );
    
    runQuery( "select doc_id=0 into " + tmptable2 + 
              " truncate table " + tmptable2 );  
  }
  
  // DOC SUBJECTS QUERY
  private void runDocSubjectsQuery( DocSearchArgs args )
  { 
    if( queryFailed )
    {
      traceMsg( "Not running runDocSubjectQuery: query already failed" );
      return;
    }
    
    traceMsg( "Now in runDocSubjectsQuery" );

    queryFlag = 1;
    
    String where = args.docSubjectArg( "v" );
      
    if( where != null )
    {
      String q = "insert into " + tmptable2 +
                 " select v.doc_id " +
                 " from docsubjects_view v" +
                 (( count > 0 ) ? ", " + tmptable1 + " t " : " " ) +
                 " where v.doc_id > 1 and " + where + 
                 (( count > 0 ) ? " and v.doc_id = t.doc_id " : " " ) +                  
                 " truncate table " + tmptable1 +
                 " insert into " + tmptable1 +
                 " select distinct doc_id from " + tmptable2 +
                 " truncate table " + tmptable2 +
                 " select cnt=count(*) from  " + tmptable1;
      traceMsg( q );
      runQuery( q );
      traceMsg( "Results: " + count );
      traceMsg( "Status: " + ((queryFailed) ? "Failed" : "Success" ));
    }
    else
      traceMsg( "Nothing to lookup in runDocSubjectsQuery" );
  }
  
  // DOC TITLE QUERY
  private void runDocTitleQuery( DocSearchArgs args )
  {
    traceMsg( "Now in runDocTitleQuery" );
    
    queryFlag = 1;
    
    String titleArg =  args.docTitleArg( null );
    
    if( titleArg != null )
    {          
      String q = "set rowcount 25000" +
                 " insert into " + tmptable1 +
                 " select distinct doc_id " +
                 " from alldoctitles_view " +
                 " where " + titleArg +
                 " select cnt=count(*) from " + tmptable1 + 
                 " where doc_id > 0";
      traceMsg( q );
      runQuery( q );
      traceMsg( "runDocTitleQuery results: " + count );
      traceMsg( "Status: " + ((queryFailed) ? "Failed" : "Success" ));
    }
    else
      traceMsg( "Nothing to lookup in runDocTitleQuery" );
  }
  
  // FILM TITLE QUERY
  private void runFilmTitleQuery( DocSearchArgs args )
  {     
    if( queryFailed )
    {
      traceMsg( "Not running runFilmTitleQuery: query already failed" );
      return;
    }

    traceMsg( "Now in runFilmTitleQuery" );

    queryFlag = 1;
    
    String where = args.filmTitleArg( "v" );
    
    if( where != null )
    { 
      String q = "set rowcount 25000 " +
                 "insert into " + tmptable2  +
                 " select f.doc_id " + 
                 " from allfilmtitles_view v, filmdocs f" +
                 (( count > 0 ) ? ", " + tmptable1 + " t " : " " ) +
                 " where v.film_id > 1 and v.film_id = f.film_id " +
                 (( count > 0 ) ? " and f.doc_id = t.doc_id " : " " ) +
                 " and " + where + 
                 " truncate table " + tmptable1 +
                 " insert into " + tmptable1 +
                 " select distinct doc_id from " + tmptable2 +
                 " truncate table " + tmptable2 +
                 " select cnt=count(*) from " + tmptable1;
      
      traceMsg( q );
      runQuery( q );
      traceMsg( "Results: " + count );
      traceMsg( "Status: " + ((queryFailed) ? "Failed" : "Success" ));
    }
    else
      traceMsg( "Nothing to lookup in runFilmTitleQuery" );
  }
  
  // MAIN QUERY
  private void runMainQuery( DocSearchArgs args )
  {
    traceMsg( "Running main query with queryFailed: " + queryFailed );
    
    queryFlag = 2;
    
    if( ! queryFailed ) 
    { 
      String docTitleArg =  args.docTitleArg( null );
      String filmTitleArg = args.filmTitleArg( null );
      String docSubject = args.docSubjectArg( null );
      String author = args.docAuthorArg( null );
      String source = args.docSourceArg( null );
      String date = args.docPubDateArg( null );
      String language = args.docLanguageArg( null );
      String type = args.docTypeArg( null );
      String contains = args.docContainsArg(null);
      
      String q = "select distinct dv.* from cinefiles_denorm.doclist_view dv " + 
                 "left outer join cinefiles_denorm.filmdocs fd on (dv.doc_id=fd.doc_id) " +
    		     "left outer join cinefiles_denorm.filmlist_view fv on (fd.film_id=fv.film_id) " +
                 "where 1=1 " +
//                ", " + tmptable1 + " t " +
//                " where v.doc_id > 1 " +
//                ((count > 0 ) ? " and v.doc_id = t.doc_id " : " " ) + 
                (( docTitleArg != null ) ? " and " + docTitleArg : " " ) +
                (( filmTitleArg != null ) ? " and " + filmTitleArg : " " ) +
                (( docSubject != null ) ? " and " + docSubject : " " ) +
                (( author != null ) ? " and " + author : " " ) +
                (( source != null ) ? " and " + source : " " ) +
                (( date != null ) ? " and " + date : " " ) +
                (( language != null ) ? " and " + language : " " ) +
                (( type != null ) ? " and " + type : " " ) +
                (( contains != null ) ? " and " + contains : " " ) +
                " order by pubdatescalar desc nulls last, doctype, doctitle";
      
      traceMsg( q );
      runQuery( q );
      traceMsg( "Results: " + count );
      traceMsg( "Status: " + ((queryFailed) ? "Failed" : "Success" ));
    }
  }
}
