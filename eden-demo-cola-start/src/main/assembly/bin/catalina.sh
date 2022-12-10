#!/bin/sh

#
# Copyright 2012-2019 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

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
