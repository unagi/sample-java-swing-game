@echo off
setlocal
REM 文字化け対策: コンソールコードページをUTF-8に変更（一時）
chcp 65001 >NUL
set "JAVA_TOOL_OPTIONS=%JAVA_TOOL_OPTIONS% -Dfile.encoding=UTF-8 --enable-native-access=ALL-UNNAMED"
set "GRADLE_OPTS=%GRADLE_OPTS% -Dfile.encoding=UTF-8"

REM 配布実行ヘルパー: Gradleでインストール済みディストを起動
call "%~dp0gradlew.bat" installDist || exit /b %errorlevel%
set APP_DIR=%~dp0build\install\tri-battle-gauntlet\bin
call "%APP_DIR%\tri-battle-gauntlet.bat"
