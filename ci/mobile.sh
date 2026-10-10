#!/usr/bin/env bash
set -euo pipefail
mkdir -p artifacts
appium --address 127.0.0.1 --port 4723 >artifacts/appium.log 2>&1 &
server_pid=$!
trap 'adb logcat -d >artifacts/logcat.log 2>&1 || true; kill "$server_pid" 2>/dev/null || true' EXIT
ready=false
for attempt in $(seq 1 60); do
  if curl --fail --silent http://127.0.0.1:4723/status >/dev/null; then ready=true; break; fi
  sleep 1
done
if [ "$ready" != true ]; then cat artifacts/appium.log; exit 1; fi
mvn --batch-mode --no-transfer-progress -Pmobile verify "-Dapp=$PWD/demo-app/app/build/outputs/apk/debug/app-debug.apk"
