package popups;

import helpers.UiCommonActions;
import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddedCartPopup implements UiCommonActions {

    public WebDriver driver;
    private final By WINDOW_LOCATOR = By.cssSelector("#cartModal div.modal-content");

    @FindBy(css = "button.close-modal")
    private WebElement continueShoppingButton;

    public AddedCartPopup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    public void waitUntilPopupDisplays() {
        WaitHelpers.waitUntilElementIsVisible(getDriver(), WINDOW_LOCATOR, 5);
    }

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
        WaitHelpers.waitForElementInvisibility(getDriver(), WINDOW_LOCATOR, 3);
    }
}
