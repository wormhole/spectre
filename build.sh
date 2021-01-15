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

rm -rf build
mkdir build
cp ./spectre-agent/target/spectre-agent-1.0.0.jar ./build/spectre-agent.jar
cp ./spectre-shell/target/spectre-shell-1.0.0.jar ./build/spectre-shell.jar
cp ./spectre-shell.* ./build/