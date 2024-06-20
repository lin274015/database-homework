package databasefinal;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class SchemaUpdater {

    public static void updateSchema() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();

            // Drop tables if they exist
            statement.executeUpdate("DROP TABLE IF EXISTS Inventory");
            statement.executeUpdate("DROP TABLE IF EXISTS Car");
            statement.executeUpdate("DROP TABLE IF EXISTS Transmission");
            statement.executeUpdate("DROP TABLE IF EXISTS Part");
            statement.executeUpdate("DROP TABLE IF EXISTS Factory");
            statement.executeUpdate("DROP TABLE IF EXISTS Supplier");
            statement.executeUpdate("DROP TABLE IF EXISTS Customer");
            statement.executeUpdate("DROP TABLE IF EXISTS Dealer");
            statement.executeUpdate("DROP TABLE IF EXISTS Model");
            statement.executeUpdate("DROP TABLE IF EXISTS Brand");
            statement.executeUpdate("DROP TABLE IF EXISTS Company");

            // Create tables
            statement.executeUpdate("CREATE TABLE Company (CompanyID INT PRIMARY KEY, CompanyName VARCHAR(100))");
            statement.executeUpdate("CREATE TABLE Brand (BrandID INT PRIMARY KEY, BrandName VARCHAR(100), CompanyID INT, FOREIGN KEY (CompanyID) REFERENCES Company(CompanyID))");
            statement.executeUpdate("CREATE TABLE Model (ModelID INT PRIMARY KEY, ModelName VARCHAR(100), BrandID INT, Type VARCHAR(100), FOREIGN KEY (BrandID) REFERENCES Brand(BrandID))");
            statement.executeUpdate("CREATE TABLE Dealer (DealerID INT PRIMARY KEY, DealerName VARCHAR(100))");
            statement.executeUpdate("CREATE TABLE Supplier (SupplierID INT PRIMARY KEY, SupplierName VARCHAR(100))");
            statement.executeUpdate("CREATE TABLE Factory (FactoryID INT PRIMARY KEY, FactoryName VARCHAR(100), SupplierID INT, FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID))");
            statement.executeUpdate("CREATE TABLE Part (PartID INT PRIMARY KEY, PartName VARCHAR(100), SupplierID INT, FOREIGN KEY (SupplierID) REFERENCES Supplier(SupplierID))");
            statement.executeUpdate("CREATE TABLE Transmission (TransmissionID INT PRIMARY KEY, TransmissionType VARCHAR(100), Defective BOOLEAN)");
            statement.executeUpdate("CREATE TABLE Customer (CustomerID INT PRIMARY KEY, CustomerName VARCHAR(100))");
            statement.executeUpdate("CREATE TABLE Car (VIN VARCHAR(100) PRIMARY KEY, ModelID INT, DealerID INT, TransmissionID INT, SaleDate DATE, SalePrice DECIMAL(10, 2), CustomerID INT, FOREIGN KEY (ModelID) REFERENCES Model(ModelID), FOREIGN KEY (DealerID) REFERENCES Dealer(DealerID), FOREIGN KEY (TransmissionID) REFERENCES Transmission(TransmissionID), FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID))");
            statement.executeUpdate("CREATE TABLE Inventory (InventoryID INT PRIMARY KEY AUTO_INCREMENT, VIN VARCHAR(100), DealerID INT, InventoryDate DATE, FOREIGN KEY (VIN) REFERENCES Car(VIN), FOREIGN KEY (DealerID) REFERENCES Dealer(DealerID))");

            System.out.println("Schema updated successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public static void main(String[] args) {
        updateSchema();
    }
}