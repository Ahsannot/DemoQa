package testCases;

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
        HomePage homePage = new HomePage(driver);
        homePage.clickElementsLink();
        logger.info("===== Home Page =====");


        // ----------------- ElementsMenuComponent PAGE -----------------
        // Initialize ElementsMenuComponent object
        ElementsMenuComponent elementsComponentPage = new ElementsMenuComponent(driver);
        elementsComponentPage.openTextBox();
        logger.info("===== Starting ElementsMenuComponent Page =====");

        // ----------------- TextBoxPage PAGE -----------------
        // Initialize TextBoxPage object
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        textBoxPage.enterUserName(randomAlphaNumeric().toUpperCase());
        textBoxPage.enterUserEmail(randomAlphaNumeric() + "@gmail.com");
        textBoxPage.enterCurrentAddress(randomString());
        textBoxPage.enterPermanentAddress(randomString());
        textBoxPage.clickSubmitBtn();

        logger.info("===== Completing TextBox Test =====");
    }
}
