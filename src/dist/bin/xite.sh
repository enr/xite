#!/bin/bash

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
esac



# Resolve links: $0 may be a link to groovy's home.
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/.."
XITE_HOME="`pwd -P`"
cd "$SAVED"


#echo "xite home $XITE_HOME"

CLASSPATH=''
for jar in $(find $XITE_HOME/lib -type f -name '*.jar'); do
  CLASSPATH="${CLASSPATH}:${jar}"
done

# For Cygwin, switch paths to Windows format before running java
if $cygwin ; then
    XITE_HOME=`cygpath --path --mixed "$XITE_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`
fi

#echo $CLASSPATH

#STARTER_MAIN_CLASS="$XITE_HOME/plugins/xite/main.groovy"
STARTER_MAIN_CLASS="xite.XiteMain"

exec java -classpath "$CLASSPATH" \
        -Dxite.home="$XITE_HOME" \
        -Dlog4j.configuration=xite-logging.properties \
        $STARTER_MAIN_CLASS \
        "$@"


