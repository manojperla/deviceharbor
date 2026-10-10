package com.manojperla.deviceharbor;
import java.net.URI;
import java.nio.file.*;
import java.util.Properties;
record DeviceConfig(URI server, Path app, String device) {
    static DeviceConfig from(Properties p) {
        URI server = URI.create(p.getProperty("appium.server","http://127.0.0.1:4723"));
        if (!("http".equals(server.getScheme()) || "https".equals(server.getScheme())) || server.getHost()==null)
            throw new IllegalArgumentException("appium.server must be an HTTP(S) URL");
        String raw = p.getProperty("app", "");
        if (raw.isBlank()) throw new IllegalArgumentException("Supply -Dapp=/absolute/path/to/app-debug.apk");
        Path app = Path.of(raw).toAbsolutePath();
        if (!Files.isRegularFile(app) || !app.toString().endsWith(".apk")) throw new IllegalArgumentException("app must point to an existing APK");
        return new DeviceConfig(server,app,p.getProperty("device.name","Android Emulator"));
    }
}
