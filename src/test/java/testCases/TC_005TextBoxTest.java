package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.TextBoxPage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

public class TC_005TextBoxTest extends BaseClass {

    @Test
    public void textBox(){
        logger.info("===== Starting TextBox Test =====");


        // ----------------- HOME PAGE -----------------
        // Initialize HomePage object
        HomePage homePage = new HomePage(getDriver());
        homePage.clickElementsLink();
        logger.info("===== Home Page =====");


        // ----------------- ElementsMenuComponent PAGE -----------------
        // Initialize ElementsMenuComponent object
        ElementsMenuComponent elementsComponentPage = new ElementsMenuComponent(getDriver());
        elementsComponentPage.openTextBox();
        logger.info("===== Starting ElementsMenuComponent Page =====");

        // ----------------- TextBoxPage PAGE -----------------
        // Initialize TextBoxPage object
        TextBoxPage textBoxPage = new TextBoxPage(getDriver());

        String userName = randomAlphaNumeric().toUpperCase();
        String email = randomAlphaNumeric() + "@gmail.com";
        String currentAddress = randomString();
        String permanentAddress = randomString();

        textBoxPage.enterUserName(userName);
        textBoxPage.enterUserEmail(email);
        textBoxPage.enterCurrentAddress(currentAddress);
        textBoxPage.enterPermanentAddress(permanentAddress);
        textBoxPage.clickSubmitBtn();

        // ASSERTIONS
        Assert.assertEquals(textBoxPage.getNameOutput(), "Name:" + userName);
        Assert.assertEquals(textBoxPage.getEmailOutput(), "Email:" + email);
        Assert.assertTrue(textBoxPage.getCurrentAddressOutput().contains(currentAddress));
        Assert.assertTrue(textBoxPage.getPermanentAddressOutput().contains(permanentAddress));

        logger.info("===== Completing TextBox Test =====");
    }
}
