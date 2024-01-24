package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SetupDrivers {

    public static WebDriver initDriver() {
        WebDriver driver;
        String driverName = System.getProperty("driver");
        if (null == driverName || driverName.equalsIgnoreCase("chrome")) {
            driver = createChromeDriver();
        } else if (driverName.equalsIgnoreCase("firefox")) {
            driver = createFirefoxDriver();
        } else {
            throw new IllegalArgumentException(String.format("%s browser is not supported", driverName));
        }
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createChromeDriver() {
        URL resourceUrl = SetupDrivers.class.getClassLoader().getResource("extensions/Adblock-Plus.crx");
        WebDriverManager.chromedriver().setup();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        return new ChromeDriver(new ChromeOptions()
                .addExtensions(new File(resourceUrl.getPath()))
                .addArguments(
                        "--start-maximized",
                        "--disable-infobars"
//                        "--disable-extensions"
                )
                .setExperimentalOption("useAutomationExtension", false)
                .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
                .setExperimentalOption("prefs", prefs)
        );
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(new FirefoxOptions().addArguments("start-maximized", "disable-infobars", "--disable-extensions"));
    }
}
