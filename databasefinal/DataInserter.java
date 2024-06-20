package databasefinal;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DataInserter {

    public static void insertSampleData() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();

            // Insert data into Company table
            statement.executeUpdate("INSERT INTO Company (CompanyID, CompanyName) VALUES (1, 'Toyota'), (2, 'Honda')");

            // Insert data into Customer table
            statement.executeUpdate("INSERT INTO Customer (CustomerID, CustomerName) VALUES (1, 'Alice'), (2, 'Bob'), (3, 'Charlie'), (4, 'David'), (5, 'Eve')");

            // Insert data into Dealer table
            statement.executeUpdate("INSERT INTO Dealer (DealerID, DealerName) VALUES (1, 'Dealer1'), (2, 'Dealer2')");

            // Insert data into Factory table
            statement.executeUpdate("INSERT INTO Factory (FactoryID, FactoryName) VALUES (1, 'Factory1'), (2, 'Factory2')");

            // Insert data into Brand table
            statement.executeUpdate("INSERT INTO Brand (BrandID, BrandName, CompanyID) VALUES (1, 'Toyota', 1), (2, 'Honda', 2)");

            // Insert data into Supplier table
            statement.executeUpdate("INSERT INTO Supplier (SupplierID, SupplierName) VALUES (1, 'Getrag'), (2, 'ZF')");

            // Insert data into Transmission table
            statement.executeUpdate("INSERT INTO Transmission (TransmissionID, TransmissionType, Defective) VALUES (1, 'Manual', 1), (2, 'Automatic', 0)");

            // Insert data into Model table
            statement.executeUpdate("INSERT INTO Model (ModelID, ModelName, Type, BrandID) VALUES (1, 'Corolla', 'Sedan', 1), (2, 'Civic', 'Sedan', 2), (3, 'RAV4', 'SUV', 1), (4, 'CR-V', 'SUV', 2), (5, 'Camry', 'Sedan', 1)");

            // Insert data into Car table
            statement.executeUpdate("INSERT INTO Car (VIN, ModelID, CustomerID, TransmissionID, DealerID, SalePrice, SaleDate) VALUES ('1A', 1, 1, 1, 1, 20000, '2023-05-01'), ('1B', 2, 2, 2, 2, 22000, '2023-06-01'), ('1C', 3, 3, 1, 1, 30000, '2023-07-01'), ('1D', 4, 4, 2, 2, 32000, '2023-08-01'), ('1E', 5, 5, 1, 1, 25000, '2023-09-01')");

            // Insert data into Inventory table
            statement.executeUpdate("INSERT INTO Inventory (VIN, DealerID, InventoryDate) VALUES ('1A', 1, '2023-01-01'), ('1B', 2, '2023-02-01'), ('1C', 1, '2023-03-01'), ('1D', 2, '2023-04-01'), ('1E', 1, '2023-05-01')");

            System.out.println("Sample data inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
