package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LinksPage extends BasePage {

    public WebDriverWait wait;
    public JavascriptExecutor js;

    public LinksPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    // ================= LOCATORS =================
    @FindBy(id = "simpleLink")
    public WebElement homeLink;

    @FindBy(id = "created")
    public WebElement createdLink;

    @FindBy(id = "no-content")
    public WebElement noContentLink;

    @FindBy(id = "moved")
    public WebElement movedLink;

    @FindBy(id = "bad-request")
    public WebElement badRequestLink;

    @FindBy(id = "unauthorized")
    public WebElement unauthorizedLink;

    @FindBy(id = "forbidden")
    public WebElement forbiddenLink;

    @FindBy(id = "invalid-url")
    public WebElement invalidUrlLink;

    @FindBy(id = "linkResponse")
    public WebElement linkResponseMessage;

    // ================= ACTION METHODS USING JS =================
    public void clickElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    public void clickHomeLink() {
        clickElement(homeLink);
    }

    public void clickCreatedLink() {
        clickElement(createdLink);
    }

    public void clickNoContentLink() {
        clickElement(noContentLink);
    }

    public void clickMovedLink() {
        clickElement(movedLink);
    }

    public void clickBadRequestLink() {
        clickElement(badRequestLink);
    }

    public void clickUnauthorizedLink() {
        clickElement(unauthorizedLink);
    }

    public void clickForbiddenLink() {
        clickElement(forbiddenLink);
    }

    public void clickInvalidUrlLink() {
        clickElement(invalidUrlLink);
    }

    // ================= VALIDATION METHODS =================
    public String getResponseMessage() {
        return wait.until(ExpectedConditions.visibilityOf(linkResponseMessage)).getText();
    }

    // Wait until response message contains expected text
    public void waitForResponseMessageToContain(String expectedText) {
        wait.until(ExpectedConditions.textToBePresentInElement(linkResponseMessage, expectedText));
    }
}
