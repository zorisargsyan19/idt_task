import helpers.CommonHelpers;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page_objects.*;

import java.util.Properties;

import static helpers.CommonHelpers.getRandomEmail;

public class AutomationExerciseTest extends BaseTest {

    private HomePage homePage;

//    As there are Advertisements in the page, and they are disturbed run process, so I have added Adblocker Plus extension
//    to block them. But The extension opens its own page in a new tab, so I am switching to default tab.
//    It was a quick solution, maybe there is another good solution
    @BeforeClass
    public void closeAddBlockerTab() {
        homePage = new HomePage(driver);
        homePage.get();
        homePage.switchToDefaultTad();
        homePage.refreshPage();
    }

    @BeforeMethod
    public void openHomePage() {
        homePage.get();
    }

//    TODO hardcoded all test data and error messages in test class to save time, it could be better to keep in constant classes of files

    @Test(priority = 1)
    public void testCase1() {
        LoginPage loginPage = homePage.clickSignupAndLoginMenu();
        Assert.assertEquals(loginPage.getSignupFormTitle(), "New User Signup!", "Signup form title is incorrect on Login page");
        String emailAddress = getRandomEmail();
        String username = "John Smith";
        SignupPage signupPage = loginPage.signup(username, emailAddress);
        Assert.assertEquals(signupPage.getAccountInfoSectionTitle(), "ENTER ACCOUNT INFORMATION",
                "Account Information is incorrect on Signup page");
        signupPage.selectTitle("Mr");
        String password = CommonHelpers.getRandomAlphanumericString(6);
        signupPage.setPassword(password);
        signupPage.setBirthDate("19-03-1993");
        signupPage.selectNewsletterCheckbox();
        signupPage.selectOffersCheckbox();
        signupPage.setFirstName("John");
        signupPage.setLastName("Smith");
        signupPage.setCompany("IDT");
        signupPage.setAddress("11 Southgate Blvd C25");
        signupPage.setAddress2("D9617");
        signupPage.selectCountry("United States");
        signupPage.setState("Delaware");
        signupPage.setCity("New Castle");
        signupPage.setZipcode("19720");
        signupPage.setMobileNumber("+13024148567");
        SuccessPage successPage = signupPage.submitForm();
        Assert.assertEquals(successPage.getPageTitle(), "ACCOUNT CREATED!", "Account Created page title isn't correct");
        successPage.clickContinueButton();
        SoftAssert softAssert = new SoftAssert();
        homePage.checkUserIsLoggedIn(softAssert, username);
        softAssert.assertAll();
        successPage = homePage.clickDeleteAccountButton();
        Assert.assertEquals(successPage.getPageTitle(), "ACCOUNT DELETED!", "Account Deleted page title isn't correct");
        homePage = successPage.clickContinueButton();
        Assert.assertTrue(homePage.isOnPage(), "Continue button doesn't take Home page from Account Deleted page");
    }

//    TODO needs to add after method for login/signup tests to check if still is logged in then logout from the page to not brake other methods

    @Test(priority = 0)
    public void testCase9() {
        ProductsPage productsPage = homePage.clickProductsMenu();
        Assert.assertTrue(productsPage.isOnPage(), "Header Products menu doesn't take to Products page");
        String productName = "Men Tshirt";
        productsPage.searchProduct(productName);
        SoftAssert softAssert = new SoftAssert();
        productsPage.productsSection.checkProductDetails(softAssert, 0, productName);
        softAssert.assertAll();
    }

    @Test(priority = 2)
    private void testCase16() {
        String emailAddress = getRandomEmail();
        String password = CommonHelpers.getRandomAlphanumericString(6);
        Properties accountDetails = CommonHelpers.readProperties("properties/account_details.properties");
        createAccount(emailAddress, password, accountDetails);
        LoginPage loginPage = homePage.logout();
        homePage = loginPage.login(emailAddress, password);
        checkUserIsLoggedIn(accountDetails.getProperty("username")).assertAll();
        homePage.productsSection.addProductToCart("Blue Top");
        homePage.productsSection.addProductToCart("Lace Top For Women");
        CartPage cartPage = homePage.clickCartMenu();
        Assert.assertTrue(cartPage.isOnPage(), "Header Cart button doesn't direct to Chart page");
        CheckoutPage checkoutPage = cartPage.clickProceedCheckoutButton();
        SoftAssert softAssert = new SoftAssert();
        String addressDetailsName = String.format("%s. %s", accountDetails.getProperty("title"), accountDetails.getProperty("username"));
        String addressDetailsCompany = accountDetails.getProperty("company");
        String addressDetailsAddress1 = accountDetails.getProperty("address");
        String addressDetailsAddress2 = accountDetails.getProperty("address2");
        String addressDetailsCityDetails = String.format("%s %s %s", accountDetails.getProperty("city"),
                accountDetails.getProperty("state"), accountDetails.getProperty("zipcode"));
        String addressDetailsCountry = accountDetails.getProperty("country");
        String addressDetailsPhone = accountDetails.getProperty("mobile_number");
        checkoutPage.checkDeliveryAddressDetails(softAssert, addressDetailsName, addressDetailsCompany, addressDetailsAddress1,
                addressDetailsAddress2, addressDetailsCityDetails, addressDetailsCountry, addressDetailsPhone);
        checkoutPage.checkBillingAddressDetails(softAssert, addressDetailsName, addressDetailsCompany, addressDetailsAddress1,
                addressDetailsAddress2, addressDetailsCityDetails, addressDetailsCountry, addressDetailsPhone);
        softAssert.assertAll();
//        TODO couldn't managed to complete the test due to lack of time
    }

    @Test(priority = 0)
    private void testCase25() {
        homePage.scrollDown();
        Assert.assertTrue(homePage.isSubscriptionDisplayed(), "SUBSCRIPTION isn't displayed at the Footer on Home page");
        homePage.clickScrollUpButton();
        SoftAssert softAssert = new SoftAssert();
        homePage.checkTopCarouselSubtitle(softAssert);
        softAssert.assertAll();
    }

    @AfterClass
    public void quiteDriver() {
        driver.quit();
    }

    private void createAccount(String email, String password, Properties accountDetailsProperties) {
        LoginPage loginPage = homePage.clickSignupAndLoginMenu();
        String username = accountDetailsProperties.getProperty("username");
        SignupPage signupPage = loginPage.signup(username, email);
        Assert.assertTrue(signupPage.isOnPage(), "Signup button doesn't direct from Login page to Signup");
        signupPage.selectTitle(accountDetailsProperties.getProperty("title"));
        signupPage.setPassword(password);
        signupPage.setBirthDate(accountDetailsProperties.getProperty("birth_date"));
        signupPage.selectNewsletterCheckbox();
        signupPage.selectOffersCheckbox();
        signupPage.setFirstName(accountDetailsProperties.getProperty("first_name"));
        signupPage.setLastName(accountDetailsProperties.getProperty("last_name"));
        signupPage.setCompany(accountDetailsProperties.getProperty("company"));
        signupPage.setAddress(accountDetailsProperties.getProperty("address"));
        signupPage.setAddress2(accountDetailsProperties.getProperty("address2"));
        signupPage.selectCountry(accountDetailsProperties.getProperty("country"));
        signupPage.setState(accountDetailsProperties.getProperty("state"));
        signupPage.setCity(accountDetailsProperties.getProperty("city"));
        signupPage.setZipcode(accountDetailsProperties.getProperty("zipcode"));
        signupPage.setMobileNumber(accountDetailsProperties.getProperty("mobile_number"));
        SuccessPage successPage = signupPage.submitForm();
        Assert.assertEquals(successPage.getPageTitle(), "ACCOUNT CREATED!", "Account isn't corrected");
        homePage = successPage.clickContinueButton();
        checkUserIsLoggedIn(username).assertAll();
    }

    private SoftAssert checkUserIsLoggedIn(String username) {
        SoftAssert softAssert = new SoftAssert();
        homePage.checkUserIsLoggedIn(softAssert, username);
        return softAssert;
    }
}
