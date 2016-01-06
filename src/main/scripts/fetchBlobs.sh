#!/bin/bash

SCRIPTDIR=~app_cinefiles_site/src/cinefiles_jsp/src/main/scripts

. ${SCRIPTDIR}/fetchBlobFuncs.sh

echo "2 SCRIPTDIR: $SCRIPTDIR"
echo "2 JPEGDIR: $JPEGDIR"

cd $JPEGDIR

QUERY="SELECT h.name, b.name \
       FROM blobs_common b \
       INNER JOIN hierarchy h \
          ON (b.id = h.id) \
       INNER JOIN collectionspace_core cc \
          ON (b.id = cc.id AND b.mimetype = 'image/tiff') \
       WHERE \
          cc.updatedat between '$LASTFETCHDATE' and now()"

if [ "$INTERACTIVE" -eq 0 ]
then
   echo $QUERY
   echo;echo -n "Continue...? [yes|no]"
   read ANS
   [ "$ANS" = "yes" ] || { echo "OK, aborting...";exit 1; }
fi

renameLastLog

$PSQL -t -c "$QUERY" > $QUERYLOG

ls -l $QUERYLOG

while read LINE
do
   IMGINFO=`echo "$LINE" | tr -d ' '`

   CSID="${IMGINFO%|*}"
   NAME="${IMGINFO#*|}"
	BT=false

   [ "${NAME##*.}" = "tiff" ] && NAME=${NAME%.tiff}.tif

   if [ "${NAME##*.}" = "tif" ]
   then
      if echo $NAME | grep 'dpi.tif'
      then
			BT=true
		fi
      NAME=`echo $NAME | sed "s/^\([0-9]*\.p[0-9]*\).*.tif$/\1.jpeg/"`
      msg "Checking $NAME"
      if [ "$BT" = "true" ]
      then
			fileExists "${NAME%.jpeg}.gif" && continue
      fi
      #fileExists "$NAME" || fetchBlob "$CSID"  "$NAME"
      echo "fetchBlob \"$CSID\"  \"$NAME\""
      fetchBlob "$CSID"  "$NAME"
   fi
done < $QUERYLOG

msg "Jpegs in download directory: $(ls -A . | grep jpeg$ | wc -l)"
thcount=$(ls -A t | grep jpeg$ | wc -l)

tcount=0
for f in *.jpeg
do
	[ -f "$f" ] && [ ! -f "t/$f" ] && createThumb $f && tcount=$((tcount+1))
done

msg "Thumbnails needed: $tcount"
msg "Thumbnails created: $(($(ls -A t | grep jpeg$ | wc -l)-thcount))"

# "-f" forces an overwrite, without it files that already exist are not moved
#mv2tree
mv2tree -f
