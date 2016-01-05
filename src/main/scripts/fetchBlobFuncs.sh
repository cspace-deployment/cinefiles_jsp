#
# fetchBlobFuncs.sh - sourced from fetchblobs.sh
#

BASEDIR=~app_cinefiles_site
BINDIR=$BASEDIR/bin
export PATH=$PATH:$BINDIR
#export LD_LIBRARY_PATH=/users/mipweb/local/lib

export PGUSER=miponca
export PGDATABASE=cinefiles_domain_cinefiles
export PGHOST=dba-postgres-prod-32.ist.berkeley.edu
export PGPORT=5313

DATA="$BASEDIR/tomcat6-cinefiles-site/cinefiles/data"
INCOMING="$DATA/incoming"
JPEGDIR="$INCOMING/jpegs"
LOGDIR="$BASEDIR/log/fetchBlobs"
QUERYLOG="$LOGDIR/query.log"
MVLOG="$LOGDIR/mv2tree.log"
IMGDIR="$DATA/doc_img"

echo "DATA: $DATA"
echo "INCOMING: $INCOMING"
echo "JPEGDIR: $JPEGDIR"
echo "QUERYLOG: $QUERYLOG"
echo "MVLOG: $MVLOG"
echo "IMGDIR: $IMGDIR"

PSQL=/usr/bin/psql

LASTFETCHDATE=`tail -1 $LOGDIR/lastfetchdate`
echo `date +'%Y-%m-%d %H:%M:%S'` >> $LOGDIR/lastfetchdate
echo $LASTFETCHDATE

if [[ -t 0 ]]
then
	INTERACTIVE=0
else
	INTERACTIVE=1
fi

function msg()
{
   [ "$INTERACTIVE" -eq 0 ] && echo "$1"
}

function exit_with_msg()
{
	echo $1
   exit 1
}

function renameLastLog()
{
   if [ -f $LOGDIR/query.log ]
   then
      n=1
      while [ -f $LOGDIR/query.log.$n ]
      do
         n=$((n+1))
      done
      mv $LOGDIR/query.log $LOGDIR/query.log.$n
   fi
	
   [ -f $LOGDIR/query.log ] && exit_with_msg "Unable to re-name last log"
}

[ -d ${JPEGDIR}/t ]  || exit_with_msg "Unable to find jpeg directory"

function fileExists()
{
   doc_id=${1%%.*}
   doc_dir="$IMGDIR/www_imgs.$((doc_id / 500 + 1))"
   [ ! -f "$doc_dir/$1" ] && return 1
   return 0
}

function fetchBlob()
{
   CSID="$1"
   NAME="$2"
   #HOST="https://cinefiles.cspace.berkeley.edu"
   HOST="http://localhost:8084"
   SRVC="cspace-services/blobs"
   CONTENT="derivatives/OriginalJpeg/content"
   CACERT="/home/app_cinefiles_site/certs/CA-bundle.cer"
   LOG="$LOGDIR/imagelog.txt"
	URL="$HOST/$SRVC/$CSID/$CONTENT"

   #echo "curl -n -s -o \"$NAME\" \"$URL\"  --cacert \"$CACERT\""
   #curl -n -s -o "$NAME" "$URL"  --cacert "$CACERT" 2>> "$LOG"
   echo "curl -n -s -o \"$NAME\" \"$URL\""
   curl -n -s -o "$NAME" "$URL"
}

function createThumb()
{
   #FINFO=`identify $1 | sed "s/^[^ ]* [^ ]* \([^ ]*\).*$/\1/"`
   #W=${FINFO%x*}
   #H=${FINFO#*x}
   #echo "JPEG Size: WxH = ${W}x${H}"

   #if [ $W -gt $H ]
   #then
   #   LONGSIDE=$W
   #   SHORTSIDE=$H
   #else
   #   LONGSIDE=$H
   #   SHORTSIDE=$W
   #fi

   #FACT=$(((LONGSIDE+30)/60))

   #LONGSIDE=60
   #SHORTSIDE=$(((SHORTSIDE+(FACT/2))/FACT))

   #if [ $W -gt $H ]
   #then
   #   W=$LONGSIDE
   #   H=$SHORTSIDE
   #else
   #   H=$LONGSIDE
   #   W=$SHORTSIDE
   #fi

   #echo "Thumb size: ${W}x${H}"

   #convert $1 -resize ${W}x${H} t/$1
   #convert $1 -thumbnail 60x60 -quality 65 t/$1
   echo "convert $1 -strip -thumbnail 60x60 -quality 65 t/$1"
   convert $1 -strip -thumbnail 60x60 -quality 65 t/$1
}

function mv2tree()
{
   echo "mv2tree called: `date`"

   if [ "$1" = "-f" ]
   then
      FORCE="-f"
      echo "Moving with FORCE"
   else
      FORCE=""
   fi

   for f in *.gif *.jpeg
   do
      if [ -f $f -a -s $f -a ! -L $f ]
      then
         chmod 444 $f
   
         doc_id=${f%%.*}
         doc_dir=$(($doc_id / 500 + 1))
         doc_dir="$IMGDIR/www_imgs.$doc_dir"
   
         if [ ! -d "$doc_dir" ]
         then
            echo Creating "$doc_dir"
            mkdir "$doc_dir"
            mkdir "${doc_dir}/t"
            chmod 775 "$doc_dir" "${doc_dir}/t"
         fi
   
         if [ -d "$doc_dir" ] && [ -n "$FORCE" -o ! -f "${doc_dir}/$f" ]
         then
            echo Moving "$f" to "${doc_dir##*/}/$f"
            mv $FORCE "$f" "$doc_dir/$f"
   
            if [ -d "${doc_dir}/t" -a -f "./t/$f" ]
            then
               echo Moving "./t/$f" to "${doc_dir##*/}/t/$f"
               mv $FORCE "./t/$f" "$doc_dir/t/$f"
            else
               echo "Not moving ./t/$f to $doc_dir/t/$f"
            fi
         else
            echo "Not moving $f to $doc_dir/$f"
         fi
      fi
   done
} >> $MVLOG
