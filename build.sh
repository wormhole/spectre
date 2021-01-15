#!/bin/sh

cd spectre-parent
call mvn clean install
cd spectre-common
call mvn clean install
cd ../spectre-transport
call mvn clean install
cd ../spectre-agent
call mvn clean install
cd ../spectre-shell
call mvn clean install
cd ..

mkdir build
cp ./spectre-agent/target/spectre-agent-*.jar ./build/spectre-agent.jar
cp ./spectre-shell/target/spectre-shell-*.jar ./build/spectre-shell.jar