package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBResultUpdater {

    public static void updateResult(String uname, String actualResult, String testStatus) {
        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),
                ConfigReader.getProperty("db_user"),
                ConfigReader.getProperty("db_password"))) {

            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM register_data WHERE uname = ?");
            checkStmt.setString(1, uname);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE register_data SET actual_result=?, test_status=?, execution_time=NOW() WHERE uname=?");
                ps.setString(1, actualResult);
                ps.setString(2, testStatus);
                ps.setString(3, uname);
                ps.executeUpdate();
                ps.close();
            } else {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO register_data (uname, actual_result, test_status, execution_time) VALUES (?, ?, ?, NOW())");
                ps.setString(1, uname);
                ps.setString(2, actualResult);
                ps.setString(3, testStatus);
                ps.executeUpdate();
                ps.close();
            }

        } catch (Exception e) {
            System.out.println("DB write-back failed for user: " + uname + " | " + e.getMessage());
        }
    }

    public static void updateRetryFlag(String uname, String flag) {
        try (Connection con = DriverManager.getConnection(
                ConfigReader.getProperty("db_url"),
                ConfigReader.getProperty("db_user"),
                ConfigReader.getProperty("db_password"))) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE register_data SET retry_flag=? WHERE uname=?");
            ps.setString(1, flag);
            ps.setString(2, uname);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                PreparedStatement psInsert = con.prepareStatement(
                        "INSERT INTO register_data (uname, retry_flag) VALUES (?, ?)");
                psInsert.setString(1, uname);
                psInsert.setString(2, flag);
                psInsert.executeUpdate();
                psInsert.close();
            }

            ps.close();

        } catch (Exception e) {
            System.out.println("Updating retry flag failed for user: " + uname + " | " + e.getMessage());
        }
    }
}
