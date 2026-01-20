package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class UploadDownloadPage extends BasePage {

    public Logger logger = LogManager.getLogger(UploadDownloadPage.class);
    public WebDriverWait wait;

    public UploadDownloadPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= LOCATORS =================

    @FindBy(id = "downloadButton")
    public WebElement downloadButton;

    @FindBy(id = "uploadFile")
    public WebElement uploadInput;

    @FindBy(id = "uploadedFilePath")
    public WebElement uploadedFilePathText;

    // ================= ACTION METHODS =================

    public void clickDownloadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadButton)).click();
        logger.info("Clicked Download button");
    }

    public void uploadFile(String filePath) {
        File file = new File(filePath);
        wait.until(ExpectedConditions.visibilityOf(uploadInput))
                .sendKeys(file.getAbsolutePath());
        logger.info("Uploaded file: " + file.getAbsolutePath());
    }

    public String waitForUploadedFileText() {
        String text = wait.until(ExpectedConditions
                .visibilityOf(uploadedFilePathText)).getText();
        logger.info("Uploaded file path text displayed: " + text);
        return text;
    }

    // ================= FILE VALIDATION =================

    public boolean waitForFileDownload(String downloadDir, String fileName, int timeoutSeconds) {

        File dir = new File(downloadDir);
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals(fileName)) {
                        logger.info("Downloaded file found: " + fileName);
                        return true;
                    }
                }
            }
        }
        logger.warn("Downloaded file NOT found within timeout: " + fileName);
        return false;
    }
}
