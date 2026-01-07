package pageObjects.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.BasePage;

public class ElementsMenuComponent extends BasePage {
    // Constructor: initializes the page with WebDriver instance
    public ElementsMenuComponent(WebDriver driver){
        super(driver);
    }

    // ************************ LOCATORS ************************

    @FindBy(xpath = "//span[normalize-space()='Text Box']")
    WebElement linkTextBox;

    @FindBy(xpath = "//span[normalize-space()='Check Box']")
    WebElement linkCheckBox;

    @FindBy(xpath = "//span[normalize-space()='Radio Button']")
    WebElement linkRadioButton;

    @FindBy(xpath = "//span[normalize-space()='Web Tables']")
    WebElement linkWebTables;

    @FindBy(xpath = "//span[normalize-space()='Buttons']")
    WebElement linkButtons;

    @FindBy(xpath = "//span[normalize-space()='Links']")
    WebElement linkLinks;

    @FindBy(xpath = "//span[normalize-space()='Broken Links - Images']")
    WebElement linkBrokenLinksImages;

    @FindBy(xpath = "//span[normalize-space()='Upload and Download']")
    WebElement linkUploadandDownload;

    @FindBy(xpath = "//span[normalize-space()='Dynamic Properties']")
    WebElement linkDynamicProperties;

    // ************************ ACTION METHODS ************************

    // ************************ ACTION METHODS ************************

    public void openTextBox() {
        linkTextBox.click();
    }

    public void openCheckBox() {
        linkCheckBox.click();
    }

    public void openRadioButton() {
        linkRadioButton.click();
    }

    public void openWebTables() {
        linkWebTables.click();
    }

    public void openButtons() {
        linkButtons.click();
    }

    public void openLinks() {
        linkLinks.click();
    }

    public void openBrokenLinksImages() {
        linkBrokenLinksImages.click();
    }

    public void openUploadAndDownload() {
        linkUploadandDownload.click();
    }

    public void openDynamicProperties() {
        linkDynamicProperties.click();
    }


}
