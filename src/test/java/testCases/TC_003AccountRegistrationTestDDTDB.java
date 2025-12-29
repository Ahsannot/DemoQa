package testCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.BooksPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.RegisterPage;

import testBase.BaseClass;
import utilities.ConfigReader;
import utilities.RegisterDBDataProvider;
import utilities.DBResultUpdater;

import java.time.Duration;

public class TC_003AccountRegistrationTestDDTDB extends BaseClass {

    @Test(dataProvider = "RegisterDataDB",
            dataProviderClass = RegisterDBDataProvider.class)
    public void accountRegistration(String fname,
                                    String lname,
                                    String uname,
                                    String password,
                                    String expectedResult) throws InterruptedException {

        logger.info("===== Starting Registration DB DDT Test for user: {} =====", uname);

        String actualResult = "Fail"; // default
        boolean testFailed = false; // track for TestNG
        SoftAssert softAssert = new SoftAssert();
        String alertText = "";

        try {
            // -------------------- Home Page --------------------
            HomePage homePage = new HomePage(driver);
            validatePageMessageSoft(homePage.getConfirmationMessage(),
                    "Book Store Application", "HomePage", softAssert);
            homePage.clickBookStoreApplicationLink();

            // -------------------- Books Page --------------------
            BooksPage booksPage = new BooksPage(driver);
            validatePageMessageSoft(booksPage.getConfirmationMessage(),
                    "Book Store", "BooksPage", softAssert);
            booksPage.clickLoginLink();

            // -------------------- Login Page --------------------
            LoginPage loginPage = new LoginPage(driver);
            validatePageMessageSoft(loginPage.getConfirmationMessage(),
                    "Login in Book Store", "LoginPage", softAssert);
            loginPage.clickNewUserButton();

            // -------------------- Register Page --------------------
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.enterFirstName(fname);
            registerPage.enterLastName(lname);
            registerPage.enterUserName(uname);
            registerPage.enterPassword(password);
            registerPage.clickRegisterButton();

            // -------------------- Handle alert safely --------------------
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                alertText = alert.getText();

                validatePageMessageSoft(alertText,
                        "User Register Successfully.", "Registration Alert", softAssert);

                if (alertText.contains("User Register Successfully.")) {
                    actualResult = "Pass";
                } else {
                    actualResult = "Fail";
                    testFailed = true;
                }

                alert.accept();
            } catch (Exception e) {
                logger.error("Alert did not appear for user {}: {}", uname, e.getMessage());
                actualResult = "Fail";
                testFailed = true;
                alertText = "No alert displayed";
            }

            // -------------------- Soft assertions --------------------
            try {
                softAssert.assertAll(); // throws AssertionError if any soft assert failed
            } catch (AssertionError ae) {
                actualResult = "Fail";
                testFailed = true;
                logger.error("Soft assertions failed for user {}: {}", uname, ae.getMessage());
            }

        } finally {
            // -------------------- Write-back to DB --------------------
            String testStatus;
            if (actualResult.equals("Pass")) {
                testStatus = "Test Passed";
                DBResultUpdater.updateRetryFlag(uname, "N");
            } else {
                testStatus = "Test Failed";
                DBResultUpdater.updateRetryFlag(uname, "Y");
            }

            DBResultUpdater.updateResult(uname, actualResult, testStatus);

            // Navigate back to base URL for next iteration
            driver.navigate().to(ConfigReader.getProperty("baseURL"));

            logger.info("===== Finished Registration DB DDT Test for user: {} | Result: {} =====", uname, actualResult);
        }

        // -------------------- Propagate failure to TestNG --------------------
        if (testFailed) {
            throw new AssertionError("Test failed for user: " + uname + " | Alert: " + alertText);
        }
    }
}
