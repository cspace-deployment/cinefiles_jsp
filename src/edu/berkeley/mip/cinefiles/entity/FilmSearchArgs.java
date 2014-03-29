package edu.berkeley.mip.cinefiles.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class FilmSearchArgs
{
  protected int queryType = 0;
  protected int lang_id = 0;
//  protected int name_id = 0;
  protected String name_id = null;
 // protected int genre_id = 0;
  protected String genre_id = null;
  protected int firstYear = 0;
  protected int lastYear = 0;
  
  protected String filmTitle = null;
  protected String director = null;
  protected String country = null;
  
  protected String genre = null;
  protected String subject = null;
  protected String prodco = null;
  protected String language = null;
  protected String quickType = null;
 
  public FilmSearchArgs()
  {
  }

  public FilmSearchArgs( HttpServletRequest req )
  {
    setQueryType( req.getParameter( "querytype" ));
    setQuickType( req.getParameter( "quicktype" ));
    setQuickTerm( req.getParameter( "quickterm" ));
    
    if( quickType != null )
       return;
    
    setFilmTitle( req.getParameter( "filmtitle" ));
    setDirectorNameId( req.getParameter( "nameId" ));
    setDirector( req.getParameter("director" ));
    setCountry( req.getParameter( "country" ));
    setGenre( req.getParameter( "genre" ));
    setGenreId( req.getParameter( "genreId" ));
    setSubject( req.getParameter( "subject" ));
    setProdco( req.getParameter( "prodco" ));
    setLangId( req.getParameter( "filmlang" ));
    setYear( req.getParameter( "year" ));
  }

  public void setQuickType( String quickType )
  {
    if(( quickType != null ) && ( quickType.length() > 0 ))
    {
      this.quickType = quickType;
      this.queryType = 2;
    }
    else
      this.quickType = null;
  }
  
  public void setQuickTerm( String quickTerm )
  {
    if(( quickType != null) && ( quickTerm != null ) && ( quickTerm.length() > 0 ))
    {
      if( quickType.equals( "title" ))
        setFilmTitle( quickTerm );
      else if( quickType.equals( "director" ))
        setDirector( quickTerm );
      else if( quickType.equals( "genre" ))
        setGenre( quickTerm );
      else if( quickType.equals( "subject" ))
        setSubject( quickTerm );
    }
  }
  
	public void setCountry( String country )
	{
    if(( country != null ) && ( country.length() > 0 ))
      this.country = country;
    else
      this.country = null;  
	}

	public String getCountry()
	{
		return country;
	}

	public String countryArg( String table )
	{
    if( country == null )
      return null;
  
    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    
    if( queryType == 2 )
      return t + "country like '%" + country + "%'";
    else if( queryType == 3 )
      return t + "country like '%" + country + "'";
    else if( queryType == 4 )
      return t + "country = '" + country + "'";

    return t + "country like '" + country + "%'";
	}

  public void setGenre( String genre )
  {
    if(( genre != null ) && ( genre.length() > 0 ))
      this.genre = genre;
    else
      this.genre = null;  
  }

	public String getGenre()
	{
		return genre;
	}

	public String genreArg( String table )
	{
    if( genre == null )
      return null;
    String g = genre.replaceAll( "'", "''" );

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    
    if( queryType == 2 )
      return t + "genre like '%" + g + "%'";
    else if( queryType == 3 )
      return t + "genre like '%" + g + "'";
    else if( queryType == 4 )
      return t + "genre like '%" + g + "'";

    return t + "genre like '" + g + "%'";
	}

	public void setGenreId( String genreId )
	{
	  genre_id = null;
    if( genreId != null )
    {
      try
      {
//        genre_id = Integer.parseInt( genreId );
    	  genre_id = genreId;
      }
      catch( Exception e )
      {
        genre_id = null;
      }
    }
	}
	
	public String getGenreId()
	{
	  return genre_id;
	}
	
	public String genreIdArg( String table )
	{
    if((table != null) && ( table.length() > 0 ) && (genre_id != null))
       return "film_id in (select film_id from cinefiles_denorm.filmgenres where genre_id = '" + genre_id + "')";  
    else
       return null;
	}
	
  public void setSubject( String subject )
  {
    if(( subject != null ) && ( subject.length() > 0 ))
      this.subject = subject;
    else
      this.subject = null;  
  }
  
  public String getSubject()
  {
    return subject;
  }
  
  public String subjectArg( String table )
  {
    if( subject == null )
      return null;
 
    String subj = subject.replaceAll( "'", "''" );

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    
    if( queryType == 2 )
      return t + "subject like '%" + subj + "%'";
    else if( queryType == 3 )
      return t + "subject like '%" + subj + "'";
    else if( queryType == 4 )
      return t + "subject = '" + subj + "'";

    return t + "subject like '" + subj + "%'";
  }
  
  public void setProdco( String prodco )
  {
    if(( prodco != null ) && ( prodco.length() > 0 ))
      this.prodco = prodco;
    else
      this.prodco = null; 
  }
  
  public String getProdco()
  {
    return prodco;
  }
  
  public String prodcoArg( String table )
  {
    if( prodco == null )
      return null;
    
    String pco = prodco.replaceAll( "'", "''" );

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    
    if( queryType == 2 )
      return t + "prodco like '%" + pco + "%'";
    else if( queryType == 3 )
      return t + "prodco like '%" + pco + "'";
    else if( queryType == 4 )
      return t + "prodco = '" + pco + "'";

    return t + "prodco like '" + pco + "%'";
  }
  
  public void setLangId( String filmlang )
  {
    this.lang_id = 0;
    
    if( filmlang == null )
      return;

    try
    {
      this.lang_id = Integer.parseInt( filmlang );
    }
    catch( Exception e )
    {
      this.lang_id = 0;
    }
  }
  
  public int getLangId()
  {
    return lang_id;
  }
  
  public String languageArg( String table )
  {
    if( lang_id <= 1 )
      return null;

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    String lang = null;

    if( lang_id == 382 ) lang = "Aboriginal";
    else if( lang_id == 6 ) lang = "Afrikaans";
    else if( lang_id == 10 ) lang = "Albanian";
    else if( lang_id == 14 ) lang = "Amharic";
    else if( lang_id == 16 ) lang = "Arabic";
    else if( lang_id == 21 ) lang = "Armenian";
    else if( lang_id == 29 ) lang = "Azerbaijani";
    else if( lang_id == 34 ) lang = "Bambara";
    else if( lang_id == 35 ) lang = "Bamileke";
    else if( lang_id == 39 ) lang = "Basque";
    else if( lang_id == 43 ) lang = "Bengali";
    else if( lang_id == 52 ) lang = "Bulgarian";
    else if( lang_id == 173 ) lang = "Cambodian";
    else if( lang_id == 373 ) lang = "Cantonese";
    else if( lang_id == 56 ) lang = "Catalan";
    else if( lang_id == 67 ) lang = "Chinese";
    else if( lang_id == 48 ) lang = "Czech";
    else if( lang_id == 81 ) lang = "Dakota";
    else if( lang_id == 82 ) lang = "Danish";
    else if( lang_id == 88 ) lang = "Dutch";
    else if( lang_id == 95 ) lang = "English";
    else if( lang_id == 98 ) lang = "Eskimo";
    else if( lang_id == 100 ) lang = "Estonian";
    else if( lang_id == 108 ) lang = "Finnish";
    else if( lang_id == 111 ) lang = "French";
    else if( lang_id == 114 ) lang = "Frisian";
    else if( lang_id == 115 ) lang = "Fulah";
    else if( lang_id == 117 ) lang = "Gaelic";
    else if( lang_id == 121 ) lang = "Georgian";
    else if( lang_id == 122 ) lang = "German";
    else if( lang_id == 130 ) lang = "Greek";
    else if( lang_id == 133 ) lang = "Gujarati";
    else if( lang_id == 383 ) lang = "Haitian Creole";
    else if( lang_id == 135 ) lang = "Hausa";
    else if( lang_id == 137 ) lang = "Hebrew";
    else if( lang_id == 141 ) lang = "Hindi";
    else if( lang_id == 376 ) lang = "Hokkien";
    else if( lang_id == 381 ) lang = "Hopi";
    else if( lang_id == 143 ) lang = "Hungarian";
    else if( lang_id == 146 ) lang = "Icelandic";
    else if( lang_id == 152 ) lang = "Indonesian";
    else if( lang_id == 154 ) lang = "Iranian";
    else if( lang_id == 157 ) lang = "Italian";
    else if( lang_id == 158 ) lang = "Japanese";
    else if( lang_id == 171 ) lang = "Kazakh";
    else if( lang_id == 177 ) lang = "Kinyarwanda";
    else if( lang_id == 178 ) lang = "Kirghiz";
    else if( lang_id == 181 ) lang = "Korean";
    else if( lang_id == 185 ) lang = "Kurdish";
    else if( lang_id == 195 ) lang = "Latin";
    else if( lang_id == 197 ) lang = "Lingala";
    else if( lang_id == 198 ) lang = "Lithuanian";
    else if( lang_id == 205 ) lang = "Macedonian";
    else if( lang_id == 210 ) lang = "Malagasy";
    else if( lang_id == 211 ) lang = "Malay";
    else if( lang_id == 212 ) lang = "Malayalam";
    else if( lang_id == 377 ) lang = "Malinke";
    else if( lang_id == 375 ) lang = "Mandarin";
    else if( lang_id == 219 ) lang = "Maori";
    else if( lang_id == 224 ) lang = "Mayan";
    else if( lang_id == 232 ) lang = "Mongolian";
    else if( lang_id == 233 ) lang = "Mossi";
    else if( lang_id == 30 ) lang = "Nahuatl";
    else if( lang_id == 236 ) lang = "Navajo";
    else if( lang_id == 239 ) lang = "Nepali";
    else if( lang_id == 241 ) lang = "Niger-Kordofanian";
    else if( lang_id == 245 ) lang = "Norwegian";
    else if( lang_id == 253 ) lang = "Oriya";
    else if( lang_id == 265 ) lang = "Papiamento";
    else if( lang_id == 386 ) lang = "Pawnee";
    else if( lang_id == 267 ) lang = "Persian";
    else if( lang_id == 269 ) lang = "Polish";
    else if( lang_id == 271 ) lang = "Portuguese";
    else if( lang_id == 384 ) lang = "Punjabi";
    else if( lang_id == 275 ) lang = "Quechua";
    else if( lang_id == 280 ) lang = "Romanian";
    else if( lang_id == 283 ) lang = "Russian";
    else if( lang_id == 289 ) lang = "Sanskrit";
    else if( lang_id == 290 ) lang = "Scots";
    else if( lang_id == 293 ) lang = "Serbo-Croatian";
    else if( lang_id == 374 ) lang = "Sign";
    else if( lang_id == 300 ) lang = "Singhalese";
    else if( lang_id == 303 ) lang = "Slavic";
    else if( lang_id == 304 ) lang = "Slovak";
    else if( lang_id == 305 ) lang = "Slovenian";
    else if( lang_id == 307 ) lang = "Somali";
    else if( lang_id == 308 ) lang = "Songhai";
    else if( lang_id == 310 ) lang = "South American Indian";
    else if( lang_id == 312 ) lang = "Spanish";
    else if( lang_id == 318 ) lang = "Swahili";
    else if( lang_id == 320 ) lang = "Swedish";
    else if( lang_id == 322 ) lang = "Tagalog";
    else if( lang_id == 324 ) lang = "Tajik";
    else if( lang_id == 380 ) lang = "Tamashek";
    else if( lang_id == 325 ) lang = "Tamil";
    else if( lang_id == 329 ) lang = "Thai";
    else if( lang_id == 330 ) lang = "Tibetan";
    else if( lang_id == 343 ) lang = "Turkish";
    else if( lang_id == 349 ) lang = "Ukrainian";
    else if( lang_id == 352 ) lang = "Urdu";
    else if( lang_id == 356 ) lang = "Vietnamese";
    else if( lang_id == 362 ) lang = "Welsh";
    else if( lang_id == 363 ) lang = "Wolof";
    else if( lang_id == 367 ) lang = "Yiddish";
    else if( lang_id == 368 ) lang = "Yoruba";

    if( lang == null )
      return null;

    return t + "filmlanguage like '%" + lang + "%'";
  }

  public void setYear( String year )
  {
    if( year == null )
    { 
      return;
    }
    
    String y = year.trim();
    
    if( y.length() < 4 )
      return;
  
    String regexpat = "^(\\D*)\\s*(\\d{4})\\s*(\\D*)\\s*(\\d{4}|)$";
    Pattern pattern = Pattern.compile( regexpat );
    Matcher matcher = pattern.matcher( y );
    
    boolean success = matcher.find();
    
    if( success )
    {
      int cnt = matcher.groupCount();
      String g1 = ((cnt > 0) ? matcher.group(1) : "").trim();
      String g2 = ((cnt > 1) ? matcher.group(2) : "");
      String g3 = ((cnt > 2) ? matcher.group(3) : "").trim();
      String g4 = ((cnt > 3) ? matcher.group(4) : "");
      
      firstYear = parseYear( g2 );
      lastYear  = parseYear( g4 );
      
      if(( firstYear > lastYear ) && ( lastYear > 0 ))
      {
        int tmp = firstYear;
        firstYear = lastYear;
        lastYear = tmp;
      }
      
      if( g1.equals( "<" ) || g1.equals( "-" ) || g1.equals( "before" ))
      {
        lastYear = ( (lastYear > firstYear ) ? lastYear : firstYear );
        firstYear = 0;
      }
      else if( g1.equals( ">" ) || g1.equals( "after" ))
      {
        lastYear = 0;
      }
      else if( lastYear == 0 )
        lastYear = firstYear;
    }
    else
    {
      firstYear = lastYear = 0;
    }
  }
  
  public int getFirstYear()
  {
    return firstYear;
  }
  
  public int getLastYear()
  {
    return lastYear;
  }
  
  public String yearArg( String table )
  {
    if(( firstYear <= 0 ) && ( lastYear <= 0 ))
      return null;

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    
    if( firstYear == 0 )
      return t + "filmyear < " + lastYear;
    else if( lastYear == 0 )
      return t + "filmyear > " + firstYear;
    else if( firstYear == lastYear )
      return t + "filmyear = " + lastYear;
    else
      return t + "filmyear between " + firstYear + " and " + lastYear;
  }
  
  public void setQueryType( String queryType )
  {
    this.queryType = 1;

    if( queryType == null )
      return;

    if( queryType.equals( "2" ))
      this.queryType = 2;
    else if( queryType.equals( "3" ))
      this.queryType = 3;
    else if( queryType.equals( "4" ))
      this.queryType = 4;
  }

  public int getQueryType()
  {
    return queryType;
  }

  public void setFilmTitle( String filmTitle )
  {
    if(( filmTitle != null ) && ( filmTitle.length() > 0 ))
      this.filmTitle = filmTitle;
    else
      this.filmTitle = null;
  }

  public String getFilmTitle()
  {
    return filmTitle;
  }

  public String filmTitleArg( String table )
  {
    if( filmTitle == null )
      return null;
    
    String title = filmTitle.replaceAll( "'", "''" );

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return t + "title like '%" + title + "%'";
    else if( queryType == 3 )
      return t + "title like '%" + title + "'";
    else if( queryType == 4 )
      return t + "title = '" + title + "'";

    return t + "title like '" + title + "%'";
   }

  public void setDirectorNameId( String nameId )
  {
    try
    {
      this.name_id = nameId;
    }
    catch( NumberFormatException e )
    {
      this.name_id = null;
    }
  }
  
  public String getDirectorNameId()
  {
    return name_id;
  }
  
  public String directorNameIdArg( String table )
  {
    if( name_id == null )
      return null;
    
    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + "."; 

    return t + "name_id like '%|" + name_id + "|%'";
  }
  
  public void setDirector( String director )
  {
    if(( director != null ) && ( director.length() > 0 ))
      this.director = director;
    else
      this.director = null;
  }

  public String getDirector()
  {
    return director;
  }

  public String directorArg( String table )
  {
    if( director == null )
       return null;

    String d = director.replaceAll( "'", "''" );

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return t + "director like '%" + d + "%'";
    else if( queryType == 3 )
      return t + "director like '%" + d + "'";
    else if( queryType == 4 )
      return t + "director = '" + d + "'";

    return t + "director like '" + d + "%'";
  }
  
  private int parseYear( String year )
  {
    if( year == null )
      return 0;
    
    int y = 0;
    
    try
    {
      y = Integer.parseInt( year );
    }
    catch( Exception e )
    {
      y = 0;
    }
    return y;
  }
}
