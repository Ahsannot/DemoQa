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

    // =================== LOCATORS ===================

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

    // =================== ACTION METHODS ===================

    // Click on "Add" button
    public void clickAddNewRecord() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    // Add a new employee
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

    // Search employee in table
    public void search(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
        searchBox.sendKeys(keyword);
    }

    // =================== DYNAMIC ROW HANDLING ===================

    // Find a table row that contains given text
    public WebElement getRowByText(String text) {
        for (WebElement row : tableRows) {
            if (row.getText().contains(text)) {
                return row;
            }
        }
        return null;
    }

    // Check if employee exists in table
    public boolean isEmployeePresent(String text) {
        return getRowByText(text) != null;
    }

    // =================== EDIT EMPLOYEE ===================

    public void editEmployee(String text, String newAge) {

        // Step 1: Find employee row
        WebElement row = getRowByText(text);
        Assert.assertNotNull(row, "Employee row not found for editing");

        // Step 2: Find Edit button
        WebElement editBtn = row.findElement(By.cssSelector("span[title='Edit']"));

        // Step 3: Create JavaScript executor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Step 4: Scroll into view
        js.executeScript("arguments[0].scrollIntoView(true);", editBtn);

        // Step 5: Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(editBtn));

        // Step 6: Click Edit button
        try {
            editBtn.click();
        } catch (ElementClickInterceptedException e) {
            // Use JS click if blocked by ads
            js.executeScript("arguments[0].click();", editBtn);
        }

        // Step 7: Update age
        wait.until(ExpectedConditions.visibilityOf(age));
        age.clear();
        age.sendKeys(newAge);

        // Step 8: Submit changes
        submit.click();
    }

    // =================== DELETE EMPLOYEE ===================

    public void deleteEmployee(String text) {

        // Step 1: Find employee row
        WebElement row = getRowByText(text);
        Assert.assertNotNull(row, "Employee row not found for deletion");

        // Step 2: Find Delete button
        WebElement deleteBtn = row.findElement(By.cssSelector("span[title='Delete']"));

        // Step 3: Create JavaScript executor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Step 4: Scroll into view
        js.executeScript("arguments[0].scrollIntoView(true);", deleteBtn);

        // Step 5: Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));

        // Step 6: Click Delete button
        try {
            deleteBtn.click();
        } catch (ElementClickInterceptedException e) {
            // Use JS click if blocked by ads
            js.executeScript("arguments[0].click();", deleteBtn);
        }
    }
}
