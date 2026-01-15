package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrokenLinksPage extends BasePage {

    public Logger logger = LogManager.getLogger(BrokenLinksPage.class);

    public BrokenLinksPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    @FindBy(linkText = "Click Here for Valid Link")
    public WebElement validLink;

    @FindBy(linkText = "Click Here for Broken Link")
    public WebElement brokenLink;

    @FindBy(xpath = "//img[1]")
    public WebElement validImage;

    @FindBy(xpath = "//img[2]")
    public WebElement brokenImage;

    // ================= HELPER METHODS =================

    public String getHref(WebElement element) {
        String tag = element.getTagName();
        if (tag.equalsIgnoreCase("a")) {
            return element.getAttribute("href");
        } else if (tag.equalsIgnoreCase("img")) {
            return element.getAttribute("src");
        }
        return null;
    }

    public int getLinkStatusCode(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 sec timeout
            connection.connect();
            int code = connection.getResponseCode();
            return code;
        } catch (Exception e) {
            logger.error("Exception while checking URL: " + url + " -> " + e.getMessage());
            return -1;
        }
    }

    public List<String> getAllLinks() {
        List<String> allLinks = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.tagName("a"));

        for (WebElement el : elements) {
            String url = el.getAttribute("href");
            if (url != null && !url.isEmpty() && !url.startsWith("javascript") && !url.startsWith("mailto")) {
                allLinks.add(url);
                logger.info("Found link: " + url);
            }
        }
        logger.info("Total valid links found: " + allLinks.size());
        return allLinks;
    }

    public List<String> getBrokenLinks() {
        List<String> brokenLinks = new ArrayList<>();
        List<String> allLinks = getAllLinks();

        for (String url : allLinks) {
            int status = getLinkStatusCode(url);
            if (status >= 400 || status == -1) {
                brokenLinks.add(url + " -> " + status);
                logger.warn("Broken link detected: " + url + " -> " + status);
            } else {
                logger.info("Valid link: " + url + " -> " + status);
            }
        }

        logger.info("Total broken links: " + brokenLinks.size());
        return brokenLinks;
    }
}
