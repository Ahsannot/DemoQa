package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.BooksPage;
import pageObjects.HomePage;
import testBase.BaseClass;

import java.util.List;

public class TC_004BooksPageTest extends BaseClass {

    @Test
    public void booksPage() {

        logger.info("===== Starting Books Page Test =====");

        HomePage homePage = new HomePage(driver);

        validatePageMessageHard(
                homePage.getConfirmationMessage(),
                "Book Store Application",
                "HomePage"
        );
        homePage.clickBookStoreApplicationLink();

        BooksPage booksPage = new BooksPage(driver);
        validatePageMessageHard(
                booksPage.getConfirmationMessage(),
                "Book Store",
                "BooksPage"
        );

        // ----------------- All Links -----------------
        List<String> allLinks = booksPage.getAllLinkUrls();
        logger.info("----- All Links on Books Page -----");
        for (String url : allLinks) {
            logger.info(url);
        }

        // ----------------- Broken Links -----------------
        List<String> brokenLinks = booksPage.getBrokenLinks();
        SoftAssert softAssert = new SoftAssert();

        if (brokenLinks.isEmpty()) {
            logger.info("No broken links found on the page.");
        } else {
            logger.warn("----- Broken Links on Books Page -----");
            for (String link : brokenLinks) {
                logger.error(link);
                // Mark as failure but continue
                softAssert.fail("Broken link: " + link);
            }
        }

        // Assert all broken links at the end
        softAssert.assertAll();

        logger.info("===== Books Page Test Completed =====");
    }
}
