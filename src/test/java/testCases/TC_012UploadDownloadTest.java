package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.UploadDownloadPage;
import pageObjects.components.ElementsMenuComponent;
import testBase.BaseClass;

import java.io.File;

public class TC_012UploadDownloadTest extends BaseClass {

    @Test
    public void uploadDownloadTest() {

        logger.info("===== Starting Upload & Download Test =====");

        // -------- NAVIGATION --------
        HomePage home = new HomePage(driver);
        home.clickElementsLink();
        logger.info("Clicked Elements link");

        ElementsMenuComponent menu = new ElementsMenuComponent(driver);
        menu.openUploadAndDownload();
        logger.info("Opened Upload & Download page");

        UploadDownloadPage uploadDownloadPage = new UploadDownloadPage(driver);

        // -------- DOWNLOAD TEST --------
        uploadDownloadPage.clickDownloadButton();

        String downloadDir = System.getProperty("user.dir")
                + File.separator + "downloads";
        String downloadedFileName = "sampleFile.jpeg";

        boolean isDownloaded = uploadDownloadPage.waitForFileDownload(
                downloadDir,
                downloadedFileName,
                10
        );

        Assert.assertTrue(isDownloaded, "Downloaded file was not found");
        logger.info("Download validation passed");

        // -------- UPLOAD TEST --------
        String uploadFilePath = System.getProperty("user.dir")
                + File.separator + "testData"
                + File.separator + "sampleUpload.txt";

        uploadDownloadPage.uploadFile(uploadFilePath);

        String uploadedText = uploadDownloadPage.waitForUploadedFileText();
        Assert.assertTrue(
                uploadedText.contains("sampleUpload.txt"),
                "Uploaded file name not displayed correctly"
        );

        logger.info("Upload validation passed");
        logger.info("===== Upload & Download Test Completed Successfully =====");
    }
}
