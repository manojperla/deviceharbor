package com.manojperla.deviceharbor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
class DeviceConfigTest {
    @TempDir Path temp;
    @Test void missingAppFailsClearly() { assertThrows(IllegalArgumentException.class,()->DeviceConfig.from(new Properties())); }
    @Test void missingFileFails() { var p=new Properties(); p.setProperty("app",temp.resolve("absent.apk").toString()); assertThrows(IllegalArgumentException.class,()->DeviceConfig.from(p)); }
    @Test void invalidServerFails() { var p=new Properties(); p.setProperty("appium.server","file:///tmp"); assertThrows(IllegalArgumentException.class,()->DeviceConfig.from(p)); }
    @Test void acceptsExistingApkAndDefaultServer() throws Exception {
        var p=new Properties(); Path apk=Files.createFile(temp.resolve("app.apk")); p.setProperty("app",apk.toString());
        var c=DeviceConfig.from(p); assertEquals(apk,c.app()); assertEquals("127.0.0.1",c.server().getHost());
    }
}
