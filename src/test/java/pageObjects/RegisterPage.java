package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage{

    public RegisterPage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//h4[normalize-space()='Register to Book Store']")
    WebElement text_RegisterPage;

    @FindBy(xpath = "//input[@id='firstname']")
    WebElement textFirstName;

    @FindBy(xpath = "//input[@id='lastname']")
    WebElement textLastName;

    @FindBy(xpath = "//input[@id='userName']")
    WebElement textUserName;

    @FindBy(xpath = "//input[@id='password']")
    WebElement textPassword;

    @FindBy(xpath = "//button[@id='register']")
    WebElement btnRegister;



    //   ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOf(text_RegisterPage));
            return confirmation.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    public void enterFirstName(String firstName) {
        textFirstName.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        textLastName.sendKeys(lastName);
    }

    public void enterUserName(String userName) {
        textUserName.sendKeys(userName);
    }

    public void enterPassword(String password) {
        textPassword.sendKeys(password);
    }

    public void clickRegisterButton() throws InterruptedException {
        Thread.sleep(10000);
        jsClick(btnRegister);
    }
}

