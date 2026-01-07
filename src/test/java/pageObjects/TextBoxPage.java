package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
}
