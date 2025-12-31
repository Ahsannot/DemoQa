package pageObjects;

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

    public BooksPage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//span[normalize-space()='Book Store']")
    WebElement text_BookStore;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    WebElement linkLogin;

    //   ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOf(text_BookStore));
            return confirmation.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    public void clickLoginLink(){
        jsClick(linkLogin);
    }

    // ************************ BROKEN LINK CHECK ************************

    public List<String> getBrokenLinks() {

        List<String> brokenLinks = new ArrayList<>();

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
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
                    brokenLinks.add(url + " --> " + responseCode);
                }

            } catch (Exception e) {
                brokenLinks.add(url + " --> Exception");
            }
        }
        return brokenLinks;
    }
}
