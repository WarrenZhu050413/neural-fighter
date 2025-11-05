#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JAVA_HOME_DEFAULT="$HOME/.jdks/jdk-21.0.9+10/Contents/Home"

export JAVA_HOME="${JAVA_HOME:-$JAVA_HOME_DEFAULT}"
export PATH="$JAVA_HOME/bin:$PATH"

cd "$PROJECT_ROOT"
./gradlew runClient "$@"
