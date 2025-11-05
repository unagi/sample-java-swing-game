#!/usr/bin/env bash
set -euo pipefail
DIR="$(cd "$(dirname "$0")" && pwd)"
"$DIR/gradlew" installDist
"$DIR/build/install/tri-battle-gauntlet/bin/tri-battle-gauntlet"

