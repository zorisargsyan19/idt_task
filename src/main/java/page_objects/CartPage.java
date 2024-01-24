package page_objects;

import helpers.WaitHelpers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(xpath = "//section[@id='cart_items']//li[text()='Shopping Cart']")
    private WebElement pageTitle;

    @FindBy(css = "a.check_out")
    private WebElement proceedCheckoutButton;

    public CartPage(WebDriver driver) {
        super(driver, "/view_cart");
    }

    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), pageTitle);
    }

    public CheckoutPage clickProceedCheckoutButton() {
        proceedCheckoutButton.click();
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.waitUntilPageLoads();
        return checkoutPage;
    }
}
