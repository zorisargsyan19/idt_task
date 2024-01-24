package page_objects;

import common_sections.ProductsSection;
import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class HomePage extends BasePage {

    private final By TOP_CAROUSEL_SUBTITLE_LOCATOR = By.cssSelector("#slider-carousel div.item.active h2");
    public ProductsSection productsSection;

    public HomePage(WebDriver driver) {
        super(driver, "/");
        productsSection = new ProductsSection(getDriver());
    }

    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), homeMenu);
        WaitHelpers.waitUntilElementAttributeContains(getDriver(), homeMenu, "style", "orange");
    }

    public void checkTopCarouselSubtitle(SoftAssert softAssert) {
        try {
            WaitHelpers.waitUntilElementIsVisible(getDriver(), TOP_CAROUSEL_SUBTITLE_LOCATOR, 2);
            softAssert.assertEquals(getDriver().findElement(TOP_CAROUSEL_SUBTITLE_LOCATOR).getText(),
                    "Full-Fledged practice website for Automation Engineers",
                    "Top carousel active element subtitle is incorrect");
        } catch (TimeoutException ex) {
            ex.printStackTrace();
            softAssert.fail("Top carousel active element subtitle is missing");
        }
    }


}
