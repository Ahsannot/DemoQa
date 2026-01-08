package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CheckBoxPage extends BasePage {

    public CheckBoxPage(WebDriver driver) {
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(css = "button[title='Expand all']")
    WebElement btnExpandAll;

    @FindBy(xpath = "//span[contains(text(),'Notes')]")
    WebElement checkBoxNotes;

    @FindBy(xpath = "//span[contains(text(),'Downloads')]")
    WebElement checkBoxDownloads;

    // *************** OUTPUT LOCATORS ****************

    @FindBy(id = "result")
    WebElement resultText;

    // Optional: locator for all selected items inside result
    By selectedItems = By.cssSelector("#result span.text-success");

    //   ************************ ACTION METHODS ************************

    public void clickExpandAllbtn() {
        btnExpandAll.click();
    }

    public void clickCheckBoxNotes() {
        checkBoxNotes.click();
    }

    public void clickCheckBoxDownloads() {
        jsClick(checkBoxDownloads); // Using JS click if normal click fails
    }

    // *************** OUTPUT METHODS ****************

    // Waits for the result to appear and returns all selected items as a list
    public List<String> getSelectedItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selectedItems));
        return items.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}
