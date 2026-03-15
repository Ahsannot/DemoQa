package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.BooksPage;
import pageObjects.HomePage;
import testBase.BaseClass;
import java.util.List;

// Test case class for verifying functionality on the Books Page
// Extends BaseClass which provides driver setup, logger, and utility methods
public class TC_004BooksPageTest extends BaseClass {

    @Test
    public void booksPage() {

        logger.info("===== Starting Books Page Test =====");

        // ----------------- HOME PAGE -----------------
        // Initialize HomePage object
        HomePage homePage = new HomePage(getDriver());

        // Verify the HomePage confirmation message using hard assertion
        validatePageMessageHard(
                homePage.getConfirmationMessage(), // fetch message from page
                "Book Store Application",          // expected message
                "HomePage"                         // page name for logging
        );

        // Click on "Book Store Application" link to navigate to Books Page
        homePage.clickBookStoreApplicationLink();

        // ----------------- BOOKS PAGE -----------------
        // Initialize BooksPage object
        BooksPage booksPage = new BooksPage(getDriver());

        // Verify the BooksPage confirmation message using hard assertion
        validatePageMessageHard(
                booksPage.getConfirmationMessage(), // fetch message from page
                "Book Store",                       // expected message
                "BooksPage"                         // page name for logging
        );

        // ----------------- GET ALL LINKS WITH TEXT -----------------
        // Fetch all links on the Books Page dynamically
        List<String> allLinksWithText = booksPage.getAllLinksWithText();
        logger.info("----- All Links on Books Page -----");
        for (String link : allLinksWithText) {
            System.out.println(link); // prints each link in format: LinkText -> URL
        }

        // ----------------- CHECK BROKEN LINKS -----------------
        // Fetch all broken links on the Books Page
        List<String> brokenLinks = booksPage.getBrokenLinks();
        SoftAssert softAssert = new SoftAssert(); // allows multiple failures without stopping the test

        if (brokenLinks.isEmpty()) {
            logger.info("No broken links found on the page.");
        } else {
            logger.warn("----- Broken Links on Books Page -----");
            // Log each broken link and register it as a soft assertion failure
            for (String link : brokenLinks) {
                logger.error(link);
                softAssert.fail("Broken link: " + link);
            }
        }

        // Assert all soft assertions at the end to fail test if any broken link exists
        softAssert.assertAll();

        logger.info("===== Books Page Test Completed =====");
    }
}
