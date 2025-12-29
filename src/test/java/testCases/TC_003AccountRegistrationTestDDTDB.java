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
                                    String expectedResult) {

        logger.info("===== Starting Registration DB DDT Test for user: {} =====", uname);

        String actualResult = "Fail"; // default as Fail
        SoftAssert softAssert = new SoftAssert();

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

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();

            validatePageMessageSoft(alertText,
                    "User Register Successfully.", "Registration Alert", softAssert);

            if (alertText.contains("User Register Successfully.")) {
                actualResult = "Pass";
            }

            alert.accept();

        } catch (Exception e) {
            logger.error("Exception occurred for user: {} | {}", uname, e.getMessage());
            softAssert.fail("Exception occurred: " + e.getMessage());
        }

        // -------------------- Final Assertion --------------------
        try {
            softAssert.assertAll(); // throws AssertionError if any soft assert failed
            actualResult = "Pass";   // mark as Pass if all soft assertions passed
        } catch (AssertionError ae) {
            actualResult = "Fail";   // mark as Fail if any assertion failed
            logger.error("Assertions failed for user {}: {}", uname, ae.getMessage());
        }

        // -------------------- Write-back to DB --------------------
        try {
            // Mark testStatus strictly based on actualResult
            String testStatus = actualResult.equals("Pass") ? "Test Passed" : "Test Failed";

            // Update DB with actual result and test status
            DBResultUpdater.updateResult(uname, actualResult, testStatus);

            // retry_flag = 'Y' only if test failed, otherwise 'N'
            DBResultUpdater.updateRetryFlag(uname, actualResult.equals("Pass") ? "N" : "Y");

        } catch (Exception ex) {
            logger.error("DB write-back failed for user: {} | {}", uname, ex.getMessage());
        }

        // Navigate back to base URL
        driver.navigate().to(ConfigReader.getProperty("baseURL"));

        logger.info("===== Finished Registration DB DDT Test for user: {} =====", uname);
    }
}
