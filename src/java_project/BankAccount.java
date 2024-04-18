package java_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {
    private List<BudgetObserver> observers = new ArrayList<>();
    private String accountName;
    private Double balance = 0.00;
    private static final long serialVersionUID = -1953064694003917513L;


    public void addObserver(BudgetObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (BudgetObserver observer : observers) {
            observer.updateBudget();
        }
    }
    //GET AND SETS =============================================================
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String xacctName) {
        accountName = xacctName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double xbalance) {
        balance = xbalance;
    }
    // </editor-fold>
}
