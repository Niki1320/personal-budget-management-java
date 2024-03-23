// package c325_project;

import java.sql.*;
import java.util.ArrayList;

public class Database {

  // DEFINE DATABASE FILE PATH ================================================
  public static final String DB_URL = "jdbc:mysql://localhost:3306/";
  public static final String DB_NAME = "new_database"; // Change to your database name
  public static final String DB_USER = "root"; // Change to your MySQL username
  public static final String DB_PASSWORD = "password123"; // Change to your MySQL password

  // DEFINE DATABASE ==========================================================
  // <editor-fold defaultstate="collapsed" desc=" Datebase Definition Code ">
  String tableBANK_ACCOUNTS =
    "CREATE TABLE IF NOT EXISTS BANK_ACCOUNTS (\n" +
    "ACCOUNT_NAME VARCHAR(20),\n" +
    "BALANCE DECIMAL(10,2));"; // Modified precision to (10,2)

String tableBUDGET_PLANS =
    "CREATE TABLE IF NOT EXISTS BUDGET_PLANS (\n" +
    "HOME_EXPENSES DECIMAL(8,2),\n" +  // Modify the data type here
    "TRANSPORTATION DECIMAL(5,2),\n" +
    "HEALTH DECIMAL(5,2),\n" +
    "CHARITY DECIMAL(5,2),\n" +
    "DAILY_LIVING DECIMAL(5,2),\n" +
    "ENTERTAINMENT DECIMAL(5,2),\n" +
    "FINANCIAL DECIMAL(5,2));";

  String tablePURCHASES =
    "CREATE TABLE IF NOT EXISTS PURCHASES (\n" +
    "AMOUNT DECIMAL(10,2),\n" + // Modified precision to (10,2)
    "DESCRIPTION VARCHAR(100),\n" +
    "DATETIME DATETIME,\n" +
    "NAME VARCHAR(20),\n" +
    "BANK VARCHAR(20),\n" +
    "CATEGORY VARCHAR(20));";

  String tableFIN_GOALS =
    "CREATE TABLE IF NOT EXISTS FIN_GOALS (\n" +
    "AMOUNT DECIMAL(10,2),\n" + // Modified precision to (10,2)
    "TARGET_DATE DATE,\n" +
    "PROGRESS INTEGER\n" +
    ");";

  // INSERT INTO BANK_ACCOUNTS TABLE ========================================
  public void insertBankAccount(String accountName, double balance) {
    String values = "'" + accountName + "', " + balance;
    InsertStatement("BANK_ACCOUNTS", values);
  }

  // INSERT INTO BUDGET_PLANS TABLE =========================================
  public void insertBudgetPlan(
    double homeExpenses,
    double transportation,
    double health,
    double charity,
    double dailyLiving,
    double entertainment,
    double financial
  ) {
    String values =
      homeExpenses +
      ", " +
      transportation +
      ", " +
      health +
      ", " +
      charity +
      ", " +
      dailyLiving +
      ", " +
      entertainment +
      ", " +
      financial;
    InsertStatement("BUDGET_PLANS", values);
  }

  // INSERT INTO PURCHASES TABLE ============================================
  public void insertPurchase(
    double amount,
    String description,
    String datetime,
    String name,
    String bank,
    String category
  ) {
    String values =
      amount +
      ", '" +
      description +
      "', '" +
      datetime +
      "', '" +
      name +
      "', '" +
      bank +
      "', '" +
      category +
      "'";
    InsertStatement("PURCHASES", values);
  }

  // INSERT INTO FIN_GOALS TABLE ============================================
  public void insertFinancialGoal(
    double amount,
    String targetDate,
    int progress
  ) {
    String values = amount + ", '" + targetDate + "', " + progress;
    InsertStatement("FIN_GOALS", values);
  }

  // </editor-fold>

  // CONSTRUCTOR ==============================================================
  public Database() {
    //Test connection to database
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      )
    ) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("Connected to MySQL database");
      }

      //Create Statement Variable
      Statement stmt = conn.createStatement();

      //Create all needed tables
      stmt.execute(tableBANK_ACCOUNTS);
      stmt.execute(tableBUDGET_PLANS);
      stmt.execute(tablePURCHASES);
      stmt.execute(tableFIN_GOALS);

      //Debug Code
      System.out.println("All tables already existed or were created.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  // TAKE STRING INPUT AND INSERT INTO TABLE ==================================
  public void InsertStatement(String table, String values) {
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      )
    ) {
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO " + table + " VALUES (" + values + ")");
      System.out.println(
        "Statement executed successfully: INSERT INTO " +
        table +
        " VALUES (" +
        values +
        ")"
      );
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.out.println(
        "The following statement failed: INSERT INTO " +
        table +
        " VALUES (" +
        values +
        ")"
      );
    }
  }

  // TAKE STRING INPUT AS A SQL QUERY STATEMENT ===============================
  public void GenericStatement(String statement) {
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      )
    ) {
      Statement stmt = conn.createStatement();
      stmt.execute(statement);
      System.out.println("Statement executed successfully: " + statement);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println("The following statement failed: " + statement);
    }
  }

  // SELECT STATEMENT =========================================================
  public ArrayList<String> SelectPurchase(String statement) {
    //Set the result String Arrray List Variable
    ArrayList<String> Results = new ArrayList<String>();

    //Connect to the database and inject the SQL Statement
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(statement)
    ) {
      //Debug
      System.out.println("Statement executed successfully: " + statement);

      //Iterate through the Result Set and add to the String Array List
      while (rs.next()) {
        Results.add(
          rs.getString("DATETIME") +
          "\t" +
          rs.getString("NAME") +
          "\t" +
          rs.getDouble("AMOUNT") +
          "\t" +
          rs.getString("BANK") +
          "\t" +
          rs.getString("CATEGORY") +
          "\t" +
          rs.getString("DESCRIPTION")
        );
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println("The following statement failed: " + statement);
    }

    return Results;
  }

  // RETRIEVES AMOUNT OF PURCHASES FOR SPECIFIC CATEGORIES ===================
  public double SumTableCategories(String categoryName) {
    //Set variables
    double Result = 0;

    //Connect to the Database and inject the SQL Statement with the categoryName parameter
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
        "SELECT SUM(AMOUNT) FROM PURCHASES WHERE CATEGORY= '" +
        categoryName +
        "';"
      )
    ) {
      //Loop through the Result Set
      while (rs.next()) {
        Result = rs.getDouble("SUM(AMOUNT)");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return Result;
  }

  // RETRIEVES TOTAL AMOUNT OF PURCHASES ======================================
  public double SumTotal() {
    //Set variables
    double Result = 0;

    //Connect to the Database
    try (
      Connection conn = DriverManager.getConnection(
        DB_URL + DB_NAME,
        DB_USER,
        DB_PASSWORD
      );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT SUM(AMOUNT) FROM PURCHASES;")
    ) {
      //Loop through the Result Set
      while (rs.next()) {
        Result = rs.getDouble("SUM(AMOUNT)");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return Result;
  }
}
