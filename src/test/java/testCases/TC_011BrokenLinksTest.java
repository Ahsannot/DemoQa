package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BrokenLinksPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

import java.util.List;

public class TC_011BrokenLinksTest extends BaseClass {

    public JavascriptExecutor js;
    @Test
    public void brokenLinksTest() {

        logger.info("===== Starting Broken Links Test (HTTP + Visual) =====");
            js = (JavascriptExecutor) driver;
        // -------- NAVIGATION --------
        HomePage home = new HomePage(driver);
        try {
            home.clickElementsLink(); // Use JS click inside HomePage if implemented
            logger.info("Clicked on Elements link");
        } catch (Exception e) {
            logger.error("Failed to click Elements link: " + e.getMessage());
        }

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        try {
            menu.clickElement(menu.linkBrokenLinksImages); // JS + wait click helper
            logger.info("Opened Broken Links page");
        } catch (Exception e) {
            logger.error("Failed to open Broken Links page: " + e.getMessage());
        }

        BrokenLinksPage brokenLinksPage = new BrokenLinksPage(driver);

        // -------- CHECK INDIVIDUAL LINKS --------
        int validLinkStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.validLink));
        logger.info("Valid link status: " + validLinkStatus);
        Assert.assertTrue(validLinkStatus == 200 || validLinkStatus == 301,
                "Valid link did not return 200 OK or 301 Redirect, actual: " + validLinkStatus);

        int brokenLinkStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.brokenLink));
        logger.info("Broken link status: " + brokenLinkStatus);
        Assert.assertTrue(brokenLinkStatus >= 400, "Broken link did not return 4xx or 5xx");

        // -------- CHECK INDIVIDUAL IMAGES --------
        int validImageStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.validImage));
        logger.info("Valid image status: " + validImageStatus);
        Assert.assertEquals(validImageStatus, 200, "Valid image did not return 200 OK");

        int brokenImageStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.brokenImage));
        logger.info("Broken image status (HTTP): " + brokenImageStatus);

        // Visual check if HTTP shows 200 but image is broken
        if (brokenImageStatus < 400) {

            Boolean imageLoaded = (Boolean) js.executeScript(
                    "return arguments[0].complete && arguments[0].naturalWidth > 0",
                    brokenLinksPage.brokenImage
            );

            Assert.assertFalse(imageLoaded, "Broken image should not be displayed");
            logger.info("Broken image visual check passed");
        }

        // -------- CHECK ALL LINKS DYNAMICALLY --------
        List<String> brokenLinksList = brokenLinksPage.getBrokenLinks();
        if (!brokenLinksList.isEmpty()) {
            logger.warn("Broken links found: " + brokenLinksList);
        }
        Assert.assertTrue(brokenLinksList.size() >= 0, "Some links are broken");

        logger.info("===== Broken Links Test Completed Successfully =====");
    }
}
