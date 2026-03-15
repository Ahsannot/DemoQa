package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ButtonsPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_009ButtonsTest extends BaseClass {

    @Test
    public void buttonsTest() {

        logger.info("===== Starting Buttons Page Test =====");

        // -------- NAVIGATION --------
        logger.info("Navigating to Home Page");
        HomePage home = new HomePage(getDriver());
        home.clickElementsLink();
        logger.info("Clicked on Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(getDriver());
        menu.openButtons();
        logger.info("Opened Buttons page from Elements menu");

        ButtonsPage buttonsPage = new ButtonsPage(getDriver());

        // -------- DOUBLE CLICK --------
        logger.info("Performing double click action");
        buttonsPage.doubleClickButton();

        logger.info("Validating double click success message");
        Assert.assertEquals(
                buttonsPage.getDoubleClickMessage(),
                "You have done a double click",
                "Double click message not displayed correctly"
        );
        logger.info("Double click validated successfully");

        // -------- RIGHT CLICK --------
        logger.info("Performing right click action");
        buttonsPage.rightClickButton();

        logger.info("Validating right click success message");
        Assert.assertEquals(
                buttonsPage.getRightClickMessage(),
                "You have done a right click",
                "Right click message not displayed correctly"
        );
        logger.info("Right click validated successfully");

        // -------- NORMAL CLICK --------
        logger.info("Performing normal click action");
        buttonsPage.clickDynamicButton();

        logger.info("Validating normal click success message");
        Assert.assertEquals(
                buttonsPage.getDynamicClickMessage(),
                "You have done a dynamic click",
                "Click message not displayed correctly"
        );
        logger.info("Normal click validated successfully");

        logger.info("===== Buttons Page Test Completed Successfully =====");
    }
}
