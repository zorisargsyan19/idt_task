package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

public interface UiCommonActions {

    WebDriver getDriver();

    default void validateChildText(SoftAssert softAssert, WebElement parent, By childSelector, String expectedText, String elementName) {
        if (!WaitHelpers.isElementChildVisible(getDriver(), parent, childSelector, 1)) {
            softAssert.fail(String.format("%s is missing", elementName));
        } else {
            softAssert.assertEquals(parent.findElement(childSelector).getText(), expectedText, String.format("%s isn't correct", elementName));
        }
    }

    default void scrollDown() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight));");
    }

    default void scrollUp() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, -Math.max(document.documentElement.scrollHeight));");
    }

    default void scrollTillElement(WebElement element) {
        String scrollScript = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor) getDriver()).executeScript(scrollScript, element);
    }

    default void hoverOnElement(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).build().perform();
    }

    default boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
