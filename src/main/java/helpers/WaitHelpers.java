package helpers;

import org.awaitility.Awaitility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class WaitHelpers {

    private static final int DEFAULT_TIMEOUT = 30;

    public static void waitUntilPageLoads(WebDriver driver, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitUntilPageLoads(WebDriver driver) {
        waitUntilPageLoads(driver, DEFAULT_TIMEOUT);
    }

    public static void waitUntilElementIsVisible(WebDriver driver, WebElement element) {
        waitUntilElementIsVisible(driver, element, DEFAULT_TIMEOUT);
    }

    public static void waitUntilElementIsVisible(WebDriver driver, WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementIsVisible(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitUntilElementAttributeContains(WebDriver driver, WebElement element, String attribute, String value, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public static void waitUntilElementAttributeContains(WebDriver driver, WebElement element, String attribute, String value) {
        waitUntilElementAttributeContains(driver, element, attribute, value, DEFAULT_TIMEOUT);
    }

    public static void waitUntilElementTextToBe(WebDriver driver, WebElement element, String text, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void waitUntilElementTextToBe(WebDriver driver, WebElement element, String text) {
        waitUntilElementTextToBe(driver, element, text, DEFAULT_TIMEOUT);
    }

    public static void waitForElementInvisibility(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean isElementChildVisible(WebDriver driver, WebElement parent, By child, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, child));
            return true;
        } catch (TimeoutException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void waitUntilElementIsClickable(WebDriver driver, WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForTabsCountToBe(WebDriver driver, int count, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.numberOfWindowsToBe(count));
    }

    public static void waitSeconds(double seconds) {
        long millisecondsToWait = (long) (seconds * 1000);
        long currentTime = System.currentTimeMillis();
        Awaitility.await().atMost(millisecondsToWait, TimeUnit.SECONDS).pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
                currentTime + millisecondsToWait < System.currentTimeMillis());
    }

    private WaitHelpers() {}
}
