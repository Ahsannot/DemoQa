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
import utilities.ExcelUtils;
import utilities.RegisterDataProvider;

import java.time.Duration;

public class TC_002AccountRegistrationTestDDTxlsx extends BaseClass {

    String excelPath = ConfigReader.getProperty("excel_User_Register_Path");
    String sheetName = ConfigReader.getProperty("register_sheet_name");

    @Test(dataProvider = "RegisterData", dataProviderClass = RegisterDataProvider.class)
    public void accountRegistration(String fname,
                                    String lname,
                                    String uname,
                                    String password,
                                    String expectedResult,
                                    int rowIndex) throws Exception {

        logger.info("===== Starting Registration DDT Test for: " + uname + " =====");

        ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
        String actualResult = "Fail";

        // Create SoftAssert object
        SoftAssert softAssert = new SoftAssert();

        try {
            // Home Page
            HomePage homePage = new HomePage(driver);

            validatePageMessageSoft(homePage.getConfirmationMessage(),
                    "Book Store Application", "HomePage", softAssert);
            homePage.clickBookStoreApplicationLink();

            // Books Page
            BooksPage booksPage = new BooksPage(driver);

            validatePageMessageSoft(booksPage.getConfirmationMessage(),
                    "Book Store", "BooksPage", softAssert);
            booksPage.clickLoginLink();

            // Login Page
            LoginPage loginPage = new LoginPage(driver);

            validatePageMessageSoft(loginPage.getConfirmationMessage(),
                    "Login in Book Store", "LoginPage", softAssert);
            loginPage.clickNewUserButton();

            // Register Page
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.enterFirstName(fname);
            registerPage.enterLastName(lname);
            registerPage.enterUserName(uname);
            registerPage.enterPassword(password);
            registerPage.clickRegisterButton();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();

            // Alert validation using SoftAssert
            validatePageMessageSoft(alertText, "User Register Successfully.", "Registration Alert", softAssert);

            if (alertText.contains("User Register Successfully.")) {
                actualResult = "Pass";
            }

            alert.accept();

            // Excel update
            excel.setCellData(actualResult, rowIndex, 5);
            if (actualResult.equalsIgnoreCase(expectedResult)) {
                excel.setCellData("Test Passed", rowIndex, 6);
            } else {
                excel.setCellData("Test Failed", rowIndex, 6);
            }


            // Soft assert for expected vs actual result
            softAssert.assertEquals(actualResult, expectedResult,
                    "Row " + (rowIndex + 1) + " - User: " + uname + " - Expected vs Actual mismatch");

        } catch (Exception e) {
            logger.error("Exception for user: " + uname + " | " + e.getMessage());
            excel.setCellData("Fail", rowIndex, 5);
            excel.setCellData("Test Failed", rowIndex, 6);

            softAssert.fail("Exception occurred: " + e.getMessage());
        } finally {
            excel.closeWorkbook();
            driver.navigate().to(ConfigReader.getProperty("baseURL"));
        }

        // Trigger all SoftAssert validations
        softAssert.assertAll();

        logger.info("===== Finished Registration DDT Test for: " + uname + " =====");
    }
}
