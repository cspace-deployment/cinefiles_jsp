package edu.berkeley.mip.cinefiles.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sql.DataSource;

import edu.berkeley.mip.bean.StatementBean;

public class FilmList extends StatementBean
{
  private static final long serialVersionUID = 1L;

  private static final String logname = "/www/tomcat/logs/apps/cine/FilmList.";

  private int count = 0;

  private boolean queryFailed = false;

  int queryFlag = 0;

  private ArrayList<String[]> filmList = null;

  public FilmList()
  {
    super();
  }

  public FilmList( DataSource dataSource, String db, FilmSearchArgs args )
  {
     super( dataSource );
     this.dataBase = db;
     this.beanlog = logname;
     this.traceFlag = false;
     
     traceMsg( "Opening FilmList:" );

     runMainQuery( args );

     endTrace( "End of FilmList:" );
  }

  protected void processResults() throws SQLException
  {
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

  protected int getResultCount()
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
      filmList = new ArrayList<String[]>();

      while( rs.next() )
      {
        if( getResultSetInt( "doc_count") < 1 )
          continue;

        String[] filmrec = new String[6];

        filmrec[0] = getResultSetIntString("film_id");
        filmrec[1] = getResultSetString("doc_count");
        filmrec[2] = getResultSetString("filmtitle");
        filmrec[3] = getResultSetString("director");
        filmrec[4] = getResultSetString("country");
        filmrec[5] = getResultSetIntString("year");
        
        filmList.add(filmrec);
      }
    }
    catch( SQLException e )
    {
      errorMsg("processResultSet: " + e.getMessage());
      queryFailed = true;
    }
  }

  public ArrayList<String[]> getFilmList()
  {
    return filmList;
  }

  public ArrayList<String[]> getFilmRecs()
  {
    int recsize = 6;

    ArrayList<String[]> filmRecs = new ArrayList<String[]>();
    Hashtable<String, Integer> idHash = new Hashtable<String, Integer>();
    
    if( filmList == null )
    {
      filmRecs.add(new String[recsize]);
      return filmRecs;
    }

    for( int i = 0, j = 0; i < filmList.size(); i++ )
    {
      String[] filmrec = new String[recsize];

      String[] filmlist = filmList.get(i);
      
      if( idHash.containsKey( filmlist[0]) )
      {
         int k = idHash.get( filmlist[0] ).intValue();
         if( k > 0 )
         {
           String[] oldrec = filmRecs.get( k );
           if( oldrec[3].matches( ".*; et. al.*" ) ||
               oldrec[3].startsWith( " - " + filmlist[2] ))
           {
              continue;
           }
           else
           {
             int x = oldrec[3].indexOf( " - ", 3 );
             oldrec[3] = oldrec[3].substring( 0, x ) + 
                         "; et. al." + 
                         oldrec[3].substring( x );
           }
         }
      }
      else
      {  
        
        filmrec[0] = filmlist[0];              // film_id
        filmrec[1] = filmlist[1].trim();       // doc_count
        filmrec[2] = filmlist[2];              // film title
        filmrec[3] = " - " + filmlist[3] +     // director
                     " - " + filmlist[4] +     // country
                     " - " + filmlist[5] +     // year
                     " (" + filmrec[1] +       // formatted doc_count
                     (filmrec[1].equals( "1" ) ? " document)" : " documents)");
        idHash.put( filmrec[0], j );
        filmRecs.add( filmrec );
        j++;
      }
    }
    return filmRecs;
  }

  public int getListSize()
  {
    if( filmList == null )
      return 0;
    else
      return filmList.size();
  }

  // MAIN QUERY
  private void runMainQuery( FilmSearchArgs args )
  {
    traceMsg( "Running main query" );
    
		queryFlag = 2;
		
    if( ! queryFailed )
    {      
      String filmtitle = args.filmTitleArg( "v" );
      String name_id = args.directorNameIdArg( "v" );
      String director = args.directorArg( "v" );
      String country = args.countryArg( "v" );
      String genre = args.genreArg( "v" );
      String genreId = args.genreIdArg( "filmgenres" );
      String subject = args.subjectArg( "v" );
      String language = args.languageArg( "v" );
      String prodco = args.prodcoArg( "v" );
      String years = args.yearArg( "v" );

      String q = "select distinct v.film_id, v.doc_count, v.filmtitle, " +
                 " v.director, v.country, v.year from filmlist_view v" +
                 " where v.film_id > 1 and v.doc_count > 0 " +
                 (( name_id != null ) ? " and " + name_id : " " ) +
                 (( filmtitle != null ) ? " and " + filmtitle : " " ) +
                 (( director != null ) ? " and " + director : " " ) +
                 (( country != null ) ? " and " + country : " " ) +
                 (( genre != null ) ? " and " + genre : " " ) +
                 (( subject != null ) ? " and " + subject : " " ) +
                 (( language != null ) ? " and " + language : " " ) +
                 (( prodco != null ) ? " and " + prodco : " " ) +
                 (( years != null ) ? " and " + years : " " ) +
                 (( genreId != null ) ? " and " + genreId : " " ) +
                 "order by v.filmtitle, v.year";

      traceMsg( q );
      runQuery( q );
    }
  }
}
