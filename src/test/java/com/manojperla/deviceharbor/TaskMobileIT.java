package com.manojperla.deviceharbor;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
class TaskMobileIT {
    private AndroidDriver driver;
    private TaskScreen screen;
    @BeforeEach void start() throws Exception {
        var c=DeviceConfig.from(System.getProperties());
        var options=new UiAutomator2Options().setDeviceName(c.device()).setApp(c.app().toString()).setFullReset(true);
        String udid=System.getProperty("device.udid"); if (udid!=null) options.setUdid(udid);
        driver=new AndroidDriver(c.server().toURL(),options); screen=new TaskScreen(driver);
    }
    @AfterEach void stop(TestInfo info) throws Exception {
        if(driver==null) return;
        try {
            Path dir=Path.of("target","device-artifacts"); Files.createDirectories(dir);
            String name=info.getTestMethod().orElseThrow().getName();
            Files.write(dir.resolve(name+".png"),driver.getScreenshotAs(OutputType.BYTES));
            Files.writeString(dir.resolve(name+".xml"),driver.getPageSource());
        } finally { driver.quit(); }
    }
    @Test void addsAndCompletesTask() { screen.add("Review release"); assertEquals("Review release",screen.title()); screen.complete(); assertEquals("Completed",screen.status()); }
    @Test void deletesTask() { screen.add("Remove me"); assertEquals("Remove me",screen.title()); screen.delete(); assertTrue(screen.empty()); }
    @Test void rejectsBlankTask() { screen.add("   "); assertEquals("Enter a task title",screen.error()); assertTrue(screen.empty()); }
}
