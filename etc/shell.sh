#!/bin/bash

clear

DIR=`dirname $0`
DIR=`cd $DIR; pwd`

OS=`uname -s`

SETUP=setup-${OS}.env

if [ -f "$DIR/$SETUP" ] ;
then
  echo "+++ sourcing $SETUP file ... "
  . $DIR/$SETUP
else
  echo "+++ sourcing default file ..."
  . $DIR/setup.env
fi

cd ../

exec /bin/bash -i -l
