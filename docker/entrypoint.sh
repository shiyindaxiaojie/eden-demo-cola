#!/bin/bash

JAVA_MAJOR_VERSION=$(java -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')

JAVA_OPTS="${JAVA_OPTS} -server"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions"
JAVA_OPTS="${JAVA_OPTS} -XX:+AlwaysPreTouch -XX:+PrintFlagsFinal -XX:-DisplayVMOutput -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="${JAVA_OPTS} -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xss${JVM_XSS} -XX:MetaspaceSize=${JVM_MS}"

if [[ "${GC_MODE}" == "g1" ]]; then
	echo "GC mode is G1"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
	JAVA_OPTS="${JAVA_OPTS} -XX:ConcGCThreads=6 -XX:ParallelGCThreads=8"
	JAVA_OPTS="${JAVA_OPTS} -XX:InitiatingHeapOccupancyPercent=45 -XX:G1ReservePercent=10 -XX:G1HeapWastePercent=5"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=60"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1MixedGCCountTarget=8 -XX:G1MixedGCLiveThresholdPercent=65"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled"
fi

if [[ "${USE_GC_LOG}" == "y" ]]; then
    echo "GC log path is '${HOME}/logs/jvm_gc.log'."
    JAVA_OPTS="${JAVA_OPTS} -XX:+PrintVMOptions"
    if [[ "$JAVA_MAJOR_VERSION" -gt "8" ]] ; then
        JAVA_OPTS="${JAVA_OPTS} -Xlog:gc:file=${HOME}/logs/jvm_gc-%p-%t.log:tags,uptime,time,level:filecount=10,filesize=100M"
	else
		JAVA_OPTS="${JAVA_OPTS} -Xloggc:${HOME}/logs/jvm_gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps"
		JAVA_OPTS="${JAVA_OPTS} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
		JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCCause -XX:+PrintGCApplicationStoppedTime"
		JAVA_OPTS="${JAVA_OPTS} -XX:+PrintTLAB -XX:+PrintReferenceGC -XX:+PrintHeapAtGC"
		JAVA_OPTS="${JAVA_OPTS} -XX:+FlightRecorder -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1"
        JAVA_OPTS="${JAVA_OPTS} -XX:+DebugNonSafepoints -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=500"
	fi
fi

if [ ! -d "${HOME}/logs" ]; then
  mkdir ${HOME}/logs
fi

if [[ "${USE_HEAP_DUMP}" == "y" ]]; then
	echo "Heap dump path is '${HOME}/logs/jvm_heap_dump.hprof'."
	JAVA_OPTS="${JAVA_OPTS} -XX:HeapDumpPath=${HOME}/logs/jvm_heap_dump.hprof -XX:+HeapDumpOnOutOfMemoryError"
fi

if [[ "${USE_LARGE_PAGES}" == "y" ]]; then
	echo "Use large pages."
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseLargePages"
fi

if [[ "${DEBUG}" == "y" ]]; then
	echo "Attach to remote JVM using port 5005."
	JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
fi

JAVA_OPTS="${JAVA_OPTS} -Dserver.port=${SERVER_PORT} -Dmanagement.server.port=${MANAGEMENT_SERVER_PORT}"

echo "The application will start in ${START_DELAY_SECS}s."
sleep ${START_DELAY_SECS}
exec java $JAVA_OPTS -noverify -Djava.security.egd=file:/dev/./urandom "org.springframework.boot.loader.JarLauncher" "$@"
