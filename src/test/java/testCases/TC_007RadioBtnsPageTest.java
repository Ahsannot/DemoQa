package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RadioButtonPage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_007RadioBtnsPageTest extends BaseClass {

    @Test
    public void radioBtnsTest() {

        logger.info("===== Starting Radio Button Test =====");

        // ----------------- HOME PAGE -----------------
        HomePage homePage = new HomePage(getDriver());
        homePage.clickElementsLink();

        // ----------------- ELEMENTS MENU -----------------
        ElementsMenuComponent elementsComponentPage = new ElementsMenuComponent(getDriver());
        elementsComponentPage.openRadioButton();

        // ----------------- RADIO BUTTON PAGE -----------------
        RadioButtonPage radioButtonPage = new RadioButtonPage(getDriver());

        // -------- YES RADIO BUTTON VALIDATION --------
        radioButtonPage.clickYes();

        Assert.assertTrue(
                radioButtonPage.isYesSelected(),
                "Yes radio button is NOT selected"
        );

        Assert.assertEquals(
                radioButtonPage.getSelectedResultText(),
                "Yes",
                "Incorrect selection message for Yes radio button"
        );

        logger.info("Yes radio button validated successfully");

        // -------- IMPRESSIVE RADIO BUTTON VALIDATION --------
        radioButtonPage.clickImpressive();

        Assert.assertTrue(
                radioButtonPage.isImpressiveSelected(),
                "Impressive radio button is NOT selected"
        );

        Assert.assertEquals(
                radioButtonPage.getSelectedResultText(),
                "Impressive",
                "Incorrect selection message for Impressive radio button"
        );

        logger.info("Impressive radio button validated successfully");

        // -------- NO RADIO BUTTON VALIDATION --------
        Assert.assertFalse(
                radioButtonPage.isNoEnabled(),
                "No radio button should be disabled"
        );

        logger.info("No radio button is disabled as expected");
    }
}
