package utilities;

import org.testng.annotations.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegisterDBDataProvider {

    public static Logger logger = LogManager.getLogger(RegisterDBDataProvider.class);

    @DataProvider(name = "RegisterDataDB")
    public Object[][] getRegisterDataFromDB() {
        logger.info("===== Starting DB DataProvider: RegisterDataDB =====");

        List<Object[]> dataList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),
                ConfigReader.getProperty("db_user"),
                ConfigReader.getProperty("db_password"));
             Statement stmt = con.createStatement();
        ) {

            String query = "SELECT fname, lname, uname, password, expected_result " +
                    "FROM register_data WHERE retry_flag='Y' OR retry_flag IS NULL";

            logger.info("Executing query: {}", query);

            ResultSet rs = stmt.executeQuery(query);
            int rowCount = 0;

            while (rs.next()) {
                dataList.add(new Object[]{
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("uname"),
                        rs.getString("password"),
                        rs.getString("expected_result")
                });
                rowCount++;
            }

            rs.close();
            logger.info("Total records fetched from DB: {}", rowCount);
            logger.info("===== Finished DB DataProvider: RegisterDataDB =====");

        } catch (Exception e) {
            logger.error("Error fetching data from DB", e);
            throw new RuntimeException("Error fetching data from DB: " + e.getMessage());
        }

        return dataList.toArray(new Object[0][0]);
    }
}
