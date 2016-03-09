package edu.berkeley.mip.cinefiles.entity;

import javax.servlet.http.HttpServletRequest;

public class DocSearchArgs
{
  protected int queryType;
  protected String docType;
  protected String filmTitle;
  protected String docSubject;
  protected String docTitle;
  protected String docAuthor;
  protected String docSource;
  protected String pubDate;
  protected String lang_id;
  protected String[] contains;

  public DocSearchArgs()
  {
    filmTitle = docType = docSubject = docTitle = null;
    docAuthor = docSource = pubDate = lang_id = null;
    contains = null;
  }

  public DocSearchArgs( HttpServletRequest req )
  {
    setQueryType( req.getParameter( "querytype" ));

    setFilmTitle( req.getParameter( "filmtitle" ));
    setDocType( req.getParameter("doctype" ));
    setDocSubject( req.getParameter( "docsubj" ));
    setDocTitle( req.getParameter( "doctitle" ));
    setDocAuthor( req.getParameter( "docauthor" ));
    setDocSource( req.getParameter( "docsource" ));
    setPubDate( req.getParameter( "pubdate" ));
    setLangId( req.getParameter( "doclang" ));
    setContains( req.getParameterValues( "contains" ));
  }

  public void setQueryType( String queryType )
  {
    this.queryType = 1;

    if( queryType == null )
      this.queryType = 1;
    else if( queryType.equals( "2" ))
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
    
    String title = "cinefiles_denorm.normalizetext('" + filmTitle.replaceAll( "'", "''" ) + "')";

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return "cinefiles_denorm.normalizetext(" + t + "title) like '%' || " + title + " || '%'";
    else if( queryType == 3 )
      return "cinefiles_denorm.normalizetext(" + t + "title) like '%' || " + title;
    else if( queryType == 4 )
        // FIXME: Exact does the same thing as contains??
    	return "cinefiles_denorm.normalizetext(" + t + "title) like '%' || " + title + " || '%'";

    return "cinefiles_denorm.normalizetext(" + t + "title) like " + title + " || '%'";
   }

  public void setDocType( String docType )
  {
    if(( docType != null ) && ( docType.length() > 0 ))
      this.docType = docType;
    else
      this.docType = "1";
  }

  public String getDocType()
  {
    return docType;
  }

  public String docTypeArg( String table )
  {
    if(( docType == null ) || ( docType.equals( "1" )))
       return null;

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    String type = null;

    if( docType.equals( "36" )) type = "advertisement";
    else if( docType.equals( "2" )) type = "article";
    else if( docType.equals( "3" )) type = "bibliography";
    else if( docType.equals( "52" )) type = "biography";
    else if( docType.equals( "4" )) type = "book excerpt";
    else if( docType.equals( "46" )) type = "book review";
    else if( docType.equals( "37" )) type = "booklet";
    else if( docType.equals( "17" )) type = "broadcast transcript";
    else if( docType.equals( "45" )) type = "brochure";
    else if( docType.equals( "41" )) type = "budget";
    else if( docType.equals( "42" )) type = "caricature";
    else if( docType.equals( "5" )) type = "collection documents";
    else if( docType.equals( "24" )) type = "correspondence";
    else if( docType.equals( "32" )) type = "credits";
    else if( docType.equals( "6" )) type = "distributor materials";
    else if( docType.equals( "53" )) type = "essay";
    else if( docType.equals( "51" )) type = "exhibition catalog";
    else if( docType.equals( "26" )) type = "exhibitor manual";
    else if( docType.equals( "30" )) type = "filmography";
    else if( docType.equals( "8" )) type = "flyer";
    else if( docType.equals( "9" )) type = "intertitles";
    else if( docType.equals( "10" )) type = "interview";
    else if( docType.equals( "54" )) type = "lobby card";
    else if( docType.equals( "35" )) type = "manuscript";
    else if( docType.equals( "50" )) type = "monograph";
    else if( docType.equals( "11" )) type = "obituary";
    else if( docType.equals( "43" )) type = "other";
    else if( docType.equals( "40" )) type = "poetry";
    else if( docType.equals( "49" )) type = "postcard";
    else if( docType.equals( "39" )) type = "poster";
    else if( docType.equals( "12" )) type = "press kit";
    else if( docType.equals( "13" )) type = "press release";
    else if( docType.equals( "27" )) type = "program";
    else if( docType.equals( "15" )) type = "program note";
    else if( docType.equals( "16" )) type = "project proposal";
    else if( docType.equals( "18" )) type = "review";
    else if( docType.equals( "34" )) type = "score";
    else if( docType.equals( "19" )) type = "screening requirements";
    else if( docType.equals( "44" )) type = "screenplay";
    else if( docType.equals( "20" )) type = "shot analysis";
    else if( docType.equals( "48" )) type = "speech script";
    else if( docType.equals( "21" )) type = "still";
    else if( docType.equals( "22" )) type = "study guide";
    else if( docType.equals( "31" )) type = "subtitles";
    else if( docType.equals( "23" )) type = "synopsis";
    else if( docType.equals( "47" )) type = "treatment";

    return(( type == null ) ? null : t + "doctype = '" + type.replaceAll( "'", "''" ) + "'" );
  }

  public void setDocSubject( String docSubject )
  {
    if(( docSubject != null ) && ( docSubject.length() > 0 ))
       this.docSubject = docSubject;
    else
      this.docSubject = null;
  }

  public String getDocSubject()
  {
    return docSubject;
  }

  public String docSubjectArg( String table )

  {
    if( docSubject == null )
       return null;

    String subject = "cinefiles_denorm.normalizetext('" + docSubject.replaceAll( "'", "''" ) + "')";

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    // FIXME: All queryTypes do the same thing??
    
    if( queryType == 2 )
      return "(cinefiles_denorm.normalizetext(" + t + "docsubject) like '%' || " + subject + " || '%' or cinefiles_denorm.normalizetext(" + t + "docnamesubject) like '%' || " + subject + " || '%')";
    else if( queryType == 3 )
      return "(cinefiles_denorm.normalizetext(" + t + "docsubject) like '%' || " + subject + " || '%' or cinefiles_denorm.normalizetext(" + t + "docnamesubject) like '%' || " + subject + " || '%')";
    else if( queryType == 4 )
      return "(cinefiles_denorm.normalizetext(" + t + "docsubject) like '%' || " + subject + " || '%' or cinefiles_denorm.normalizetext(" + t + "docnamesubject) like '%' || " + subject + " || '%')";

    return "(cinefiles_denorm.normalizetext(" + t + "docsubject) like '%' || " + subject + " || '%' or cinefiles_denorm.normalizetext(" + t + "docnamesubject) like '%' || " + subject + " || '%')";
  }

  public void setDocTitle( String docTitle )
  {
    if(( docTitle != null ) && ( docTitle.length() > 0 ))
      this.docTitle = docTitle;
    else
      this.docTitle = null;
  }

  public String getDocTitle()
  {
    return docTitle;
  }

  public String docTitleArg( String table )
  {
    if( docTitle == null )
       return null;

    String doctitle = "cinefiles_denorm.normalizetext('" + docTitle.replaceAll( "'", "''" ) + "')";

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return "cinefiles_denorm.normalizetext(" + t + "doctitle) like '%' || " + doctitle + " || '%'";
    else if( queryType == 3 )
      return "cinefiles_denorm.normalizetext(" + t + "doctitle) like '%' || " + doctitle;
    else if( queryType == 4 )
      // FIXME: Exact does the same thing as contains??
      return "cinefiles_denorm.normalizetext(" + t + "doctitle) like '%' || " + doctitle + " || '%'";

    return "cinefiles_denorm.normalizetext(" + t + "doctitle) like " + doctitle + " || '%'";
  }

  public void setDocAuthor( String docAuthor )
  {
    if(( docAuthor != null ) && ( docAuthor.length() > 0 ))
      this.docAuthor = docAuthor;
    else
      this.docAuthor = null;
  }

  public String getDocAuthor()
  {
    return docAuthor;
  }

  public String docAuthorArg( String table )
  {
    if( docAuthor == null )
       return null;

    String author = "cinefiles_denorm.normalizetext('" + docAuthor.replaceAll( "'", "''" ) + "')";

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return "cinefiles_denorm.normalizetext(" + t + "author) like '%' || " + author + " || '%'";
    else if( queryType == 3 )
      return "cinefiles_denorm.normalizetext(" + t + "author) like '%' || " + author;
    else if( queryType == 4 )
      // FIXME: Exact does the same thing as contains??
      return "cinefiles_denorm.normalizetext(" + t + "author) like '%' || " + author + " || '%'";

    return "cinefiles_denorm.normalizetext(" + t + "author) like " + author + " || '%'";
  }

  public void setDocSource( String docSource )
  {
    if(( docSource != null ) && ( docSource.length() > 0 ))
      this.docSource = docSource;
    else
      this.docSource = null;
  }

  public String getDocSource()
  {
    return docSource;
  }

  public String docSourceArg( String table )
  {
    if( docSource == null )
      return docSource;

    String source = "cinefiles_denorm.normalizetext('" + docSource.replaceAll( "'", "''" ) + "')";

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return "cinefiles_denorm.normalizetext(" + t + "source) like '%' || " + source + " || '%'";
    else if( queryType == 3 )
      return "cinefiles_denorm.normalizetext(" + t + "source) like '%' || " + source;
    else if( queryType == 4 )
      // FIXME: Exact does the same thing as contains??
      return "cinefiles_denorm.normalizetext(" + t + "source) like '%' || " + source + " || '%'";

    return "cinefiles_denorm.normalizetext(" + t + "source) like " + source + " || '%'";
  }

  public void setPubDate( String pubDate )
  {
    if(( pubDate != null ) && ( pubDate.length() > 0 ))
      this.pubDate = pubDate;
    else
      this.pubDate = null;
  }

  public String getPubDate()
  {
    return pubDate;
  }

  public String docPubDateArg( String table )
  {
    if( pubDate == null )
      return pubDate;

    String pDate = "cinefiles_denorm.normalizetext('" + pubDate.replaceAll( "'", "''" ) + "')";
    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    if( queryType == 2 )
      return "cinefiles_denorm.normalizetext(" + t + "pubdate) like '%' || " + pDate + " || '%'";
    else if( queryType == 3 )
      return "cinefiles_denorm.normalizetext(" + t + "pubdate) like '%' || " + pDate;
    else if( queryType == 4 )
        // FIXME: Exact does the same thing as contains??
      return "cinefiles_denorm.normalizetext(" + t + "pubdate) like '%' || " + pDate + " || '%'";

    return "cinefiles_denorm.normalizetext(" + t + "pubdate) like " + pDate + " || '%'";
  }

  public void setLangId( String lang_id )
  {
    if(( lang_id != null ) && ( lang_id.length() > 0 ))
      this.lang_id = lang_id;
    else
      this.lang_id = "1";
  }

  public String getLangId()
  {
    return lang_id;
  }

  public String docLanguageArg( String table )
  {
    if( lang_id.equals( "1" ))
      return null;

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";
    String lang = null;

    if( lang_id.equals( "16" )) lang = "Arabic";
    else if( lang_id.equals( "39" )) lang = "Basque";
    else if( lang_id.equals( "52" )) lang = "Bulgarian";
    else if( lang_id.equals( "373" )) lang = "Cantonese";
    else if( lang_id.equals( "56" )) lang = "Catalan";
    else if( lang_id.equals( "67" )) lang = "Chinese";
    else if( lang_id.equals( "48" )) lang = "Czech";
    else if( lang_id.equals( "82" )) lang = "Danish";
    else if( lang_id.equals( "88" )) lang = "Dutch";
    else if( lang_id.equals( "92" )) lang = "Egyptian";
    else if( lang_id.equals( "111" )) lang = "French";
    else if( lang_id.equals( "122" )) lang = "German";
    else if( lang_id.equals( "130" )) lang = "Greek";
    else if( lang_id.equals( "137" )) lang = "Hebrew";
    else if( lang_id.equals( "141" )) lang = "Hindi";
    else if( lang_id.equals( "157" )) lang = "Italian";
    else if( lang_id.equals( "158" )) lang = "Japanese";
    else if( lang_id.equals( "181" )) lang = "Korean";
    else if( lang_id.equals( "375" )) lang = "Mandarin";
    else if( lang_id.equals( "245" )) lang = "Norwegian";
    else if( lang_id.equals( "267" )) lang = "Persian";
    else if( lang_id.equals( "269" )) lang = "Polish";
    else if( lang_id.equals( "271" )) lang = "Portuguese";
    else if( lang_id.equals( "283" )) lang = "Russian";
    else if( lang_id.equals( "293" )) lang = "Serbo-Croatian";
    else if( lang_id.equals( "305" )) lang = "Slovenian";
    else if( lang_id.equals( "312" )) lang = "Spanish";
    else if( lang_id.equals( "320" )) lang = "Swedish";
    else if( lang_id.equals( "329" )) lang = "Thai";
    else if( lang_id.equals( "343" )) lang = "Turkish";

    return t + "doclanguage ilike '%" + lang.replaceAll( "'", "''" ) + "%'";
  }

  public void setContains( String[] contains )
  {
    this.contains = contains;
  }

  public String[] getContains()
  {
    return contains;
  }

  public String docContainsArg( String table )
  {
    if( contains == null )
      return null;

    String t = ((table == null) || ( table.length() == 0 )) ? "" : table + ".";

    StringBuffer where = new StringBuffer();

      for( int i = 0; i < contains.length; i++ )
    {
      int x = 0;
      try
      {
        x = Integer.parseInt(contains[i]);

        if( i > 0 ) where.append(" and ");

        switch( x )
        {
        case 1:
          where.append(t + "filmog = TRUE ");
          break;
        case 2:
          where.append(t + "dist_co = TRUE ");
          break;
        case 3:
          where.append(t + "illust = TRUE ");
          break;
        case 4:
          where.append(t + "prod_co = TRUE ");
          break;
        case 5:
          where.append(t + "cast_cr = TRUE ");
          break;
        case 6:
          where.append(t + "costinfo = TRUE ");
          break;
        case 7:
          where.append(t + "tech_cr = TRUE ");
          break;
        case 8:
          where.append(t + "bx_info = TRUE ");
          break;
        }
      }
      catch( Exception e )
      {
        return null;
      }
    }
    return where.toString();
  }
}
