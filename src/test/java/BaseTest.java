import helpers.CommonHelpers;
import helpers.SetupDrivers;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    protected void setupSpec() {
        driver = SetupDrivers.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshotFolder = new File("screenshots");
            if (!screenshotFolder.exists()) {
                boolean isCreated = screenshotFolder.mkdir();
                if (!isCreated) {
                    System.out.println("Couldn't create screenshot folder");
                }
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File(
                        String.format("%s/%s_%s.png", screenshotFolder.getAbsolutePath(), result.getMethod().getMethodName(), CommonHelpers.getCurrentDateTime())));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
