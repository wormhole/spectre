@echo off

if exist "%JAVA_HOME%" (
    if exist "%JAVA_HOME%\lib\tools.jar" (
        "%JAVA_HOME%\bin\java" -Xbootclasspath/a:"%JAVA_HOME%\lib\tools.jar" -jar spectre-shell.jar %~sdp0\spectre-agent.jar
    ) else (
        echo Could not found tools.jar
    )
) else (
    echo Could not found variable JAVA_HOME
)