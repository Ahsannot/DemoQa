package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TextBoxPage extends BasePage{

    public TextBoxPage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//input[@id='userName']")
    WebElement inputUserName;

    @FindBy(xpath = "//input[@id='userEmail']")
    WebElement inputUserEmail;

    @FindBy(xpath = "//textarea[@id='currentAddress']")
    WebElement inputCurrentAddress;

    @FindBy(xpath = "//textarea[@id='permanentAddress']")
    WebElement inputPermanentAddress;

    @FindBy(xpath = "//button[@id='submit']")
    WebElement btnSubmit;

    // *************** OUTPUT LOCATORS ****************

    @FindBy(id = "name")
    WebElement outputName;

    @FindBy(id = "email")
    WebElement outputEmail;

    @FindBy(id = "currentAddress")
    WebElement outputCurrentAddress;

    @FindBy(id = "permanentAddress")
    WebElement outputPermanentAddress;


    //   ************************ ACTION METHODS ************************

    public void enterUserName(String userName){
        inputUserName.sendKeys(userName);
    }

    public void enterUserEmail(String userEmail){
        inputUserEmail.sendKeys(userEmail);
    }

    public void enterCurrentAddress(String currentAddress){
        inputCurrentAddress.sendKeys(currentAddress);
    }

    public void enterPermanentAddress(String permanentAddress){
        inputPermanentAddress.sendKeys(permanentAddress);
    }

    public void clickSubmitBtn(){
        btnSubmit.click();
    }

    // *************** OUTPUT METHODS ****************

    public String getNameOutput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(outputName)).getText();
    }

    public String getEmailOutput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(outputEmail)).getText();
    }

    public String getCurrentAddressOutput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(outputCurrentAddress)).getText();
    }

    public String getPermanentAddressOutput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(outputPermanentAddress)).getText();
    }

}
