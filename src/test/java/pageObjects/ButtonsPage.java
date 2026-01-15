package pageObjects;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ButtonsPage extends BasePage {

    WebDriverWait wait;

    public ButtonsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =================== LOCATORS ===================

    @FindBy(id = "doubleClickBtn")
    WebElement doubleClickBtn;

    @FindBy(id = "rightClickBtn")
    WebElement rightClickBtn;

    @FindBy(xpath = "//button[text()='Click Me']")
    WebElement clickMeBtn;

    @FindBy(id = "doubleClickMessage")
    WebElement doubleClickMsg;

    @FindBy(id = "rightClickMessage")
    WebElement rightClickMsg;

    @FindBy(id = "dynamicClickMessage")
    WebElement clickMsg;

    // =================== ACTION METHODS ===================

    // Double click action
    public void doubleClickButton() {

        wait.until(ExpectedConditions.visibilityOf(doubleClickBtn));

        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtn).perform();
    }


    // Right click action
    public void rightClickButton() {
        wait.until(ExpectedConditions.visibilityOf(rightClickBtn));

        Actions actions = new Actions(driver);
        actions.contextClick(rightClickBtn).perform();
    }

    // Normal click action
    public void clickButton() {
        wait.until(ExpectedConditions.elementToBeClickable(clickMeBtn)).click();
    }

    // =================== VALIDATION METHODS ===================

    public String getDoubleClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(doubleClickMsg)).getText();
    }

    public String getRightClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(rightClickMsg)).getText();
    }

    public String getClickMessage() {
        return wait.until(ExpectedConditions.visibilityOf(clickMsg)).getText();
    }
}
