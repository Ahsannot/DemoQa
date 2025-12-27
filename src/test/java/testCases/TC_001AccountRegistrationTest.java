package testCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageObjects.BooksPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.RegisterPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC_001AccountRegistrationTest extends BaseClass {

    @Test
    public void accountRegistration() throws InterruptedException {

        logger.info("===== Starting Account Registration Test =====");

        HomePage homePage = new HomePage(driver);

        validatePageMessageHard(
                homePage.getConfirmationMessage(),
                "Book Store Application",
                "HomePage"
        );

        homePage.clickBookStoreApplicationLink();

        BooksPage booksPage = new BooksPage(driver);
        validatePageMessageHard(
                booksPage.getConfirmationMessage(),
                "Book Store",
                "BooksPage"
        );

        booksPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        validatePageMessageHard(
                loginPage.getConfirmationMessage(),
                "Login in Book Store",
                "LoginPage"
        );

        loginPage.clickNewUserButton();

        RegisterPage registerPage = new RegisterPage(driver);
        validatePageMessageHard(
                registerPage.getConfirmationMessage(),
                "Register to Book Store",
                "RegisterPage"
        );

        registerPage.enterFirstName(randomString().toUpperCase());
        registerPage.enterLastName(randomString().toUpperCase());
        registerPage.enterUserName(randomString().toUpperCase());
        registerPage.enterPassword(randomAlphaNumeric() + "Pass@123");
        registerPage.clickRegisterButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        validatePageMessageHard(
                alert.getText(),
                "User Register Successfully.",
                "Registration Alert"
        );

        alert.accept();
        logger.info("Registration completed successfully");
    }
}
