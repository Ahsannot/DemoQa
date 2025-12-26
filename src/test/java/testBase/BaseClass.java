package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;


public class BaseClass {

    public WebDriver driver;
    public ChromeOptions chromeOptions;
    public Logger logger;
    public Properties prop;

    @BeforeClass
    public void setup() throws IOException {
        logger = LogManager.getLogger(this.getClass());
        logger.info("Starting test...");

        try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
            prop = new Properties();
            prop.load(file);
        }

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(prop.getProperty("baseURL"));

        logger.info("Navigated to: " + prop.getProperty("baseURL"));
    }

    // ================= HELPER / UTILITY METHOD FOR PAGE OPENNING VALIDATION =================

    public void validatePageMessage(String actual, String expected, String pageName) {
        Assert.assertEquals(actual, expected, pageName + " validation failed");
        logger.info(pageName + " validation passed");
    }


    // ========== RANDOM DATA HELPERS ==========
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5); // e.g., "Abcde"
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10); // e.g., "1234567890"
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(8); // e.g., "A1B2C3D4"
    }


    // Attach driver to test result attributes before each test method for reporting
    // It runs even if the test belongs to a skipped group
    @BeforeMethod(alwaysRun = true)
    // Method method (Represents the test method about to run)  ITestResult result (Test status, Attributes, Exceptions)
    public void attachDriverToTestResult(Method method, ITestResult result) {
        // Attaches the WebDriver instance to the current test result
        //Stored as a key–value pair:
        //Key: "driver"
        //Value: driver
        result.setAttribute("driver", driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }
}
