#!/bin/bash

BASEDIR=~app_cinefiles_site
SCRIPTDIR=$BASEDIR/src/cinefiles_jsp/src/main/scripts
LOGDIR=$BASEDIR/log
LOG=$LOGDIR/update_images.log

echo -n "Called from `pwd` at `date`" >> $LOG

export PATH=/bin:/usr/bin

if [[ -t 0 ]]
then
	echo " - INTERACTIVE" | tee -a $LOG
	$SCRIPTDIR/fetchBlobs.sh | tee -a $LOG
else
	echo "" >> $LOG
	$SCRIPTDIR/fetchBlobs.sh >> $LOG 2>&1
fi
