package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RadioButtonPage extends BasePage {

    public RadioButtonPage(WebDriver driver) {
        super(driver);
    }

    // ************************ LOCATORS ************************

    // Radio inputs
    @FindBy(id = "yesRadio")
    WebElement yesRadioInput;

    @FindBy(id = "impressiveRadio")
    WebElement impressiveRadioInput;

    @FindBy(id = "noRadio")
    WebElement noRadioInput;

    // Labels (for clicking)
    @FindBy(xpath = "//label[normalize-space()='Yes']")
    WebElement radioYes;

    @FindBy(xpath = "//label[normalize-space()='Impressive']")
    WebElement radioImpressive;

    // Result text
    @FindBy(className = "text-success")
    WebElement selectedText;

    // ************************ ACTION METHODS ************************

    public void clickYes() {
        radioYes.click();
    }

    public void clickImpressive() {
        radioImpressive.click();
    }

    public boolean isYesSelected() {
        return yesRadioInput.isSelected();
    }

    public boolean isImpressiveSelected() {
        return impressiveRadioInput.isSelected();
    }

    public boolean isNoEnabled() {
        return noRadioInput.isEnabled();
    }

    public String getSelectedResultText() {
        return selectedText.getText();
    }
}
