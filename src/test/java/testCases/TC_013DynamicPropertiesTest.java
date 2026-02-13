package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.DynamicPropertiesPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_013DynamicPropertiesTest extends BaseClass {

    @Test
    public void dynamicPropertiesTest() {

        logger.info("===== Starting Dynamic Properties Test =====");

        // -------- NAVIGATION --------
        HomePage home = new HomePage(driver);
        home.clickElementsLink();
        logger.info("Clicked Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        menu.openDynamicProperties();

        DynamicPropertiesPage dynamicPropertiesPage = new DynamicPropertiesPage(driver);

        // -------- ENABLE AFTER TEST --------
        dynamicPropertiesPage.waitForEnableAfterButton();
        dynamicPropertiesPage.clickEnableAfterButton();

        logger.info("Enabled button test passed");

        // -------- VISIBLE AFTER TEST --------
        dynamicPropertiesPage.waitForVisibleAfterButton();

        Assert.assertTrue(
                dynamicPropertiesPage.isVisibleAfterButtonDisplayed(),
                "Visible button is not displayed"
        );

        logger.info("Visible button test passed");

        // -------- COLOR CHANGE TEST --------
        String classBefore = dynamicPropertiesPage.getColorChangeButtonClass();

        try {
            Thread.sleep(6000); // required for demoqa color change
        } catch (Exception e) {
            logger.error("Exception occurred while waiting for color change", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        String classAfter = dynamicPropertiesPage.getColorChangeButtonClass();

        Assert.assertNotEquals(
                classBefore,
                classAfter,
                "Color Change ? button did not change color"
        );

        logger.info("Button text color change test passed");
        logger.info("===== Dynamic Properties Test Completed Successfully =====");
    }
}
