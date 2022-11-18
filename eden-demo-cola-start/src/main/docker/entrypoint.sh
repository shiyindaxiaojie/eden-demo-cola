#!/bin/sh

echo "The application will start in ${JAVA_SLEEP}s..." && sleep ${JAVA_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "org.ylzl.eden.demo.ColaApplication" "$@"
