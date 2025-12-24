package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//h5[normalize-space()='Book Store Application']")
    WebElement linkBookStoreApplication;



    //   ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOf(linkBookStoreApplication));
            return confirmation.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    public void clickBookStoreApplicationLink() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(linkBookStoreApplication));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        } catch (Exception e) {
            System.out.println("Unable to click Book Store Application link: " + e.getMessage());
        }
    }

}
