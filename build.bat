cd spectre-parent
call mvn clean install
cd ../spectre-common
call mvn clean install
cd ../spectre-transport
call mvn clean install
cd ../spectre-agent
call mvn clean install
cd../spectre-shell
call mvn clean install
cd ..

md build
copy %~dp0\spectre-agent\target\spectre-agent-*.jar %~dp0\build\spectre-agent.jar
copy %~dp0\spectre-shell\target\spectre-shell-*.jar %~dp0\build\spectre-shell.jar