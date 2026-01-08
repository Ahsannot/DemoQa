package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CheckBoxPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

import java.util.Arrays;
import java.util.List;

public class TC_006CheckBoxPageTest extends BaseClass {

    @Test
    public void checkBoxTest() {

        logger.info("===== Starting CheckBoxPage Test =====");

        // ----------------- HOME PAGE -----------------
        HomePage homePage = new HomePage(driver);
        homePage.clickElementsLink();
        logger.info("===== Home Page =====");

        // ----------------- ElementsMenuComponent PAGE -----------------
        ElementsMenuComponent elementsComponentPage = new ElementsMenuComponent(driver);
        elementsComponentPage.openCheckBox();
        logger.info("===== Starting ElementsMenuComponent Page =====");

        // ----------------- CheckBoxPage PAGE -----------------
        CheckBoxPage checkBoxPage = new CheckBoxPage(driver);

        // Click actions
        checkBoxPage.clickExpandAllbtn();
        checkBoxPage.clickCheckBoxNotes();
        checkBoxPage.clickCheckBoxDownloads(); // Downloads includes wordFile & excelFile

        // Expected selected values
        List<String> expectedItems = Arrays.asList(
                "notes",
                "downloads",
                "wordFile",
                "excelFile"
        );

        // Get actual selected items from result div
        List<String> actualItems = checkBoxPage.getSelectedItems();

        // Validate exact values
        Assert.assertEquals(actualItems, expectedItems, "Selected checkbox values do not match expected!");
        logger.info("Selected items validated: " + actualItems);

        logger.info("===== Completing CheckBoxPage Test =====");
    }
}
