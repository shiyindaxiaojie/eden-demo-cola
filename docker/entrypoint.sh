#!/bin/bash

JAVA_OPTS="${JAVA_OPTS} -server"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockCommercialFeatures"
JAVA_OPTS="${JAVA_OPTS} -XX:+AlwaysPreTouch -XX:+PrintFlagsFinal -XX:-DisplayVMOutput -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="${JAVA_OPTS} -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xss${JVM_XSS} -XX:MetaspaceSize=${JVM_MS}"

if [[ "${USE_G1_GC}" == "y" ]]; then
	echo "Garbage first collector is enabled."
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:G1LogLevel=finest -XX:MaxGCPauseMillis=200"
	JAVA_OPTS="${JAVA_OPTS} -XX:ConcGCThreads=6 -XX:ParallelGCThreads=8"
	JAVA_OPTS="${JAVA_OPTS} -XX:InitiatingHeapOccupancyPercent=45 -XX:G1ReservePercent=10 -XX:G1HeapWastePercent=5"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=60 -XX:G1HeapRegionSize=2m"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1MixedGCCountTarget=8 -XX:G1MixedGCLiveThresholdPercent=65"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled"
fi

if [[ "${USE_HEAP_DUMP}" == "y" ]]; then
	echo "Heap dump path is '${WORKDIR}/logs/jvm_heap_dump.hprof'."
	JAVA_OPTS="${JAVA_OPTS} -XX:HeapDumpPath=${WORKDIR}/logs/jvm_heap_dump.hprof -XX:+HeapDumpOnOutOfMemoryError"
fi

if [[ "${USE_GC_LOG}" == "y" ]]; then
    echo "GC log path is '/dev/shm/logs/jvm_gc.log'."
    JAVA_OPTS="${JAVA_OPTS} -Xloggc:/dev/shm/logs/jvm_gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
    JAVA_OPTS="${JAVA_OPTS} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=10m"
    JAVA_OPTS="${JAVA_OPTS} -XX:+PrintVMOptions -XX:+PrintGCCause -XX:+PrintGCApplicationStoppedTime"
	JAVA_OPTS="${JAVA_OPTS} -XX:+PrintTLAB -XX:+PrintReferenceGC -XX:+PrintHeapAtGC"
	JAVA_OPTS="${JAVA_OPTS} -XX:+FlightRecorder -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1"
    JAVA_OPTS="${JAVA_OPTS} -XX:+DebugNonSafepoints -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=500"
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
exec java $JAVA_OPTS -noverify -Djava.security.egd=file:/dev/./urandom "org.springframework.boot.loader.JarLauncher" "$@"
