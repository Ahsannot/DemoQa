package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.WebTablesPage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_008WebTablesTest extends BaseClass {

    @Test
    public void webTablesTest() {

        logger.info("===== Starting Web Tables Test =====");

        // -------- NAVIGATION --------
        HomePage home = new HomePage(driver);
        home.clickElementsLink();

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        menu.openWebTables();

        WebTablesPage webTables = new WebTablesPage(driver);

        // -------- ADD EMPLOYEE --------
        webTables.clickAddNewRecord();
        webTables.addEmployee(
                "John",
                "Doe",
                "john.doe@test.com",
                "30",
                "50000",
                "QA"
        );

        webTables.search("John");

        Assert.assertTrue(
                webTables.isEmployeePresent("John"),
                "Newly added employee record is NOT displayed"
        );

        logger.info("Employee added successfully");

        // -------- EDIT EMPLOYEE --------
        webTables.editEmployee("John", "35");
        webTables.search("John");

        Assert.assertTrue(
                webTables.isEmployeePresent("35"),
                "Employee age was NOT updated successfully"
        );

        logger.info("Employee edited successfully");

        // -------- DELETE EMPLOYEE --------
        webTables.deleteEmployee("John");
        webTables.search("John");

        Assert.assertFalse(
                webTables.isEmployeePresent("John"),
                "Employee record was NOT deleted successfully"
        );

        logger.info("Employee deleted successfully");

        logger.info("===== Web Tables Test Completed Successfully =====");
    }
}
