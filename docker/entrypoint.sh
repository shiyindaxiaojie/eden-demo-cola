#!/bin/bash

JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC"
JAVA_OPTS="${JAVA_OPTS} -server -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xmn${JVM_XMN} -XX:MetaspaceSize=${JVM_MS} -XX:MaxMetaspaceSize=${JVM_MMS}"
JAVA_OPTS="${JAVA_OPTS} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${WORKDIR}/logs/java_heapdump.hprof"
JAVA_OPTS="${JAVA_OPTS} -XX:-UseLargePages"
if [[ "${JVM_DEBUG}" == "y" ]]; then
	JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
fi

echo "The application will start in ${START_DELAY_SECS}s..."
sleep ${START_DELAY_SECS}
exec java $JAVA_OPTS -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom "org.springframework.boot.loader.JarLauncher" "$@"
