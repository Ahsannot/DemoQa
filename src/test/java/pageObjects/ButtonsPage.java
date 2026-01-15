package pageObjects;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ButtonsPage extends BasePage {

    public WebDriverWait wait;
    public Actions actions;

    public ButtonsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    // =================== LOCATORS ===================

    @FindBy(id = "doubleClickBtn")
    public WebElement doubleClickBtn;

    @FindBy(id = "rightClickBtn")
    public WebElement rightClickBtn;

    @FindBy(xpath = "//button[text()='Click Me']")
    public WebElement clickMeBtn;

    @FindBy(id = "doubleClickMessage")
    public WebElement doubleClickMsg;

    @FindBy(id = "rightClickMessage")
    public WebElement rightClickMsg;

    @FindBy(id = "dynamicClickMessage")
    public WebElement dynamicClickMsg;

    // =================== ACTION METHODS ===================

    public void doubleClickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(doubleClickBtn));
        actions.doubleClick(doubleClickBtn).perform();
    }

    public void rightClickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(rightClickBtn));
        actions.contextClick(rightClickBtn).perform();
    }

    public void clickDynamicButton() {
        wait.until(ExpectedConditions.elementToBeClickable(clickMeBtn)).click();
    }

    // =================== VALIDATION METHODS ===================

    public String getDoubleClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(doubleClickMsg)).getText();
    }

    public String getRightClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(rightClickMsg)).getText();
    }

    public String getDynamicClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(dynamicClickMsg)).getText();
    }
}
