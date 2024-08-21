#!/bin/bash

JAVA_MAJOR_VERSION=$(java -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')

JAVA_OPTS="${JAVA_OPTS} -server"
JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions"
JAVA_OPTS="${JAVA_OPTS} -XX:+AlwaysPreTouch -XX:+PrintFlagsFinal -XX:-DisplayVMOutput -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="${JAVA_OPTS} -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xss${JVM_XSS} -XX:MetaspaceSize=${JVM_MS}"
JAVA_OPTS="${JAVA_OPTS} -XX:MaxGCPauseMillis=200"

if [[ "${GC_MODE}" == "ShenandoahGC" ]]; then
	echo "GC mode is ShenandoahGC"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseShenandoahGC"
elif [[ "${GC_MODE}" == "ZGC" ]]; then
	echo "GC mode is ZGC"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseZGC"
elif [[ "${GC_MODE}" == "G1" ]]; then
	echo "GC mode is G1"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC"
	JAVA_OPTS="${JAVA_OPTS} -XX:InitiatingHeapOccupancyPercent=45 -XX:G1ReservePercent=10 -XX:G1HeapWastePercent=5"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=60"
	JAVA_OPTS="${JAVA_OPTS} -XX:G1MixedGCCountTarget=8 -XX:G1MixedGCLiveThresholdPercent=65"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled"
elif [[ "${GC_MODE}" == "CMS" ]]; then
	echo "GC mode is CMS"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseConcMarkSweepGC -Xmn${JVM_XMN:-512m}"
	JAVA_OPTS="${JAVA_OPTS} -XX:ParallelGCThreads=${PARALLEL_GC_THREADS:-2} -XX:ConcGCThreads=${CONC_GC_THREADS:-1}"
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=92"
	JAVA_OPTS="${JAVA_OPTS} -XX:+CMSClassUnloadingEnabled -XX:+CMSScavengeBeforeRemark"
	if [[ "$JAVA_MAJOR_VERSION" -le "8" ]] ; then
		JAVA_OPTS="${JAVA_OPTS} -XX:+CMSIncrementalMode -XX:CMSFullGCsBeforeCompaction=5"
		JAVA_OPTS="${JAVA_OPTS} -XX:+ExplicitGCInvokesConcurrent -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses"
	fi
fi

if [[ "${USE_GC_LOG}" == "Y" ]]; then
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

if [[ "${USE_HEAP_DUMP}" == "Y" ]]; then
	echo "Heap dump path is '${HOME}/logs/jvm_heap_dump.hprof'."
	JAVA_OPTS="${JAVA_OPTS} -XX:HeapDumpPath=${HOME}/logs/jvm_heap_dump.hprof -XX:+HeapDumpOnOutOfMemoryError"
fi

if [[ "${USE_LARGE_PAGES}" == "Y" ]]; then
	echo "Use large pages."
	JAVA_OPTS="${JAVA_OPTS} -XX:+UseLargePages"
fi

if [[ "${JDWP_DEBUG}" == "Y" ]]; then
	echo "Attach to remote JVM using port ${JDWP_PORT}."
	JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=${JDWP_PORT},server=y,suspend=n"
fi

JAVA_OPTS="${JAVA_OPTS} -Dserver.port=${SERVER_PORT} -Dmanagement.server.port=${MANAGEMENT_SERVER_PORT}"

echo "The application will start in ${START_DELAY_SECS}s."
sleep ${START_DELAY_SECS}
exec java $JAVA_OPTS -noverify -Djava.security.egd=file:/dev/./urandom "org.springframework.boot.loader.JarLauncher" "$@"
