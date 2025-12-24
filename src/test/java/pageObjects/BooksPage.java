package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

}
