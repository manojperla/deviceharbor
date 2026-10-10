# DeviceHarbor

Java 17 + Appium Android automation framework with screen objects and a tiny native Java demo app.

## Quick checks (no device)

Install JDK 17 and Maven 3.9+, then run `mvn verify`. This compiles the mobile tests
and runs four configuration checks. It does **not** run device journeys.

## Run on Android

1. Install Android Studio/SDK, platform 35, build tools, and an API 30+ emulator. Set `ANDROID_HOME` and add platform-tools to PATH.
2. Install Gradle 8.9 and run `gradle -p demo-app assembleDebug`.
3. With Node 22 installed, run `npm install -g appium@2.19.0` then `appium driver install uiautomator2@4.2.9`.
4. Start the emulator; confirm `adb devices` shows one ready device.
5. In another terminal run `appium --address 127.0.0.1 --port 4723`.
6. Run `mvn -Pmobile verify -Dapp=/absolute/path/to/demo-app/app/build/outputs/apk/debug/app-debug.apk`.
   In PowerShell quote the entire `"-Dapp=C:/path with spaces/app-debug.apk"` argument.

Optional properties: `-Dappium.server=http://127.0.0.1:4723`, `-Ddevice.name=Android`,
and `-Ddevice.udid=emulator-5554` when multiple devices are connected.
Missing APKs fail with a clear error; mobile runs are never silently skipped.

## Journeys and design

- Add and complete a task; delete a task; reject whitespace-only input.
- Screen objects use accessibility IDs and explicit waits. Each test installs a fresh app session.
- Screenshots and view hierarchies are captured after every journey in `target/device-artifacts`.
- JUnit XML results are in `target/failsafe-reports`. CI additionally uploads Appium and Android logs.
- The demo intentionally holds a single task in memory; it has no accounts, network, or production data.

GitHub Actions builds the APK and runs the three journeys on a Linux-hosted Android API 30
emulator with UiAutomator2. The Java client 9.4.0 and Selenium 4.33.0 are pinned as a compatible pair.
Android is the implemented platform. iOS requires a separate XCUITest driver, macOS host,
app build, and screen adaptation; it is not implemented here.

## Troubleshooting

Connection refused: check Appium `/status`. No device: check `adb devices` and UDID.
Session failure: confirm the APK is an absolute readable path and UiAutomator2 is installed.
Emulator failures in CI: inspect the emulator step and attached logs before rerunning.
The `mobile` Maven profile enables Failsafe integration tests; ordinary `verify` only runs unit checks.
MIT licensed. Created by Manoj Perla.
