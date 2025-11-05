@echo off
setlocal
REM UTF-8 helper wrapper for Gradle on Windows
chcp 65001 >NUL
set "JAVA_TOOL_OPTIONS=%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8 --enable-native-access=ALL-UNNAMED"
set "GRADLE_OPTS=%GRADLE_OPTS% -Dfile.encoding=UTF-8"
call "%~dp0gradlew.bat" %*
exit /b %ERRORLEVEL%

