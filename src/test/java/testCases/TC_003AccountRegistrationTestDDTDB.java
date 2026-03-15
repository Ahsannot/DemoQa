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
        String alertText = "No alert displayed";
        SoftAssert softAssert = new SoftAssert();

        try {
            // -------------------- Home Page --------------------
            HomePage homePage = new HomePage(getDriver());
            validatePageMessageSoft(homePage.getConfirmationMessage(),
                    "Book Store Application", "HomePage", softAssert);
            homePage.clickBookStoreApplicationLink();

            // -------------------- Books Page --------------------
            BooksPage booksPage = new BooksPage(getDriver());
            validatePageMessageSoft(booksPage.getConfirmationMessage(),
                    "Book Store", "BooksPage", softAssert);
            booksPage.clickLoginLink();

            // -------------------- Login Page --------------------
            LoginPage loginPage = new LoginPage(getDriver());
            validatePageMessageSoft(loginPage.getConfirmationMessage(),
                    "Login in Book Store", "LoginPage", softAssert);
            loginPage.clickNewUserButton();

            // -------------------- Register Page --------------------
            RegisterPage registerPage = new RegisterPage(getDriver());
            registerPage.enterFirstName(fname);
            registerPage.enterLastName(lname);
            registerPage.enterUserName(uname); // <-- username
            registerPage.enterPassword(password);
            registerPage.clickRegisterButton();

            // -------------------- Handle alert safely --------------------
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alertText = alert.getText();

            validatePageMessageSoft(alertText,
                    expectedResult, "Registration Alert", softAssert);

            if (alertText.contains(expectedResult)) {
                actualResult = "Pass";
            }

            alert.accept();

            // -------------------- Soft assertion check --------------------
            softAssert.assertAll(); // fail test if needed

        } finally {
            // -------------------- Write-back to DB using username --------------------
            // Determine test status
            String testStatus;
            if (actualResult.equals("Pass")) {
                testStatus = "Test Passed";
            } else {
                testStatus = "Test Failed";
            }

            // Update the result in the database
            DBResultUpdater.updateResult(uname, actualResult, testStatus);

            // Determine retry flag
            String retryFlag;
            if (actualResult.equals("Pass")) {
                retryFlag = "N"; // No retry needed
            } else {
                retryFlag = "Y"; // Retry next time
            }

            // Update the retry flag in the database
            DBResultUpdater.updateRetryFlag(uname, retryFlag);


            // Navigate back to base URL for next iteration
            getDriver().navigate().to(ConfigReader.getProperty("baseURL"));

            logger.info("===== Finished Registration DB DDT Test for user: {} | Result: {} =====",
                    uname, actualResult);
        }
    }
}
