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
cp ./spectre-agent/target/spectre-agent-1.0.0.jar ./build/spectre-agent.jar
cp ./spectre-shell/target/spectre-shell-1.0.0.jar ./build/spectre-shell.jar
cp ./spectre-shell.* ./build/