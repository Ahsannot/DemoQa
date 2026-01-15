package pageObjects.components;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.BasePage;

import java.time.Duration;

public class ElementsMenuComponent extends BasePage {

    public JavascriptExecutor js;
    public WebDriverWait wait;  // Explicit wait instance

    public ElementsMenuComponent(WebDriver driver){
        super(driver);
        // Initialize WebDriverWait for 10 seconds
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // ************************ LOCATORS ************************

    @FindBy(xpath = "//span[normalize-space()='Text Box']")
    public WebElement linkTextBox;

    @FindBy(xpath = "//span[normalize-space()='Check Box']")
    public WebElement linkCheckBox;

    @FindBy(xpath = "//span[normalize-space()='Radio Button']")
    public WebElement linkRadioButton;

    @FindBy(xpath = "//span[normalize-space()='Web Tables']")
    public WebElement linkWebTables;

    @FindBy(xpath = "//span[normalize-space()='Buttons']")
    public WebElement linkButtons;

    @FindBy(xpath = "//span[normalize-space()='Links']")
    public WebElement linkLinks;

    @FindBy(xpath = "//span[normalize-space()='Broken Links - Images']")
    public WebElement linkBrokenLinksImages;

    @FindBy(xpath = "//span[normalize-space()='Upload and Download']")
    public WebElement linkUploadandDownload;

    @FindBy(xpath = "//span[normalize-space()='Dynamic Properties']")
    public WebElement linkDynamicProperties;

    // ************************ HELPER METHOD ************************

    public void clickElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    // ************************ ACTION METHODS ************************

    public void openTextBox() {
        clickElement(linkTextBox);
    }

    public void openCheckBox() {
        clickElement(linkCheckBox);
    }

    public void openRadioButton() {
        clickElement(linkRadioButton);
    }

    public void openWebTables() {
        clickElement(linkWebTables);
    }

    public void openButtons() {
        clickElement(linkButtons);
    }

    public void openLinks() {
        clickElement(linkLinks);
    }

    public void openBrokenLinksImages() {
        clickElement(linkBrokenLinksImages);
    }

    public void openUploadAndDownload() {
        clickElement(linkUploadandDownload);
    }

    public void openDynamicProperties() {
        clickElement(linkDynamicProperties);
    }
}
