package edu.berkeley.mip.cinefiles.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class DocImages implements Serializable
{
  /*
   * DocImages represents a list of image files for a particular document. It
   * will normally be initialized with a document Id, and the path to the
   * documents will be set by the parent class if the default path is not
   * correct. The default path is the path used on the WebFarm server.
   * 
   * After doing the lookup, which requires a physical path, the path names are
   * modified to use the URL path to get the file URLs.
   */
  
  private static final long serialVersionUID = 1L;

  private static final String defaultDir =
    "/home/app_cinefiles_site/tomcat6-cinefiles-site/cinefiles/data/doc_img";

  private static final String logfile =
    "/home/app_cinefiles_site/tomcat6-cinefiles-site/logs/apps/cine/DocImages.log";

  private static final String urlPath = "/cinefiles/doc_img";

  protected ArrayList<String> errors;
  protected String[] images;

  private String imageDir;
  private String srcDir;
  private String beanDate;
  private int docId;
  private FileWriter log = null;
  private boolean traceFlag = false;

  // Just because there should be a default Constructor.
  public DocImages()
  {
    this(defaultDir, 0);
  }

  // This is the Constructor that will normally be used.
  public DocImages( int docId )
  {
    this(defaultDir, docId);
  }

  // Use this is the default image path needs to be changed.
  public DocImages( String imageDir, int docId )
  {
    this.docId = docId;
    this.imageDir = imageDir.replaceAll("/$", "");

    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy 'at' kk:mm:ss");
    formatter.setTimeZone(TimeZone.getTimeZone("PST"));

    this.beanDate = formatter.format(new Date());

    if( (docId > 0) && (imageDir != null) && (imageDir.length() > 1) )
    {
      lookupImages();
    }
    else
      errorMsg("Not running lookupImages from Constructor");
  }

  // Can be used with the "no-args" Constructor.
  public void setDocId( int docId )
  {
    if( docId >= 0 )
      this.docId = docId;
    else
      this.docId = 0;
  }

  // Can be used with the "no-args" Constructor
  public void setImageDir( String imageDir )
  {
    this.imageDir = imageDir.replaceAll("/$", "");
  }

  // Does the actual file search.
  public void lookupImages()
  {
    int dir = (docId / 500) + 1;

    srcDir = imageDir + "/www_imgs." + dir;
    imageDir = urlPath + "/www_imgs." + dir;
    
    logMsg( "Looking in " + srcDir );

    File www_imgs = new File( srcDir );

    if( !www_imgs.isDirectory() )
    {
      errorMsg("lookupImages: " + srcDir + " - Not a directory");
      return;
    }

    images = www_imgs.list(new CinefilesFilter());

    if( images == null )
    {
      errorMsg("lookupImages: for " + www_imgs + " Failed.");
      return;
    }

    Arrays.sort(images, new PgComparator());
  }

  // Accessor method for the name of the image directory
  public String getImageDir()
  {
    return imageDir;
  }

  // Accessor method for absolute path to image directory
  public String getSrcDir()
  {
    return srcDir;
  }
  
  // Accessor method for the String array of file names
  public String[] getImageList()
  {
    return images;
  }

  // Returns the number of items in the array of file names
  public int getCount()
  {
    return images.length;
  }

  // Useful for debuggin, get the error messages and trace messages
  // collected into the ArrayList of messages.
  public ArrayList<String> getErrorMsgs()
  {
    return errors;
  }

  // A convenience method for writing error message to the ArrayList
  private void errorMsg( String msg )
  {
    if( errors == null )
    {
      this.errors = new ArrayList<String>();
      errors.add("Error list initialized at: " + beanDate);
    }
    errors.add(msg);
  }

  private void logMsg( String msg )
  {
    if( ! traceFlag )
      return;

    if( log == null )
    {
      try
      {
        log = new FileWriter( logfile, true );
      }
      catch( Exception e )
      {
        traceFlag = false;
      }
    }

    if( log == null )
      return;

    try
    {
      log.write( msg + "\n" );
      log.close();
    }
    catch( Exception e ) {}
  }

  // A FilenameFilter to be used with the "File.list" method
  class CinefilesFilter implements FilenameFilter
  {
    public boolean accept( File dir, String name )
    {
      return(name.startsWith(docId + ".p") &&
            (name.endsWith(".jpeg") || name.endsWith(".gif")));
    }
  }

  // A comparator for sorting arrays of page images, sorts on page number
  class PgComparator implements Comparator<String>
  {
    public int compare( String arg1, String arg2 )
    {
      if( arg1.equals(arg2) )
      {
        return 0;
      }

      try
      {
        String s1 = arg1.replaceFirst("^\\d*\\.p", "");
        String s2 = arg2.replaceFirst("^\\d*\\.p", "");
        s1 = s1.replaceAll("\\.\\w*$", "");
        s2 = s2.replaceAll("\\.\\w*$", "");
        int i1 = Integer.parseInt(s1);
        int i2 = Integer.parseInt(s2);
        return(i1 - i2);
      }
      catch( Exception e )
      {}

      return -1;
    }
  }
}
