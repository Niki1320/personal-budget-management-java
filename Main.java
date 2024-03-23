public class Main {
    public static void main(String[] args) {
        Database db = new Database();

       // Inserting into BANK_ACCOUNTS table
db.insertBankAccount("Savings", 5000.00); // Ensure balance is within 5 digits and 2 decimal places

// Inserting into BUDGET_PLANS table
db.insertBudgetPlan(1000.00, 500.00, 300.00, 100.00, 700.00, 200.00, 400.00); // Ensure values for each category are within 5 digits and 2 decimal places

// Inserting into PURCHASES table
db.insertPurchase(50.00, "Groceries", "2024-03-23 10:00:00", "John", "Bank A", "Food"); // Ensure purchase amount is within 5 digits and 2 decimal places

// Inserting into FIN_GOALS table
db.insertFinancialGoal(10000.00, "2024-12-31", 50); // Ensure financial goal amount is within 5 digits and 2 decimal places

    }
}
