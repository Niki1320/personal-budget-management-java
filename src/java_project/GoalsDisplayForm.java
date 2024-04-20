package java_project;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GoalsDisplayForm extends javax.swing.JFrame {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	  public static final String DB_NAME = "budgetplanner"; // Change to your database name
	  public static final String DB_USER = "root"; // Change to your MySQL username
	  public static final String DB_PASSWORD = "niki123"; // Change to your MySQL password

    private javax.swing.JButton btnAddGoal;
    private javax.swing.JButton btnDeleteGoal;
    JButton btnBack = new JButton("Back");
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGoals;
    private javax.swing.table.DefaultTableModel tableModel;
    

    public GoalsDisplayForm() {
        initComponents();
        loadGoals();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
    tblGoals = new javax.swing.JTable();
    btnAddGoal = new javax.swing.JButton();
    btnDeleteGoal = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Goals Display");

    tableModel = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Amount", "Target Date", "Progress"}
    ) {
        Class[] types = new Class[]{String.class, String.class, JProgressBar.class};
        boolean[] canEdit = new boolean[]{false, false, false};

        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };

    tblGoals.setModel(tableModel);
    jScrollPane1.setViewportView(tblGoals);

    btnAddGoal.setText("Add Goal");
    btnAddGoal.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            btnAddGoalActionPerformed(evt);
        }
    });

    btnDeleteGoal.setText("Delete Selected Goal");
    btnDeleteGoal.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            btnDeleteGoalActionPerformed(evt);
        }
    });

    btnBack.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnBackActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnAddGoal)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnDeleteGoal)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnBack)
                                    )
                            )
                            .addContainerGap()
                    )
    );
    layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAddGoal)
                                    .addComponent(btnDeleteGoal)
                                    .addComponent(btnBack)
                            )
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    )
    );

    
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
    

    private void loadGoals() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(DB_URL + DB_NAME,
                    DB_USER,
                    DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FIN_GOALS");

            while (resultSet.next()) {
                double amount = resultSet.getDouble("AMOUNT");
                String targetDate = resultSet.getString("TARGET_DATE");
                int progress = resultSet.getInt("PROGRESS");

                // Create a string representation of the progress bar
                String progressBarString = progress + "%";

                tableModel.addRow(new Object[]{amount, targetDate, progressBarString});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void btnAddGoalActionPerformed(ActionEvent evt) {
        GoalsForm EmailForm = new GoalsForm();
        dispose();
        EmailForm.setVisible(true);
    }

    

    private void btnDeleteGoalActionPerformed(ActionEvent evt) {
        int selectedRow = tblGoals.getSelectedRow();
        if (selectedRow != -1) {
            String goalDate = (String) tableModel.getValueAt(selectedRow, 1);

            try {

            Connection connection = DriverManager.getConnection(DB_URL + DB_NAME,
                    DB_USER,
                    DB_PASSWORD);
            
                String query = "DELETE FROM FIN_GOALS WHERE TARGET_DATE = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, goalDate);
                preparedStatement.executeUpdate();

                tableModel.removeRow(selectedRow);

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // PurchaseViewerScreen BS = new PurchaseViewerScreen();
        // dispose();
        PurchaseViewerScreen BS = PurchaseViewerScreen.getInstance();
    dispose();
        BS.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoalsDisplayForm().setVisible(true);
            }
        });
    }
}
