@echo off
setlocal
REM 配布実行ヘルパー: Gradleでインストール済みディストを起動
call "%~dp0gradlew.bat" installDist || exit /b %errorlevel%
set APP_DIR=%~dp0build\install\tri-battle-gauntlet\bin
call "%APP_DIR%\tri-battle-gauntlet.bat"

