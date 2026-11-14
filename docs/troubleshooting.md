# Diagnosing a DeviceHarbor run

Start with `mvn verify`. If that fails, fix compilation or configuration before
starting an emulator. Unit-check reports are in `target/surefire-reports`.

For a mobile-profile failure, follow the dependency order:

1. **APK:** build the demo and confirm the supplied `-Dapp` path points to the
   generated APK. Quote the whole property argument when the path contains spaces.
2. **Device:** run `adb devices`; the selected emulator must be ready, not offline.
   Supply `-Ddevice.udid=emulator-5554` (or the actual ID) if selection is ambiguous.
3. **Appium:** inspect `http://127.0.0.1:4723/status` and the server log.
   Confirm the documented UiAutomator2 driver is installed.
4. **Journey:** inspect `target/failsafe-reports` for the first test error and
   `target/device-artifacts` for screenshots/view hierarchies. Session creation
   failures may happen before journey evidence is available.
5. **CI:** inspect emulator setup, Appium logs, and Android logs before rerunning.

Use `mvn -Pmobile verify` with the APK property for the full result: Failsafe's
verify phase reports integration-test failures. A successful ordinary
`mvn verify` is not device coverage.

Include the command, commit SHA, emulator/API level, and first error in a report.
Review view hierarchies and screenshots before sharing evidence from a real app.
