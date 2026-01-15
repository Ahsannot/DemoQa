package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LinksPage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_010LinksTest extends BaseClass {

    @Test
    public void linksTest() {

        logger.info("===== Starting Links Page Test =====");

        // -------- NAVIGATION --------
        logger.info("Navigating to Home Page");
        HomePage home = new HomePage(driver);
        home.clickElementsLink();
        logger.info("Clicked on Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        menu.openLinks();
        logger.info("Opened Links page from Elements menu");

        LinksPage linksPage = new LinksPage(driver);

        // -------- HOME LINK --------
        logger.info("Clicking Home link");
        String parentWindow = driver.getWindowHandle();
        linksPage.clickHomeLink();

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        Assert.assertTrue(
                driver.getCurrentUrl().contains("demoqa.com"),
                "Home link did not open correct URL"
        );

        driver.close();
        driver.switchTo().window(parentWindow);
        logger.info("Home link validated successfully");

        // -------- CREATED LINK --------
        logger.info("Clicking Created link");
        linksPage.clickCreatedLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("201"),
                "Created link response not displayed correctly"
        );
        logger.info("Created link validated successfully");

        // -------- NO CONTENT LINK --------
        logger.info("Clicking No Content link");
        linksPage.clickNoContentLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("204"),
                "No Content link response not displayed correctly"
        );
        logger.info("No Content link validated successfully");

        // -------- MOVED LINK --------
        logger.info("Clicking Moved link");
        linksPage.clickMovedLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("Moved"),
                "Moved link response not displayed correctly"
        );
        logger.info("Moved link validated successfully");

        // -------- BAD REQUEST LINK --------
        logger.info("Clicking Bad Request link");
        linksPage.clickBadRequestLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("400"),
                "Bad Request response not displayed correctly"
        );
        logger.info("Bad Request link validated successfully");

        // -------- UNAUTHORIZED LINK --------
        logger.info("Clicking Unauthorized link");
        linksPage.clickUnauthorizedLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("401"),
                "Unauthorized response not displayed correctly"
        );
        logger.info("Unauthorized link validated successfully");

        // -------- FORBIDDEN LINK --------
        logger.info("Clicking Forbidden link");
        linksPage.clickForbiddenLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().contains("403"),
                "Forbidden response not displayed correctly"
        );
        logger.info("Forbidden link validated successfully");

        // -------- INVALID URL LINK --------
        logger.info("Clicking Invalid URL link");
        linksPage.clickInvalidUrlLink();

        Assert.assertTrue(
                linksPage.getResponseMessage().toLowerCase().contains("error"),
                "Invalid URL response not displayed correctly"
        );
        logger.info("Invalid URL link validated successfully");

        logger.info("===== Links Page Test Completed Successfully =====");
    }
}
