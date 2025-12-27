package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

/**
 * Data provider class for supplying Register data from an Excel file.
 */
public class RegisterDataProvider {

    // Logger instance for logging
    public static final Logger logger = LogManager.getLogger(RegisterDataProvider.class);

    /**
     * Provides Register test data from Excel.
     * @return 2D Object array with Register credentials and expected results
     * @throws Exception if Excel file reading fails
     */

    @DataProvider(name = "RegisterData")
    public static Object[][] getRegisterData() throws Exception {
        String excelPath = ConfigReader.getProperty("excel_User_Register_Path");
        String sheetName = ConfigReader.getProperty("register_sheet_name");

        System.out.println("Reading Register data from Excel: " + excelPath + ", Sheet: " + sheetName);
        logger.info("Reading Register data from Excel: {}, Sheet: {}", excelPath, sheetName);

        ExcelUtils excel = null;
        Object[][] data = null;

        try {
            // Load Excel data
            excel = new ExcelUtils(excelPath, sheetName);
            int rowCount = excel.getRowCount();

            data = new Object[rowCount - 1][6]; // 6 columns: fname, lname, uname, password, expectedResult, rowIndex

            // Read data row by row (starting from row 1 to skip header)
            for (int i = 1; i < rowCount; i++) {
                data[i - 1][0] = excel.getCellData(i, 0); // FirstName
                data[i - 1][1] = excel.getCellData(i, 1); // LastName
                data[i - 1][2] = excel.getCellData(i, 2); // Username
                data[i - 1][3] = excel.getCellData(i, 3); // Password
                data[i - 1][4] = excel.getCellData(i, 4); // ExpectedResult
                data[i - 1][5] = i;                       // RowIndex (optional for tracking)

                logger.info("Loaded Row {} -> UserName: {}, ExpectedResult: {}", i, data[i - 1][2], data[i - 1][4]);

            }

        } catch (Exception e) {
            System.out.println("Error reading Register data: " + e.getMessage());
            logger.error("Failed to read Register data from Excel", e);
            throw e;
        } finally {
            // Close workbook to release file resources
            if (excel != null) {
                excel.closeWorkbook();
                System.out.println("Excel workbook closed.");
                logger.info("Excel workbook closed.");
            }
        }
        return data;
    }

}
