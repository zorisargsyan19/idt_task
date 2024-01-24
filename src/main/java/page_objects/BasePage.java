package page_objects;

import helpers.UiCommonActions;
import helpers.WaitHelpers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.asserts.SoftAssert;

public abstract class BasePage extends LoadableComponent implements UiCommonActions {

//    Elements could be moved separate Header class and use Header field here
    @FindBy(css = "ul.navbar-nav a[href='/']")
    protected WebElement homeMenu;

    @FindBy(css = "ul.navbar-nav a[href='/login']")
    protected WebElement signUpAndLoginMenu;

    @FindBy(css = "a[href$='products']")
    protected WebElement productsMenu;

    @FindBy(css = "a[href$='logout']")
    protected WebElement headerLogoutButton;

    @FindBy(css = "a[href$='view_cart']")
    protected WebElement headerCartButton;

    @FindBy(xpath = "//a[text()=' Logged in as ']")
    protected WebElement loggedInUsername;

    @FindBy(css = "a[href$='delete_account']")
    protected WebElement deleteAccountButton;

    @FindBy(xpath = "//h2[text()='Subscription']")
    protected WebElement subscriptionOptionTitle;

    @FindBy(id = "scrollUp")
    private WebElement scrollUpButton;

    private WebDriver driver;

//    Hardcoding the URL here for simplicity as in the example it is constant
    private final String SERVER_URL = "https://automationexercise.com";
    private final String pagePath;

    public BasePage(WebDriver driver, String pagePath) {
        this.pagePath = pagePath;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(getExpectedUrl());
        waitUntilPageLoads();
    }

    @Override
    protected void isLoaded() {
        assert isOnPage();
    }

    public boolean isOnPage() {
        return driver.getCurrentUrl().equals(getExpectedUrl());
    }

    public String getExpectedUrl() {
        return String.format("%s%s", SERVER_URL, pagePath);
    }

    public String getServerUrl() {
        return SERVER_URL;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public LoginPage clickSignupAndLoginMenu() {
        signUpAndLoginMenu.click();
        return getLoginPage();
    }



    public void checkUserIsLoggedIn(SoftAssert softAssert, String username) {
        if (isElementDisplayed(loggedInUsername)) {
            softAssert.assertEquals(loggedInUsername.getText(), String.format("Logged in as %s", username),
                    "Logged user username is incorrect");
        } else {
            softAssert.fail("Logged in as username text is missing from the page header");
        }
    }

    public SuccessPage clickDeleteAccountButton() {
        deleteAccountButton.click();
        SuccessPage deleteSuccessPage = new SuccessPage(getDriver(), "/delete_account");
        deleteSuccessPage.waitUntilPageLoads();
        return deleteSuccessPage;
    }

    public ProductsPage clickProductsMenu() {
        productsMenu.click();
        ProductsPage productsPage = new ProductsPage(getDriver());
        productsPage.waitUntilPageLoads();
        return productsPage;
    }

    public CartPage clickCartMenu() {
        scrollUp();
        WaitHelpers.waitUntilElementIsVisible(getDriver(), headerCartButton, 2);
        headerCartButton.click();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.waitUntilPageLoads();
        return cartPage;
    }

    public LoginPage logout() {
        headerLogoutButton.click();
        return getLoginPage();
    }

    public boolean isSubscriptionDisplayed() {
        return isElementDisplayed(subscriptionOptionTitle);
    }

    public void clickScrollUpButton() {
        WaitHelpers.waitUntilElementIsClickable(getDriver(), scrollUpButton, 2);
        scrollUpButton.click();
    }

    private LoginPage getLoginPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.waitUntilPageLoads();
        return loginPage;
    }

    public void switchToDefaultTad() {
        WaitHelpers.waitForTabsCountToBe(getDriver(), 2, 5);
        String currentHandle = driver.getWindowHandle();
        driver.switchTo().window(currentHandle);
        refreshPage();
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
        WaitHelpers.waitUntilPageLoads(getDriver());
    }

    abstract void waitUntilPageLoads();
}
