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
        HomePage home = new HomePage(getDriver());
        home.clickElementsLink();
        logger.info("Clicked Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(getDriver());
        menu.openDynamicProperties();

        DynamicPropertiesPage dynamicPropertiesPage = new DynamicPropertiesPage(getDriver());

        // -------- ENABLE AFTER TEST --------

        dynamicPropertiesPage.waitForEnableAfterButton();
        dynamicPropertiesPage.clickEnableAfterButton();
        logger.info("Enabled button test passed");

        // -------- VISIBLE AFTER TEST --------
        dynamicPropertiesPage.waitForVisibleAfterButton();
        Assert.assertTrue(
                dynamicPropertiesPage.isVisibleAfterButtonDisplayed(),
                "'Visible button' is not displayed"
        );
        logger.info("'Visible button' test passed");

        // -------- COLOR CHANGE TEST (class-based) --------
        dynamicPropertiesPage.waitForColorChangeToDanger();
        Assert.assertTrue(dynamicPropertiesPage.isButtonDanger(), "Button color did not change to Red");
        logger.info("Button text color is changed, test passed");
    }
}
