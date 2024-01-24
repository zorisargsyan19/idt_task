package common_sections;

import helpers.CommonHelpers;
import helpers.UiCommonActions;
import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import popups.AddedCartPopup;

import java.util.List;
import java.util.Properties;

public class ProductsSection implements UiCommonActions {

    @FindBy(css = "div.features_items h2.title")
    private WebElement productsSectionTitle;

    private final By PRODUCT_ITEMS_LOCATOR = By.cssSelector("div.features_items div.col-sm-4");
    private final By PRODUCT_NAME_LOCATOR = By.cssSelector("div.productinfo p");
    private final By PRODUCT_PRICE_LOCATOR = By.cssSelector("div.productinfo h2");
    private final By PRODUCT_IMAGE_LOCATOR = By.cssSelector("div.productinfo img");
    private final By PRODUCT_ADD_TO_CART_LOCATOR = By.cssSelector("div.productinfo a");
    private final By PRODUCT_VIEW_BUTTON_LOCATOR = By.cssSelector(" div.choose a");

    private WebDriver driver;

    public ProductsSection(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private List<WebElement> getProducts() {
        return getDriver().findElements(PRODUCT_ITEMS_LOCATOR);
    }

    public int getProductsCount() {
        return getProducts().size();
    }

    private WebElement getProduct(int index) {
        return getProducts().get(index);
    }

    private WebElement getProduct(String name) {
        String productSelectorByName = String.format("//div[@class='features_items']//div[contains(@class, 'productinfo')]//p[text()='%s']/../../..", name);
        return getDriver().findElement(By.xpath(productSelectorByName));
    }

    public void checkProductDetails(SoftAssert softAssert, int index, String productName) {
        Properties properties = CommonHelpers.readProperties("properties/product_Details.properties");
        String productPropertyName = productName.toLowerCase().replaceAll(" ", "_");
        if (getProductsCount() <= index && !isElementDisplayed(getProduct(index))) {
            softAssert.fail(String.format("There aren't %d items in the Products list", index + 1));
        } else {
            WebElement product = getProduct(index);
            String expectedName = properties.getProperty(String.format("%s.name", productPropertyName));
            validateChildText(softAssert, product, PRODUCT_NAME_LOCATOR, expectedName, String.format("%s product name", expectedName));
            String expectedPrice = properties.getProperty(String.format("%s.price", productPropertyName));
            validateChildText(softAssert, product, PRODUCT_PRICE_LOCATOR, expectedPrice, String.format("%s product price", expectedName));
            validateChildText(softAssert, product, PRODUCT_ADD_TO_CART_LOCATOR, "Add to cart", String.format("%s product Add to cart button", expectedName));
            validateChildText(softAssert, product, PRODUCT_VIEW_BUTTON_LOCATOR, "View Product", String.format("%s product View Product button", expectedName));
            String expectedImage = properties.getProperty(String.format("%s.image", productPropertyName));
            if (!WaitHelpers.isElementChildVisible(getDriver(), product, PRODUCT_IMAGE_LOCATOR, 1)) {
                softAssert.fail(String.format("%s product image is missing", expectedName));
            } else {
                softAssert.assertTrue(product.findElement(PRODUCT_IMAGE_LOCATOR).getAttribute("src").endsWith(expectedImage),
                        String.format("%s product image isn't correct", expectedName));
            }
        }
    }

    public void waitProductsSectionTitleToBe(String title) {
        WaitHelpers.waitUntilElementTextToBe(getDriver(), productsSectionTitle, title);
    }

//    It isn't good way to assert outside of tests. As due to lake of time couldn't manage exceptions in page objects
    public void addProductToCart(String name) {
        WebElement product = getProduct(name);
        scrollTillElement(product);
        hoverOnElement(product);
        By overviewAddChartButtonLocator = By.cssSelector("div.overlay-content a.add-to-cart");
        if (WaitHelpers.isElementChildVisible(getDriver(), product, overviewAddChartButtonLocator, 2)) {
            WaitHelpers.waitSeconds(0.3);
            product.findElement(overviewAddChartButtonLocator).click();
            AddedCartPopup addedCartPopup = new AddedCartPopup(getDriver());
            addedCartPopup.waitUntilPopupDisplays();
            addedCartPopup.clickContinueShoppingButton();
        } else {
            throw new AssertionError(String.format("%s Product is missing", name));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
