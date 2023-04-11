@echo off
rem A universal Talkie launcher for Windows

set CLASSPATH=io.github.shaharpr.talkie.Server

where java > nul

if %ERRORLEVEL% neq 0 goto javaNotFound

FOR /F "tokens=*" %%g IN ('dir /b Talkie-*-jar*.jar') do (SET JAR=%%g)

java -cp %JAR% %CLASSPATH% %1

goto EOF

:javaNotFound
echo Java is not found. Install Java from https://java.com
goto EOF