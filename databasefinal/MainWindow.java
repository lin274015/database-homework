package databasefinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainWindow extends JFrame {
    private JLabel queryTitleLabel;
    private JTextArea queryResultArea;
    private JComboBox<String> querySelectionBox;

    public MainWindow() {
        setTitle("Database Query Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        queryTitleLabel = new JLabel("Query Results:");
        queryResultArea = new JTextArea(20, 50);
        queryResultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(queryResultArea);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());

        String[] queryOptions = {
            "Top Dealer Last Year",
            "Top 2 Brands Last Year",
            "Best SUV Sales Month",
            "Dealers with Longest Average Inventory Time",
            "VIN and Customer Name"
        };

        querySelectionBox = new JComboBox<>(queryOptions);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(queryTitleLabel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(querySelectionBox);
        bottomPanel.add(searchButton);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedQuery = (String) querySelectionBox.getSelectedItem();
            String query = "";

            if (selectedQuery != null) {
                switch (selectedQuery) {
                    case "Top Dealer Last Year":
                        query = QueryService.getTopDealerLastYearQuery();
                        break;
                    case "Top 2 Brands Last Year":
                        query = QueryService.getTop2BrandsLastYearQuery();
                        break;
                    case "Best SUV Sales Month":
                        query = QueryService.getBestSUVSalesMonthQuery();
                        break;
                    case "Dealers with Longest Average Inventory Time":
                        query = QueryService.getDealersWithLongestAverageInventoryTimeQuery();
                        break;
                    case "VIN and Customer Name":
                        query = "SELECT Car.VIN, Customer.CustomerName FROM Car JOIN Customer ON Car.CustomerID = Customer.CustomerID";
                        break;
                }
                executeQueryAndDisplayResults(query);
            }
        }
    }

    private void executeQueryAndDisplayResults(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder results = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                results.append(metaData.getColumnName(i)).append("\t");
            }
            results.append("\n");

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    results.append(resultSet.getString(i)).append("\t");
                }
                results.append("\n");
            }

            queryResultArea.setText(results.toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            queryResultArea.setText("Error executing query.");
        } finally {
            DatabaseManager.closeResources(resultSet, statement, connection);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
