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

rd /s /q build
md build
copy %~sdp0\spectre-agent\target\spectre-agent-1.1.0.jar %~sdp0\build\spectre-agent.jar
copy %~sdp0\spectre-shell\target\spectre-shell-1.1.0.jar %~sdp0\build\spectre-shell.jar
copy %~sdp0\spectre-shell.* %~sdp0\build\