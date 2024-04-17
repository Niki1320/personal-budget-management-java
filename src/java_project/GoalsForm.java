package java_project;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import javax.swing.JProgressBar;

public class GoalsForm extends javax.swing.JFrame {
	
	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	  public static final String DB_NAME = "budgetplanner"; // Change to your database name
	  public static final String DB_USER = "root"; // Change to your MySQL username
	  public static final String DB_PASSWORD = "101478"; // Change to your MySQL password

    //CONSTRUCTOR===============================================================
    public GoalsForm() {
        initComponents();
        lblConfirmation.setVisible(false);
    }

    //GENERATED CODE============================================================
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtemailAddress = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblConfirmation = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTargetDate = new javax.swing.JTextField(); // New text field for target date

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 130));
        setResizable(false);

        jLabel1.setText("Enter amount");

        btnSend.setLabel("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        

        jLabel2.setText("Enter target date (yyyy-MM-dd)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtemailAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblConfirmation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTargetDate, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtemailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTargetDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend)
                    .addComponent(btnBack)
                    .addComponent(lblConfirmation))
                .addContainerGap())
        );

        txtemailAddress.getAccessibleContext().setAccessibleName("");
        lblConfirmation.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try {
            // Connect to the SQLite database
            Connection connection = DriverManager.getConnection(DB_URL + DB_NAME,
                    DB_USER,
                    DB_PASSWORD);
    
            // Fetch the balance from the BANK_ACCOUNTS table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BALANCE FROM BANK_ACCOUNTS WHERE ACCOUNT_NAME = 'Checking'");
    
            double checkingBalance = 0.0;
            if (resultSet.next()) {
                checkingBalance = resultSet.getDouble("BALANCE");
            }
    
            // Get the target date from the text field
            String targetDateStr = txtTargetDate.getText();
            LocalDate targetDate = LocalDate.parse(targetDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    
            // Calculate progress for each goal with the selected target date
            LocalDate currentDate = LocalDate.now();
            long daysRemaining = ChronoUnit.DAYS.between(currentDate, targetDate);
            double goalAmount = Double.parseDouble(txtemailAddress.getText()); // Amount entered by the user
            double dailyTarget = goalAmount / daysRemaining;
            double dailyProgress = checkingBalance - (goalAmount - dailyTarget);
            double goalProgress = (dailyProgress / goalAmount) * 100;
    
            System.out.println("Checking Balance: " + checkingBalance);
            System.out.println("Goal Amount: " + goalAmount);
            System.out.println("Daily Target: " + dailyTarget);
            System.out.println("Daily Progress: " + dailyProgress);
            System.out.println("Goal Progress: " + goalProgress);
    
            // Insert the goal into the FIN_GOALS table
            String insertGoalQuery = "INSERT INTO FIN_GOALS (AMOUNT, TARGET_DATE, PROGRESS) VALUES (?, ?, ?)";
            PreparedStatement insertGoalStatement = connection.prepareStatement(insertGoalQuery);
            insertGoalStatement.setDouble(1, goalAmount); // Set the goal amount
            insertGoalStatement.setString(2, targetDateStr); // Set the target date
            insertGoalStatement.setInt(3, (int) goalProgress); // Set the progress as integer
            insertGoalStatement.executeUpdate();
    
            // Create a progress bar
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true); // Show progress as a percentage
            progressBar.setValue((int) Math.min(goalProgress, 100)); // Limit progress to 100%
    
            // Add the progress bar to the frame
            getContentPane().setLayout(new java.awt.FlowLayout());
            getContentPane().add(progressBar);
    
            // Close the connection
            resultSet.close();
            statement.close();
            insertGoalStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        PurchaseViewerScreen BS = new PurchaseViewerScreen();
        dispose();
        BS.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    

    //JFRAME VARIABLES =========================================================
    // <editor-fold defaultstate="collapsed" desc=" JFrame Variables ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblConfirmation;
    private javax.swing.JTextField txtemailAddress;
    private javax.swing.JTextField txtTargetDate; // New text field for target date
    private javax.swing.JLabel jLabel2; // New label for target date
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
