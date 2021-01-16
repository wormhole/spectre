#!/bin/bash

if [ -n ${JAVA_HOME} ];then
	if [ -d ${JAVA_HOME} ];then
		if [ -f ${JAVA_HOME}/lib/tools.jar ];then
			${JAVA_HOME}/bin/java -Xbootclasspath/a:${JAVA_HOME}/lib/tools.jar -jar spectre-shell.jar `pwd`/spectre-agent.jar
		else
			echo Cound not found tools.jar
		fi
	else
		echo Cound not found path ${JAVA_HOME}
	fi
else
	echo Cound not set variable JAVA_HOME
fi