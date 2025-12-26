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

public class TC_002AccountRegistrationTestxlsx extends BaseClass {

    @Test
    public void accountRegistration() throws InterruptedException {

        logger.info("===== Starting Account Registration Test =====");

        HomePage homePage = new HomePage(driver);

        //********* Home Page validation *********
        String homeMsg = homePage.getConfirmationMessage();
        logger.info("Confirmation message received: " + homeMsg);
        validatePageMessage(homeMsg, "Book Store Application", "HomePage");

        homePage.clickBookStoreApplicationLink();
        logger.info("Clicked on Book Store Application");

        //********* Books Page validation *********
        BooksPage booksPage = new BooksPage(driver);
        String bookStoreMessage = booksPage.getConfirmationMessage();
        logger.info("Confirmation message received: " + bookStoreMessage);
        validatePageMessage(bookStoreMessage, "Book Store", "BooksPage");

        booksPage.clickLoginLink();
        logger.info("Clicked on Login in Side-Bar");

        //********* Login Page validation *********
        LoginPage loginPage = new LoginPage(driver);
        String loginPageMessage = loginPage.getConfirmationMessage();
        logger.info("Confirmation message received: " + loginPageMessage);
        validatePageMessage(loginPageMessage, "Login in Book Store", "LoginPage");

        loginPage.clickNewUserButton();
        logger.info("Clicked on New User button");

        //********* Register Page validation *********
        RegisterPage registerPage = new RegisterPage(driver);
        String registerPageMessage = registerPage.getConfirmationMessage();
        logger.info("Confirmation message received: " + registerPageMessage);
        validatePageMessage(registerPageMessage, "Register to Book Store", "RegisterPage");

        logger.info("Adding New User Info");

        registerPage.enterFirstName(randomString().toUpperCase()); // Hafiz
        registerPage.enterLastName(randomString().toUpperCase()); // Ahsan
        registerPage.enterUserName(randomString().toUpperCase()); // hafiz_ahsan
        registerPage.enterPassword(randomAlphaNumeric()+"Pass@123"); // Ahsan@1234

        registerPage.clickRegisterButton();

        logger.info("Filled registration form and clicked Register button");

        //********* Alert validation *********
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        validatePageMessage(alertText, "User Register Successfully", "Registration Alert");

        alert.accept();
        logger.info("Registration completed successfully");
    }
}
