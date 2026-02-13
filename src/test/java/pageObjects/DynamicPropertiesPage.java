package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DynamicPropertiesPage extends BasePage {

    public Logger logger = LogManager.getLogger(DynamicPropertiesPage.class);
    public WebDriverWait wait;

    public DynamicPropertiesPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= LOCATORS =================

    @FindBy(id = "enableAfter")
    public WebElement enableAfterButton;

    @FindBy(id = "visibleAfter")
    public WebElement visibleAfterButton;

    @FindBy(id = "colorChange")
    public WebElement colorChangeButton;

    // ================= ACTION METHODS =================

    public void waitForEnableAfterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(enableAfterButton));
        logger.info("Button is enabled after 5 Sec");
    }

    public void clickEnableAfterButton() {
        enableAfterButton.click();
        logger.info("Clicked Enabled  button");
    }

    public void waitForVisibleAfterButton() {
        wait.until(ExpectedConditions.visibilityOf(visibleAfterButton));
        logger.info("Button is visible after 5 Sec");
    }

    public boolean isVisibleAfterButtonDisplayed() {
        return visibleAfterButton.isDisplayed();
    }

    public String getColorChangeButtonClass() {
        String className = colorChangeButton.getAttribute("class");
        logger.info("Color Change, button class: " + className);
        return className;
    }
}
