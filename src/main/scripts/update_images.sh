#!/bin/bash

BASEDIR=~app_cinefiles_site
BINDIR=$BASEDIR/bin
LOGDIR=$BASEDIR/log
LOG=$LOGDIR/update_images.log

echo -n "Called from `pwd` at `date`" >> $LOG

export PATH=/bin:/usr/bin

if [[ -t 0 ]]
then
	echo " - INTERACTIVE" | tee -a $LOG
   $BINDIR/fetchBlobs.sh | tee -a $LOG
else
	echo "" >> $LOG
   $BINDIR/fetchBlobs.sh >> $LOG 2>&1
fi
