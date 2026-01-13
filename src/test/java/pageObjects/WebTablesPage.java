package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebTablesPage extends BasePage {

    WebDriverWait wait;

    public WebTablesPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ------------------- LOCATORS -------------------

    @FindBy(id = "addNewRecordButton")
    WebElement addButton;

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement email;

    @FindBy(id = "age")
    WebElement age;

    @FindBy(id = "salary")
    WebElement salary;

    @FindBy(id = "department")
    WebElement department;

    @FindBy(id = "submit")
    WebElement submit;

    @FindBy(css = ".rt-tbody .rt-tr-group")
    List<WebElement> tableRows;

    @FindBy(id = "searchBox")
    WebElement searchBox;

    // ------------------- ACTION METHODS -------------------

    public void clickAddNewRecord() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void addEmployee(String fName, String lName, String mail,
                            String empAge, String empSalary, String dept) {

        wait.until(ExpectedConditions.visibilityOf(firstName)).sendKeys(fName);
        lastName.sendKeys(lName);
        email.sendKeys(mail);
        age.sendKeys(empAge);
        salary.sendKeys(empSalary);
        department.sendKeys(dept);
        submit.click();
    }

    public void search(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
        searchBox.sendKeys(keyword);
    }

    // ------------------- DYNAMIC ROW HANDLING -------------------

    private WebElement getRowByText(String text) {
        wait.until(ExpectedConditions.visibilityOfAllElements(tableRows));
        return tableRows.stream()
                .filter(row -> row.getText().contains(text))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmployeePresent(String text) {
        return getRowByText(text) != null;
    }

    public void editEmployee(String text, String newAge) {

        WebElement row = getRowByText(text);
        Assert.assertNotNull(row, "Employee row not found for editing");

        WebElement editBtn = row.findElement(By.cssSelector("span[title='Edit']"));

        // Scroll element into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", editBtn);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(editBtn));

        try {
            editBtn.click();
        } catch (ElementClickInterceptedException e) {
            // Fallback to JS click if ad blocks it
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", editBtn);
        }

        // Update age
        wait.until(ExpectedConditions.visibilityOf(age)).clear();
        age.sendKeys(newAge);
        submit.click();
    }


    public void deleteEmployee(String text) {

        WebElement row = getRowByText(text);
        Assert.assertNotNull(row, "Employee row not found for deletion");

        WebElement deleteBtn = row.findElement(By.cssSelector("span[title='Delete']"));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", deleteBtn);

        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));

        try {
            deleteBtn.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", deleteBtn);
        }
    }

}
