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

             /*
             Create a SQL query to count how many rows exist with this username
             PreparedStatement is used to safely run SQL queries.
             COUNT(*) means: count how many rows match the condition.
             ? is a placeholder for the username value.
              */

            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM register_data WHERE uname = ?");

            // Replace the '?' placeholder with the actual username
            checkStmt.setString(1, uname);

            // Execute the query and store the result
            ResultSet rs = checkStmt.executeQuery();

            //ResultSet cursor starts before the first row.
            //rs.next() moves it to the first row.
            rs.next();

            // Read the value from the first column (COUNT(*))
            //Get the value from column 1
            int count = rs.getInt(1);

            if (count > 0) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE register_data SET actual_result=?, test_status=?, execution_time=NOW() WHERE uname=?");

                //setString() is used to put a String value into a SQL query placeholder (?).
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

            // 1️⃣ Check how many rows exist with this username
            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM register_data WHERE uname = ?"
            );
            checkStmt.setString(1, uname);

            ResultSet rs = checkStmt.executeQuery();
            rs.next(); // move to first row

            int count = rs.getInt(1); // number of rows with this username

            // 2️⃣ If record exists → UPDATE
            if (count > 0) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE register_data SET retry_flag=? WHERE uname=?"
                );
                ps.setString(1, flag);
                ps.setString(2, uname);
                ps.executeUpdate();
                ps.close();
            }
            // 3️⃣ If record does not exist → INSERT
            else {
                PreparedStatement psInsert = con.prepareStatement(
                        "INSERT INTO register_data (uname, retry_flag) VALUES (?, ?)"
                );
                psInsert.setString(1, uname);
                psInsert.setString(2, flag);
                psInsert.executeUpdate();
                psInsert.close();
            }

        } catch (Exception e) {
            System.out.println("Updating retry flag failed for user: " + uname + " | " + e.getMessage());
        }
    }

}
