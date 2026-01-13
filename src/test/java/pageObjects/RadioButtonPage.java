package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RadioButtonPage extends BasePage{

    public RadioButtonPage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//label[normalize-space()='Yes']")
    WebElement radioYes;

    @FindBy(xpath = "//label[normalize-space()='Impressive']")
    WebElement radioImpressive;

    @FindBy(xpath = "//label[normalize-space()='No']")
    WebElement radioNo;


    //   ************************ ACTION METHODS ************************

    public void clickradioYes(){
        radioYes.click();
    }

    public void clickradioImpressive(){
        radioImpressive.click();
    }

    public void clickradioNo(){
        radioNo.click();
    }

}
