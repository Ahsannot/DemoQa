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
import org.testng.asserts.SoftAssert;
import utilities.ConfigReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseClass {

    protected WebDriver driver;
    protected ChromeOptions chromeOptions;
    protected Logger logger;

    @BeforeClass
    public void setup() throws IOException {
        logger = LogManager.getLogger(this.getClass());
        logger.info("Starting test...");

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("baseURL"));
        logger.info("Navigated to: " + ConfigReader.getProperty("baseURL"));
    }

    // ================= HARD ASSERT (USE ONLY IN TC_001) =================
    public void validatePageMessageHard(String actual, String expected, String pageName) {
        Assert.assertEquals(actual, expected, pageName + " validation failed");
        logger.info(pageName + " validation passed");
    }

    // ================= SOFT / DDT-SAFE VALIDATION (USE IN TC_002) =================
    public boolean validatePageMessageSoft(String actual, String expected, String pageName, SoftAssert softAssert) {
        if (actual.equals(expected)) {
            logger.info(pageName + " validation passed");
            return true;
        } else {
            logger.warn(pageName + " validation failed | Expected: "
                    + expected + " | Actual: " + actual);
            if (softAssert != null) {
                softAssert.assertEquals(actual, expected, pageName + " validation failed");
            }
            return false;
        }
    }

    // ================= RANDOM DATA HELPERS =================
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    // Attach driver for reporting
    @BeforeMethod(alwaysRun = true)
    public void attachDriverToTestResult(Method method, ITestResult result) {
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
