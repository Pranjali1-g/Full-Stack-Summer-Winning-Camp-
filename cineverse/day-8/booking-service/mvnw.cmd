@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-17"
set "PATH=%JAVA_HOME%\bin;%PATH%"
powershell -NoProfile -ExecutionPolicy Bypass -Command "& {expr (Get-Content -Raw '%~dp0.mvn\wrapper\maven-wrapper.properties' | ConvertFrom-StringData).distributionUrl; & '%~dp0.mvn\wrapper\maven-wrapper.jar' %*}"
if %ERRORLEVEL% NEQ 0 ( powershell -Command "Start-Process cmd -ArgumentList '/c cd /d %~dp0 && mvnw.cmd %*' -Verb RunAs" )