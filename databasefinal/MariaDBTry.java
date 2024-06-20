package databasefinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBTry {

    public static void main(String[] args) {

        String driver = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://0.tcp.jp.ngrok.io:11051/411177021";
        String user = "411177021";
        String password = "411177021";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MariaDB Connector/J driver
            Class.forName(driver);
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Success: Connected to the database!");

            // Create a statement
            statement = connection.createStatement();

            // Execute the query
            String query = "SELECT Dealer.DealerName, SUM(Car.SalePrice) AS TotalSales " +
                           "FROM Car " +
                           "JOIN Dealer ON Car.DealerID = Dealer.DealerID " +
                           "WHERE YEAR(Car.SaleDate) = YEAR(CURDATE()) - 1 " +
                           "GROUP BY Dealer.DealerName " +
                           "ORDER BY TotalSales DESC " +
                           "LIMIT 1";
            resultSet = statement.executeQuery(query);

            // Process the result set
            System.out.println("Dealer with the highest sales last year:");
            while (resultSet.next()) {
                String dealerName = resultSet.getString("DealerName");
                double totalSales = resultSet.getDouble("TotalSales");
                System.out.println("Dealer Name: " + dealerName + ", Total Sales: " + totalSales);
            }
            System.out.println();

        } catch (ClassNotFoundException e) {
            System.err.println("Error: MariaDB JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Connection failed or SQL error!");
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
