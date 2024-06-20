package databasefinal;
public class QueryService {

    public static String getDefectiveTransmissionsQuery(String startDate, String endDate) {
        return "SELECT Car.VIN, Customer.CustomerName " +
               "FROM Car " +
               "JOIN Model ON Car.ModelID = Model.ModelID " +
               "JOIN Brand ON Model.BrandID = Brand.BrandID " +
               "JOIN Transmission ON Car.TransmissionID = Transmission.TransmissionID " +
               "JOIN Inventory ON Car.VIN = Inventory.VIN " +
               "JOIN Factory ON Inventory.DealerID = Factory.FactoryID " +
               "JOIN Supplier ON Factory.SupplierID = Supplier.SupplierID " +
               "JOIN Customer ON Car.CustomerID = Customer.CustomerID " + // 修正此行
               "WHERE Transmission.Defective = 1 " +
               "AND Supplier.SupplierName = 'Getrag' " +
               "AND Car.SaleDate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
               "ORDER BY Car.VIN";
    }

    public static String getTopDealerLastYearQuery() {
        return "SELECT Dealer.DealerName, SUM(Car.SalePrice) AS TotalSales " +
               "FROM Car " +
               "JOIN Dealer ON Car.DealerID = Dealer.DealerID " +
               "WHERE YEAR(Car.SaleDate) = YEAR(CURDATE()) - 1 " +
               "GROUP BY Dealer.DealerName " +
               "ORDER BY TotalSales DESC " +
               "LIMIT 1";
    }

    public static String getTop2BrandsLastYearQuery() {
        return "SELECT Brand.BrandName, COUNT(*) AS SalesCount " +
               "FROM Car " +
               "JOIN Model ON Car.ModelID = Model.ModelID " +
               "JOIN Brand ON Model.BrandID = Brand.BrandID " +
               "WHERE YEAR(Car.SaleDate) = YEAR(CURDATE()) - 1 " +
               "GROUP BY Brand.BrandName " +
               "ORDER BY SalesCount DESC " +
               "LIMIT 2";
    }

    public static String getBestSUVSalesMonthQuery() {
        return "SELECT MONTH(Car.SaleDate) AS SaleMonth, COUNT(*) AS SUVSales " +
               "FROM Car " +
               "JOIN Model ON Car.ModelID = Model.ModelID " +
               "WHERE Model.Type = 'SUV' " +
               "GROUP BY SaleMonth " +
               "ORDER BY SUVSales DESC " +
               "LIMIT 1";
    }

    public static String getDealersWithLongestAverageInventoryTimeQuery() {
        return "SELECT Dealer.DealerName, AVG(DATEDIFF(Car.SaleDate, Inventory.InventoryDate)) AS AvgInventoryTime " +
               "FROM Inventory " +
               "JOIN Car ON Inventory.VIN = Car.VIN " +
               "JOIN Dealer ON Inventory.DealerID = Dealer.DealerID " +
               "GROUP BY Dealer.DealerName " +
               "ORDER BY AvgInventoryTime DESC " +
               "LIMIT 1"; 
    }
}
