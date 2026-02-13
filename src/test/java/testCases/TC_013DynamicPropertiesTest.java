package testCases;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.DynamicPropertiesPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

import java.time.Duration;

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

        // Log initial state
        System.out.println("Button initially primary? " + dynamicPropertiesPage.isButtonPrimary());
        System.out.println("Initial color: " + dynamicPropertiesPage.getColorChangeButtonColor());

        // Wait for button to change color to danger (class-based)
        dynamicPropertiesPage.waitForColorChangeToDanger();

        // Log final state
        System.out.println("Button now danger? " + dynamicPropertiesPage.isButtonDanger());
        System.out.println("Color after: " + dynamicPropertiesPage.getColorChangeButtonColor());

        // Assert the change
        Assert.assertTrue(dynamicPropertiesPage.isButtonDanger(), "Button did not change to danger");
        logger.info("Button text color change test passed");
    }
}
