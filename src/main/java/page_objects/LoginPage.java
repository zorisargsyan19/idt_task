package page_objects;

import helpers.WaitHelpers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = "div.signup-form>h2")
    private WebElement signupFormTitle;

    @FindBy(css = "div.signup-form input[name='name']")
    private WebElement signupNameField;

    @FindBy(css = "div.signup-form input[name='email']")
    private WebElement signupEmailAddressField;

    @FindBy(css = "div.signup-form button")
    private WebElement signupButton;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmailField;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement loginPasswordField;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver, "/login");
    }

    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilElementIsVisible(getDriver(), signupNameField);
    }

    public String getSignupFormTitle() {
        return signupFormTitle.getText();
    }

    public void setName(String name) {
        signupNameField.sendKeys(name);
    }

    public void setSignupEmailAddress(String emailAddress) {
        signupEmailAddressField.sendKeys(emailAddress);
    }

    public void clickSignupButton() {
        signupButton.click();
    }

    public SignupPage signup(String name, String email) {
        setName(name);
        setSignupEmailAddress(email);
        clickSignupButton();
        SignupPage signupPage = new SignupPage(getDriver());
        signupPage.waitUntilPageLoads();
        return signupPage;
    }

    public void setLoginEmail(String email) {
        loginEmailField.sendKeys(email);
    }

    public void setLoginPassword(String password) {
        loginPasswordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public HomePage login(String email, String password) {
        setLoginEmail(email);
        setLoginPassword(password);
        clickLoginButton();
        HomePage homePage = new HomePage(getDriver());
        homePage.waitUntilPageLoads();
        return homePage;
    }
}
