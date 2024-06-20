package databasefinal;

import java.sql.*;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mariadb://0.tcp.jp.ngrok.io:12592/411177021";
    private static final String USER = "411177021";
    private static final String PASSWORD = "411177021";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
