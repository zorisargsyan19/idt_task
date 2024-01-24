package page_objects;

import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(id = "address_delivery")
    private WebElement deliveryAddressDetailsSection;

    @FindBy(id = "address_invoice")
    private WebElement billingAddressDetailsSection;

    private final By ORDERS_LOCATOR = By.cssSelector("#cart_info tbody tr[id^='product']");

    public CheckoutPage(WebDriver driver) {
        super(driver, "/checkout");
    }

    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), deliveryAddressDetailsSection);
    }

    public void checkDeliveryAddressDetails(SoftAssert softAssert, String name, String company, String address1,
                                            String address2, String cityDetails, String country, String phone) {
        checkAddressDetails(softAssert, deliveryAddressDetailsSection, "delivery", name, company, address1,
                address2, cityDetails, country, phone);
    }

    public void checkBillingAddressDetails(SoftAssert softAssert, String name, String company, String address1,
                                            String address2, String cityDetails, String country, String phone) {
        checkAddressDetails(softAssert, billingAddressDetailsSection, "billing", name, company, address1,
                address2, cityDetails, country, phone);
    }

    public List<WebElement> getOrders() {
        return getDriver().findElements(ORDERS_LOCATOR);
    }

    public void checkOrderDetails(SoftAssert softAssert, List<String> products) {
//    TODO couldn't implement doe to lack of time
    }

    private void checkAddressDetails(SoftAssert softAssert, WebElement addressSection, String sectionName, String name, String company,
                                    String address1, String address2, String cityDetails, String country, String phone) {
        By nameLocator = By.cssSelector("li.address_firstname.address_lastname");
        By companyLocator = By.cssSelector("li:nth-child(3)");
        By address1Locator = By.cssSelector("li:nth-child(4)");
        By address2Locator = By.cssSelector("li:nth-child(5)");
        By cityLocator = By.cssSelector("li.address_city");
        By countryLocator = By.cssSelector("li.address_country_name");
        By phoneLocator = By.cssSelector("li.address_phone");
        validateChildText(softAssert, addressSection, nameLocator, name, String.format("%s name", sectionName));
        validateChildText(softAssert, addressSection, companyLocator, company, String.format("%s company", sectionName));
        validateChildText(softAssert, addressSection, address1Locator, address1, String.format("%s address", sectionName));
        validateChildText(softAssert, addressSection, address2Locator, address2, String.format("%s address2", sectionName));
        validateChildText(softAssert, addressSection, cityLocator, cityDetails, String.format("%s city details", sectionName));
        validateChildText(softAssert, addressSection, countryLocator, country, String.format("%s country", sectionName));
        validateChildText(softAssert, addressSection, phoneLocator, phone, String.format("%s phone number", sectionName));
    }
}
