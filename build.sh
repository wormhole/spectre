#!/bin/sh

cd spectre-parent
call mvn clean install
cd ../spectre-transport
call mvn clean install
cd ../spectre-agent
call mvn clean install
cd ../spectre-shell
call mvn clean install
cd ..