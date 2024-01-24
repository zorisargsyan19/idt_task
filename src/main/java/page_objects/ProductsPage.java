package page_objects;

import common_sections.ProductsSection;
import helpers.WaitHelpers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage {

    @FindBy(id = "search_product")
    private WebElement searchField;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    public ProductsSection productsSection;

    public ProductsPage(WebDriver driver) {
        super(driver, "/products");
        productsSection = new ProductsSection(getDriver());
    }

    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), searchField);
    }

    public void searchProduct(String name) {
        searchField.sendKeys(name);
        searchButton.click();
        WaitHelpers.waitUntilPageLoads(getDriver());
        productsSection.waitProductsSectionTitleToBe("SEARCHED PRODUCTS");
    }

}
