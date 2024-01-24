package page_objects;

import helpers.CommonHelpers;
import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;

public class SignupPage extends BasePage {

    @FindBy(css = "div.login-form>h2>b")
    private WebElement accountInfoSectionTitle;

    private final String TITLE_SELECTOR = "input[value='%s']";

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "days")
    private WebElement birthDay;

    @FindBy(id = "months")
    private WebElement birthMonth;

    @FindBy(id = "years")
    private WebElement birthYear;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement offersCheckbox;

    @FindBy(id = "first_name")
    private WebElement firstNameField;

    @FindBy(id = "last_name")
    private WebElement lastNameField;

    @FindBy(id = "company")
    private WebElement companyField;

    @FindBy(id = "address1")
    private WebElement addressField;

    @FindBy(id = "address2")
    private WebElement address2Field;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "zipcode")
    private WebElement zipcodeField;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberField;

    @FindBy(css = "form[action$='signup'] button")
    private WebElement createAccountButton;

    public SignupPage(WebDriver driver) {
        super(driver, "/signup");
    }

//    Assuming the last loaded element is name field here
    @Override
    public void waitUntilPageLoads() {
        WaitHelpers.waitUntilPageLoads(getDriver());
        WaitHelpers.waitUntilElementIsVisible(getDriver(), nameField);
    }

    public String getAccountInfoSectionTitle() {
        return accountInfoSectionTitle.getText();
    }

    public void selectTitle(String title) {
        By titleLocator = By.cssSelector(String.format(TITLE_SELECTOR, title));
        getDriver().findElement(titleLocator).click();
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void selectBirthDay(int day) {
        if (day < 1 || day > 31) {
//            We could define our exceptions and use them, for simplicity I use Error here
            throw new Error("Incorrect day of month is specified");
        }
        Select select = new Select(birthDay);
        select.selectByValue(Integer.toString(day));
    }

    public void selectBirthMonth(int month) {
        if (month < 1 || month > 12) {
            throw new Error("Incorrect month value is specified");
        }
        Select select = new Select(birthMonth);
        select.selectByValue(Integer.toString(month));
    }

    public void selectBirthYear(int year) {
        if (year < 1900 || year > 2021) {
            throw new Error("Incorrect year value is specified");
        }
        Select select = new Select(birthYear);
        select.selectByValue(Integer.toString(year));
    }

    public void setBirthDate(String birthDate) {
        LocalDate date = CommonHelpers.convertToDate(birthDate);
        selectBirthDay(date.getDayOfMonth());
        selectBirthMonth(date.getMonthValue());
        selectBirthYear(date.getYear());
    }

    public void selectNewsletterCheckbox() {
        newsletterCheckbox.click();
    }

    public void selectOffersCheckbox() {
        offersCheckbox.click();
    }

    public void setFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void setCompany(String company) {
        companyField.sendKeys(company);
    }

    public void setAddress(String address) {
        addressField.sendKeys(address);
    }

    public void setAddress2(String address) {
        address2Field.sendKeys(address);
    }

    public void selectCountry(String country) {
        Select select = new Select(countryDropdown);
        select.selectByValue(country);
    }

    public void setState(String state) {
        stateField.sendKeys(state);
    }

    public void setCity(String city) {
        cityField.sendKeys(city);
    }

    public void setZipcode(String zipcode) {
        zipcodeField.sendKeys(zipcode);
    }

    public void setMobileNumber(String mobileNumber) {
        mobileNumberField.sendKeys(mobileNumber);
    }

    public void clickCreateAccountButton() {
        createAccountButton.click();
    }

    public SuccessPage submitForm() {
        clickCreateAccountButton();
        SuccessPage successPage = new SuccessPage(getDriver(), "/account_created");
        successPage.waitUntilPageLoads();
        return successPage;
    }
}
