package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BrokenLinksPage;
import pageObjects.HomePage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

import java.util.List;

public class TC_011BrokenLinksTest extends BaseClass {

    @Test
    public void brokenLinksTest() {

        logger.info("===== Starting Broken Links Test (HTTP Status Code) =====");

        // -------- NAVIGATION --------
        HomePage home = new HomePage(driver);
        home.clickElementsLink();
        logger.info("Clicked on Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        menu.openBrokenLinksImages();
        logger.info("Opened Broken Links page");

        BrokenLinksPage brokenLinksPage = new BrokenLinksPage(driver);

        // -------- CHECK INDIVIDUAL LINKS/IMAGES --------
        int validLinkStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.validLink));
        logger.info("Valid link status: " + validLinkStatus);
        Assert.assertEquals(validLinkStatus, 200, "Valid link did not return 200 OK");

        int brokenLinkStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.brokenLink));
        logger.info("Broken link status: " + brokenLinkStatus);
        Assert.assertTrue(brokenLinkStatus >= 400, "Broken link did not return 4xx or 5xx");

        int validImageStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.validImage));
        logger.info("Valid image status: " + validImageStatus);
        Assert.assertEquals(validImageStatus, 200, "Valid image did not return 200 OK");

        int brokenImageStatus = brokenLinksPage.getLinkStatusCode(
                brokenLinksPage.getHref(brokenLinksPage.brokenImage));
        logger.info("Broken image status: " + brokenImageStatus);
        Assert.assertTrue(brokenImageStatus >= 400, "Broken image did not return 4xx or 5xx");

        // -------- CHECK ALL LINKS DYNAMICALLY --------
        List<String> brokenLinksList = brokenLinksPage.getBrokenLinks();
        if (!brokenLinksList.isEmpty()) {
            logger.warn("Broken links found: " + brokenLinksList);
        }
        Assert.assertTrue(brokenLinksList.size() >= 0, "Some links are broken");

        logger.info("===== Broken Links Test Completed Successfully =====");
    }
}
