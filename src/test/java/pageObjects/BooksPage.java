package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BooksPage extends BasePage {

    public Logger logger = LogManager.getLogger(BooksPage.class);

    public BooksPage(WebDriver driver){
        super(driver);
    }

    // ************************ LOCATORS ************************

    @FindBy(xpath = "//span[normalize-space()='Book Store']")
    WebElement text_BookStore;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    WebElement linkLogin;

    // ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation =
                    wait.until(ExpectedConditions.visibilityOf(text_BookStore));
            String message = confirmation.getText();
            logger.info("Confirmation message fetched: " + message);
            return message;
        } catch (Exception e) {
            logger.error("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    public void clickLoginLink(){
        try {
            jsClick(linkLogin);
            logger.info("Clicked on Login link.");
        } catch (Exception e) {
            logger.error("Error clicking Login link: " + e.getMessage());
        }
    }

    // ************************ GET ALL LINKS DYNAMICALLY ************************

    /**
     * Get all href attributes from the page links dynamically
     * @return List of all link URLs
     */
    public List<String> getAllLinkUrls() {
        List<String> urls = new ArrayList<>();

        // Wait for all links to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        logger.info("Total <a> elements found: " + allLinks.size());

        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");
            String text = link.getText();
            logger.info("Link found: '" + text + "' -> " + url);

            if (url != null && !url.isEmpty()) {
                urls.add(url);
            }
        }

        logger.info("Total non-empty href links: " + urls.size());
        return urls;
    }


    // ************************ BROKEN LINK CHECK WITH LOGGER ************************

    public List<String> getBrokenLinks() {

        List<String> brokenLinks = new ArrayList<>();
        List<WebElement> allLinks = driver.findElements(By.tagName("a")); // dynamic fetch

        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");

            // Skip invalid links
            if (url == null || url.isEmpty()
                    || url.startsWith("mailto")
                    || url.startsWith("javascript")) {
                continue;
            }

            try {
                HttpURLConnection connection =
                        (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.connect();

                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    logger.error("Broken link detected: " + url + " --> " + responseCode);
                    brokenLinks.add(url + " --> " + responseCode);
                } else {
                    logger.info("Valid link: " + url + " --> " + responseCode);
                }

            } catch (Exception e) {
                logger.error("Exception for URL: " + url + " --> " + e.getMessage());
                brokenLinks.add(url + " --> Exception");
            }
        }

        if (brokenLinks.isEmpty()) {
            logger.info("No broken links found on the Books Page.");
        } else {
            logger.warn("Total broken links found: " + brokenLinks.size());
        }

        return brokenLinks;
    }
}
