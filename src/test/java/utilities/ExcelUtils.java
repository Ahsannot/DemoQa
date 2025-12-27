package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public Logger logger ;

    // Public variables for working with Excel files
    public String filePath;
    public String sheetName;
    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public Workbook workbook;
    public Sheet sheet;

    /**
     * Constructor - Loads an Excel file and accesses the specified sheet.
     *
     * @param filePath   Path to the Excel file
     * @param sheetName  Name of the sheet to be accessed
     * @throws Exception If file not found or sheet is missing
     */

    public ExcelUtils(String filePath, String sheetName) throws Exception {

        logger = LogManager.getLogger(ExcelUtils.class);

        this.filePath = filePath;
        this.sheetName = sheetName;

        try {
            System.out.println("Opening Excel file: " + filePath);
            logger.info("Opening Excel file: {}", filePath);

            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in workbook.");
            }

            System.out.println("Loaded sheet: " + sheetName);
            logger.info("Loaded sheet: {}", sheetName);

        } catch (IOException e) {
            logger.error("Error opening Excel file", e);
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    /**
     * Gets the number of non-empty rows in the sheet.
     *
     * @return Total row count
     */
    public int getRowCount() {
        int count = sheet.getPhysicalNumberOfRows();
        System.out.println("Row count: " + count);
        logger.info("Row count retrieved: {}", count);
        return count;
    }

    /**
     * Reads data from a specific cell in the Excel sheet.
     *
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @return Cell data as a string, or empty string if blank
     */
    public String getCellData(int rowNum, int colNum) {
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) return "";

            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
//                case BOOLEAN:
//                    return String.valueOf(cell.getBooleanCellValue());
//                case FORMULA:
//                    return cell.getCellFormula();
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println("Error reading cell at row " + rowNum + ", column " + colNum);
            logger.error("Error reading cell [{}][{}]", rowNum, colNum, e);
            return "";
        }
    }


    /**
     * Writes data to a specific cell in the Excel sheet.
     *
     * @param value   The value to write
     * @param rowNum  Row number (0-based)
     * @param colNum  Column number (0-based)
     * @throws Exception If file write fails
     */
    public void setCellData(String value, int rowNum, int colNum) throws Exception {
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(value);

            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);

            fileOutputStream.close();

            System.out.println("Written data to cell [" + rowNum + "][" + colNum + "]: " + value);
            logger.info("Written data to Excel [{}][{}]: {}", rowNum, colNum, value);

        } catch (Exception e) {
            System.out.println("Failed to write data to Excel file.");
            logger.error("Failed to write data to Excel file", e);
            throw e;
        }
    }


    /**
     * Closes the workbook and frees system resources.
     *
     * @throws IOException If closing the workbook fails
     */
    public void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close();
            System.out.println("Workbook closed.");
            logger.info("Workbook closed.");
        }
    }

}
