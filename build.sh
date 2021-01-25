#!/bin/sh

cd spectre-parent
mvn clean install
cd spectre-common
mvn clean install
cd ../spectre-transport
mvn clean install
cd ../spectre-agent
mvn clean install
cd ../spectre-shell
mvn clean install
cd ..

rm -rf build
mkdir build
cp ./spectre-agent/target/spectre-agent-1.1.0.jar ./build/spectre-agent.jar
cp ./spectre-shell/target/spectre-shell-1.1.0.jar ./build/spectre-shell.jar
cp ./spectre-shell.bat ./build/spectre-shell.bat
cp ./spectre-shell.sh ./build/spectre-shell.sh