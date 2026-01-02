package utilities;

// TestNG DataProvider annotation
import org.testng.annotations.DataProvider;

// Log4j imports for logging execution details
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// JDBC imports for database connectivity
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Collections to store DB data dynamically
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides registration test data from a database
 * using TestNG DataProvider mechanism.
 */
public class RegisterDBDataProvider {

    // Logger instance for logging info and error messages
    public static Logger logger = LogManager.getLogger(RegisterDBDataProvider.class);

    /**
     * TestNG DataProvider method that fetches registration data from DB
     * and returns it in Object[][] format for test execution.
     */
    @DataProvider(name = "RegisterDataDB")
    public Object[][] getRegisterDataFromDB() {

        // Log the start of DataProvider execution
        logger.info("===== Starting DB DataProvider: RegisterDataDB =====");

        // List to hold each row of test data fetched from the database
        List<Object[]> dataList = new ArrayList<>();

        // Try-with-resources ensures DB connection and statement are closed automatically
        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),      // Database URL
                ConfigReader.getProperty("db_user"),     // Database username
                ConfigReader.getProperty("db_password"));// Database password
             Statement stmt = con.createStatement();     // SQL statement object
        ) {

            // SQL query to fetch registration data eligible for execution
            String query = "SELECT fname, lname, uname, password, expected_result " +
                    "FROM register_data WHERE retry_flag='Y' OR retry_flag IS NULL";

            // Log the query being executed
            logger.info("Executing query: {}", query);

            // Execute query and store results in ResultSet
            ResultSet rs = stmt.executeQuery(query);

            int rowCount = 0;

            // Iterate through each record returned by the query
            while (rs.next()) {

                // Add each row as an Object array (one test case)
                dataList.add(new Object[]{
                        rs.getString("fname"),            // First name
                        rs.getString("lname"),            // Last name
                        rs.getString("uname"),            // Username
                        rs.getString("password"),         // Password
                        rs.getString("expected_result")   // Expected test outcome
                });

                rowCount++;
            }

            // Log the total number of records fetched
            logger.info("Total records fetched from DB: {}", rowCount);

            // Close ResultSet explicitly
            rs.close();

            // Log completion of DataProvider execution
            logger.info("===== Finished DB DataProvider: RegisterDataDB =====");

        } catch (Exception e) {
            // Log any exception encountered while fetching data
            logger.error("Error fetching data from DB", e);

            // Fail the test execution if DB access fails
            throw new RuntimeException("Error fetching data from DB: " + e.getMessage());
        }

        // Convert List<Object[]> to Object[][] as required by TestNG DataProvider
        return dataList.toArray(new Object[0][0]);
    }
}
