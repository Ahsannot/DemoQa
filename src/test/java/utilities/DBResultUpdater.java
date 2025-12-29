package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBResultUpdater {

    public static void updateResult(String uname, String actualResult, String testStatus) {
        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),
                ConfigReader.getProperty("db_user"),
                ConfigReader.getProperty("db_password"));
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE register_data SET actual_result=?, test_status=?, execution_time=NOW() WHERE uname=?")
        ) {
            ps.setString(1, actualResult);
            ps.setString(2, testStatus);
            ps.setString(3, uname);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("DB write-back failed for user: " + uname + " | " + e.getMessage());
        }
    }

    public static void updateRetryFlag(String uname, String flag) {
        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),
                ConfigReader.getProperty("db_user"),
                ConfigReader.getProperty("db_password"));
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE register_data SET retry_flag=? WHERE uname=?")
        ) {
            ps.setString(1, flag);
            ps.setString(2, uname);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Updating retry flag failed for user: " + uname + " | " + e.getMessage());
        }
    }
}
