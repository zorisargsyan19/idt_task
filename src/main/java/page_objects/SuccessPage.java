package page_objects;

import helpers.WaitHelpers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuccessPage extends BasePage {

    @FindBy(css = "section#form h2 b")
    private WebElement pageTitle;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement continueButton;

    public SuccessPage(WebDriver driver, String pagePath) {
        super(driver, pagePath);
    }

    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), pageTitle);
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public HomePage clickContinueButton() {
        continueButton.click();
        HomePage homePage = new HomePage(getDriver());
        homePage.waitUntilPageLoads();
        return homePage;
    }
}
