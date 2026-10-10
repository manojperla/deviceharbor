package com.manojperla.deviceharbor;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
final class TaskScreen {
    private final AndroidDriver driver;
    private final WebDriverWait wait;
    TaskScreen(AndroidDriver driver) { this.driver=driver; wait=new WebDriverWait(driver,Duration.ofSeconds(10)); }
    void add(String title) {
        var input=wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("task-input")));
        input.sendKeys(title);
        try { driver.hideKeyboard(); } catch (org.openqa.selenium.WebDriverException ignored) { }
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("add-task"))).click();
    }
    String title() { return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("task-title"))).getText(); }
    void complete() { wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("complete-task"))).click(); }
    String status() { return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("task-status"))).getText(); }
    void delete() { wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("delete-task"))).click(); }
    boolean empty() { return wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.accessibilityId("task-title"))); }
    String error() { return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("validation-error"))).getText(); }
}
