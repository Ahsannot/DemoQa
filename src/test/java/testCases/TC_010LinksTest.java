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
        HomePage home = new HomePage(getDriver());
        home.clickElementsLink();
        logger.info("Clicked on Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(getDriver());
        menu.openLinks();
        logger.info("Opened Links page from Elements menu");

        LinksPage linksPage = new LinksPage(getDriver());

        // -------- HOME LINK --------
        logger.info("Clicking Home link");
        String parentWindow = getDriver().getWindowHandle();
        linksPage.clickHomeLink();

        // Switch to new tab
        for (String window : getDriver().getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                getDriver().switchTo().window(window);
                break;
            }
        }

        Assert.assertTrue(getDriver().getCurrentUrl().contains("demoqa.com"),
                "Home link did not open correct URL. Current URL: " + getDriver().getCurrentUrl());
        getDriver().close();
        getDriver().switchTo().window(parentWindow);
        logger.info("Home link validated successfully");

        // -------- OTHER LINKS --------

        linksPage.clickCreatedLink();
        linksPage.waitForResponseMessageToContain("201");
        String createdResponse = linksPage.getResponseMessage();
        Assert.assertTrue(createdResponse.contains("201"),
                "Created link response not correct. Actual response: " + createdResponse);
        logger.info("CreatedLink validated successfully : 201");

        linksPage.clickNoContentLink();
        linksPage.waitForResponseMessageToContain("204");
        String noContentResponse = linksPage.getResponseMessage();
        System.out.println("No Content response: " + noContentResponse);
        Assert.assertTrue(noContentResponse.contains("204"),
                "No Content link response not correct. Actual response: " + noContentResponse);

        linksPage.clickMovedLink();
        linksPage.waitForResponseMessageToContain("301");
        String movedResponse = linksPage.getResponseMessage();
        Assert.assertTrue(movedResponse.contains("301"),
                "Moved link response not correct. Actual response: " + movedResponse);

        linksPage.clickBadRequestLink();
        linksPage.waitForResponseMessageToContain("400");
        String badRequestResponse = linksPage.getResponseMessage();
        Assert.assertTrue(badRequestResponse.contains("400"),
                "Bad Request response not correct. Actual response: " + badRequestResponse);

        linksPage.clickUnauthorizedLink();
        linksPage.waitForResponseMessageToContain("401");
        String unauthorizedResponse = linksPage.getResponseMessage();
        Assert.assertTrue(unauthorizedResponse.contains("401"),
                "Unauthorized response not correct. Actual response: " + unauthorizedResponse);

        linksPage.clickForbiddenLink();
        linksPage.waitForResponseMessageToContain("403");
        String forbiddenResponse = linksPage.getResponseMessage();
        Assert.assertTrue(forbiddenResponse.contains("403"),
                "Forbidden response not correct. Actual response: " + forbiddenResponse);

        linksPage.clickInvalidUrlLink();
        linksPage.waitForResponseMessageToContain("404");
        String invalidUrlResponse = linksPage.getResponseMessage();
        Assert.assertTrue(invalidUrlResponse.toLowerCase().contains("404"),
                "Invalid URL response not correct. Actual response: " + invalidUrlResponse);

        logger.info("===== Links Page Test Completed Successfully =====");
    }
}
