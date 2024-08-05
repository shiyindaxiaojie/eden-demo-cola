#!/bin/bash

JAVA_OPTS="${JAVA_OPTS} -server -XX:+AlwaysPreTouch -XX:-DisplayVMOutput -XX:+PrintFlagsFinal"
JAVA_OPTS="${JAVA_OPTS} -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockCommercialFeatures"
JAVA_OPTS="${JAVA_OPTS} -XX:+FlightRecorder -XX:+DebugNonSafepoints -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=500"
JAVA_OPTS="${JAVA_OPTS} -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1"
JAVA_OPTS="${JAVA_OPTS} -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xss${JVM_XSS} -XX:MetaspaceSize=${JVM_MS} -XX:MaxMetaspaceSize=${JVM_MMS}"
JAVA_OPTS="${JAVA_OPTS} -XX:ConcGCThreads=6 -XX:ParallelGCThreads=8"

if [[ "${USE_G1_GC}" == "y" ]]; then
	echo "G1 garbage collection is enabled."
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:G1LogLevel=finest -XX:G1NewSizePercent=50 -XX:MaxGCPauseMillis=200"
	JAVA_OPTS="${JAVA_OPTS} -XX:InitiatingHeapOccupancyPercent=45 -XX:G1MixedGCLiveThresholdPercent=65"
fi

if [[ "${USE_HEAP_DUMP}" == "y" ]]; then
	echo "Heap dump path is '${WORKDIR}/logs/jvm_heap_dump.hprof'."
	JAVA_OPTS="${JAVA_OPTS} -XX:HeapDumpPath=${WORKDIR}/logs/jvm_heap_dump.hprof -XX:+HeapDumpOnOutOfMemoryError"
fi

if [[ "${USE_GC_LOG}" == "y" ]]; then
    echo "GC log path is '/dev/shm/logs/jvm_gc.log'."
	JAVA_OPTS="${JAVA_OPTS} -XX:LogFile=/dev/shm/logs/jvm_gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=10m"
	JAVA_OPTS="${JAVA_OPTS} -XX:+PrintReferenceGC -XX:+PrintHeapAtGC -XX:+PrintGCApplicationStoppedTime"
fi

if [[ "${USE_LARGE_PAGES}" == "y" ]]; then
	echo "Use large pages."
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseLargePages"
fi

if [[ "${DEBUG}" == "y" ]]; then
	echo "Attach to remote JVM using port 5005."
	JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"
fi

echo "The application will start in ${START_DELAY_SECS}s."
sleep ${START_DELAY_SECS}
exec java ${JAVA_OPTS} -noverify -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "org.ylzl.eden.demo.ColaApplication" "$@"
