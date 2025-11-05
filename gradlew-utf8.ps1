[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding
$env:JAVA_TOOL_OPTIONS = "$($env:JAVA_TOOL_OPTIONS) -Dfile.encoding=UTF-8 --enable-native-access=ALL-UNNAMED"
$env:GRADLE_OPTS = "$($env:GRADLE_OPTS) -Dfile.encoding=UTF-8"
& "$PSScriptRoot\gradlew.bat" @args

