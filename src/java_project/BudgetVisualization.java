// package java_project;
// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.PiePlot;
// import org.jfree.data.general.DefaultPieDataset;
// import org.jfree.ui.ApplicationFrame;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.HashMap;
// import java.util.Map;


// public class BudgetVisualization extends ApplicationFrame {
	
// 	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
// 	public static final String DB_NAME = "budgetplanner"; // Change to your database name
// 	public static final String DB_USER = "root"; // Change to your MySQL username
// 	public static final String DB_PASSWORD = "niki123"; // Change to your MySQL password

//     private Map<String, Double> categoryMap;

//     public BudgetVisualization(String title) {
//         super(title);
//         this.categoryMap = getCategoryDataFromDatabase();
//         setContentPane(createDemoPanel());
//         setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
//         setVisible(true); // Show the frame
//     }

//     private JPanel createDemoPanel() {
//         DefaultPieDataset dataset = new DefaultPieDataset();
//         for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
//             dataset.setValue(entry.getKey(), entry.getValue());
//         }
//         JFreeChart chart = ChartFactory.createPieChart(
//                 "Budget Overview",  // chart title
//                 dataset,             // data
//                 true,                // include legend
//                 true,
//                 false
//         );

//         // Customize chart
//         PiePlot plot = (PiePlot) chart.getPlot();
//         plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//         plot.setNoDataMessage("No data available");
//         plot.setCircular(true);
//         plot.setLabelGap(0.02);

//         JPanel panel = new JPanel(new BorderLayout());
//         panel.add(new ChartPanel(chart), BorderLayout.CENTER);

//         JButton backButton = new JButton("Back");
//         backButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 dispose();
//                 new PurchaseViewerScreen().setVisible(true);
//             }
//         });
//         panel.add(backButton, BorderLayout.SOUTH);

//         return panel;
//     }

//     private Map<String, Double> getCategoryDataFromDatabase() {
//         Map<String, Double> categoryData = new HashMap<>();

//         try {
//             Connection connection = DriverManager.getConnection(DB_URL + DB_NAME,
//                     DB_USER,
//                     DB_PASSWORD);
//             String query = "SELECT CATEGORY, SUM(AMOUNT) FROM PURCHASES GROUP BY CATEGORY";
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             ResultSet resultSet = preparedStatement.executeQuery();

//             while (resultSet.next()) {
//                 String category = resultSet.getString("CATEGORY");
//                 Double totalAmount = resultSet.getDouble(2);
//                 categoryData.put(category, totalAmount);
//             }

//             resultSet.close();
//             preparedStatement.close();
//             connection.close();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return categoryData;
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             BudgetVisualization demo = new BudgetVisualization("Budget Visualization");
//             demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         });
//     }
// }

package java_project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BudgetVisualization extends ApplicationFrame {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "budgetplanner"; // Change to your database name
    public static final String DB_USER = "root"; // Change to your MySQL username
    public static final String DB_PASSWORD = "niki123"; // Change to your MySQL password

    private Map<String, Double> categoryMap;

    public BudgetVisualization(String title) {
        super(title);
        this.categoryMap = getCategoryDataFromDatabase();
        setContentPane(createDemoPanel());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
        setVisible(true); // Show the frame
    }

    private JPanel createDemoPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Budget Overview",  // chart title
                dataset,             // data
                true,                // include legend
                true,
                false
        );
    
        // Customize chart
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setLabelGap(0.02);
    
        // Set the pie plot to be circular
        plot.setCircular(true);
    
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PurchaseViewerScreen().setVisible(true);
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);
    
        return panel;
    }
    

    private Map<String, Double> getCategoryDataFromDatabase() {
        Map<String, Double> categoryData = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL + DB_NAME,
                    DB_USER,
                    DB_PASSWORD);
            String query = "SELECT CATEGORY, SUM(AMOUNT) FROM PURCHASES GROUP BY CATEGORY";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("CATEGORY");
                Double totalAmount = resultSet.getDouble(2);
                categoryData.put(category, totalAmount);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryData;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BudgetVisualization demo = new BudgetVisualization("Budget Visualization");
            demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}

