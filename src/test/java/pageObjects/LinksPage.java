package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LinksPage extends BasePage {

    public WebDriverWait wait;

    public LinksPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    // ================= ACTION METHODS =================

    public void clickHomeLink() {
        wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
    }

    public void clickCreatedLink() {
        wait.until(ExpectedConditions.elementToBeClickable(createdLink)).click();
    }

    public void clickNoContentLink() {
        wait.until(ExpectedConditions.elementToBeClickable(noContentLink)).click();
    }

    public void clickMovedLink() {
        wait.until(ExpectedConditions.elementToBeClickable(movedLink)).click();
    }

    public void clickBadRequestLink() {
        wait.until(ExpectedConditions.elementToBeClickable(badRequestLink)).click();
    }

    public void clickUnauthorizedLink() {
        wait.until(ExpectedConditions.elementToBeClickable(unauthorizedLink)).click();
    }

    public void clickForbiddenLink() {
        wait.until(ExpectedConditions.elementToBeClickable(forbiddenLink)).click();
    }

    public void clickInvalidUrlLink() {
        wait.until(ExpectedConditions.elementToBeClickable(invalidUrlLink)).click();
    }

    // ================= VALIDATION METHODS =================

    public String getResponseMessage() {
        return wait.until(ExpectedConditions.visibilityOf(linkResponseMessage)).getText();
    }
}
