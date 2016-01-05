#!/bin/bash

### last update was  55

BASEDIR=/home/app_cinefiles_site
LOGDIR=~/log
LOG=$LOGDIR/update_featured.log
TOMCAT='tomcat6-cinefiles-site'
INCDIR=~/$TOMCAT/webapps/cinefiles/include

export PATH=$BASEDIR/bin:$PATH

echo  "$(date): running update_featured" >> $LOGDIR/run.log

#d=`date +'%w'`
#m=`date +'%M'`
#m=${m#0}
#h=`date +'%k'`

#if [ $d -ne 0 ]
#then
#  echo "$(date): Wrong time at $d $h $m" >> $LOG
#  exit
#fi

cd $INCDIR/featured

if [ -f currentfeatured ]
then
  cf=`cat currentfeatured`
else
  echo "$(date): No currentfeatured in `pwd`" >> $LOG
  exit 1
fi

if [ $cf -ge 0 ]
then
  cnt=`ls featured.jspf.* | wc -l`
else
  echo "$(date): Wrong current featured: $cf in `pwd`" >> $LOG
  exit 1
fi

if [ $cnt -gt 0 ]
then
  nf=$cf
else
  echo "$(date): No files found in `pwd`" >> $LOG
  exit 1
fi

while [ $cf -eq $nf ]
do
  nf=$((RANDOM%$cnt))
done

echo $nf > currentfeatured

echo "$(date): moving featured.jspf.$nf to featured.jspf" >> $LOG

ln -sf $INCDIR/featured/featured.jspf.$nf $INCDIR/featured.jspf
