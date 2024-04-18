package java_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetPlan implements Serializable {
	private transient List<BudgetObserver> observers;
    private double homeExpenses;
    private double transportation;
    private double health;
    private double charity;
    private double dailyLiving;
    private double entertainment;
    private double financial;
    private static final long serialVersionUID = -3906633615602061346L;
    
    public BudgetPlan() {
        observers = new ArrayList<>();
    }


    public void addObserver(BudgetObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    private void notifyObservers() {
        for (BudgetObserver observer : observers) {
            observer.updateBudget();
        }
    }

    public void setHomeExpenses(double he) {
        homeExpenses = he;
        notifyObservers();
    }

    public double getHomeExpenses() {
        return homeExpenses;
    }

    public void setTransportation(double tp) {
        transportation = tp;
    }

    public double getTransportation() {
        return transportation;
    }

    public void setHealth(double ha) {
        health = ha;
    }

    public double getHealth() {
        return health;
    }

    public void setCharity(double ch) {
        charity = ch;
    }

    public double getCharity() {
        return charity;
    }

    public void setDailyLiving(double dl) {
        dailyLiving = dl;
    }

    public double getDailyLiving() {
        return dailyLiving;
    }

    public void setEntertainment(double et) {
        entertainment = et;
    }

    public double getEntertainment() {
        return entertainment;
    }

    public void setFinancial(double fn) {
        financial = fn;
    }

    public double getFinancial() {
        return financial;
    }
    // </editor-fold>
}
