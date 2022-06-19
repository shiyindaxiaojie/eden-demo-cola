#!/bin/sh

APP_DIR="../target"
APP_PATTERN="$APP_DIR/*.jar"
APP_NAME=$(ls -l $APP_PATTERN | awk '{print $9}')
APP_JVM="-server -Xms512m -Xmx512m"
APP_PID=$(ps -ef | grep java | grep $APP_NAME | grep -v grep | awk '{print $2}')

if [ $1 == "start" ]; then
	if [ $JAVA_HOME ]; then
		echo JAVA_HOME: $JAVA_HOME
		echo JAVA_VERSION:
		java -version
	fi
	echo start $APP_NAME
	echo "nohup java $APP_JVM -jar $APP_NAME --spring.config.additional-location=../conf/ --logging.file.path=../logs/ >/dev/null 2>&1 &"
	nohup java $APP_JVM -jar $APP_NAME --spring.config.additional-location=../conf/ --logging.file.path=../logs/ >/dev/null 2>&1 &
elif [ $1 == "stop" ]; then
	echo "stop $APP_NAME($APP_SERVER)"
	if [ ! $APP_PID ]; then
		echo stop $APP_NAME failed due to running pid not found
	else
		echo "kill -9 $APP_PID"
		kill -9 $APP_PID
	fi
fi
