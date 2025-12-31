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

// BooksPage class represents the "Books" page of the application
// Contains locators and actions specific to this page
public class BooksPage extends BasePage {

    // Logger instance for logging info, error, and debug messages
    public Logger logger = LogManager.getLogger(BooksPage.class);

    // Constructor: initializes the page with WebDriver instance
    public BooksPage(WebDriver driver){
        super(driver);
    }

    // ************************ LOCATORS ************************
    // Web elements located using @FindBy annotation

    // Locator for "Book Store" text on the page
    @FindBy(xpath = "//span[normalize-space()='Book Store']")
    WebElement text_BookStore;

    // Locator for "Login" link on the page
    @FindBy(xpath = "//span[normalize-space()='Login']")
    WebElement linkLogin;

    // ************************ ACTION METHODS ************************

    /**
     * Fetches the visible confirmation message from the page.
     * Waits up to 10 seconds for the element to appear.
     * @return the text of the confirmation message or null if not found
     */
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

    /**
     * Clicks on the "Login" link using JavaScript click (jsClick) method
     */
    public void clickLoginLink(){
        try {
            jsClick(linkLogin); // Uses JS click to avoid element overlay issues
            logger.info("Clicked on Login link.");
        } catch (Exception e) {
            logger.error("Error clicking Login link: " + e.getMessage());
        }
    }

    // ************************ GET ALL LINKS WITH TEXT ************************

    /**
     * Get all <a> link texts and their corresponding URLs dynamically
     * @return List of strings in the format: "LinkText -> URL"
     */
    public List<String> getAllLinksWithText() {
        List<String> links = new ArrayList<>();

        // Wait until at least one <a> element is present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        // Find all <a> elements on the page
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        logger.info("Total <a> elements found: " + allLinks.size());

        // Iterate through each link and extract text and href
        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");
            String text = link.getText().trim();

            // Only consider links with non-empty href
            if (url != null && !url.isEmpty()) {
                links.add(text + " -> " + url);
                logger.info("Link found: " + text + " -> " + url);
            }
        }

        logger.info("Total valid links found: " + links.size());
        return links;
    }

    // ************************ BROKEN LINK CHECK ************************

    /**
     * Checks all links on the page for broken URLs
     * Uses HTTP response code to determine validity
     * @return List of broken links with their response codes
     */
    public List<String> getBrokenLinks() {
        List<String> brokenLinks = new ArrayList<>();
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");

            // Skip links that are null, empty, mailto:, or javascript:
            if (url == null || url.isEmpty() || url.startsWith("mailto") || url.startsWith("javascript")) {
                continue;
            }

            try {
                // Open HTTP connection and check response code
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000); // 5 seconds timeout
                connection.connect();

                int responseCode = connection.getResponseCode();

                // Consider response codes 400+ as broken
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
