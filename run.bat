@echo off
setlocal
REM UTF-8 helper: switch console code page and set encoding
chcp 65001 >NUL
set "JAVA_TOOL_OPTIONS=%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8 --enable-native-access=ALL-UNNAMED"
set "GRADLE_OPTS=%GRADLE_OPTS% -Dfile.encoding=UTF-8"

REM Install distribution and run
call "%~dp0gradlew.bat" installDist || exit /b %errorlevel%
set "APP_DIR=%~dp0build\install\tri-battle-gauntlet\bin"
call "%APP_DIR%\tri-battle-gauntlet.bat"

