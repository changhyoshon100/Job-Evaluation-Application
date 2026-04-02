package edu.gatech.seclass.jobcompare6300;


import android.widget.EditText;

public class ComparisonSettings {

    private int salaryWeight = 1;
    private int bonusWeight = 1;
    private int stockWeight = 1;
    private int stipendWeight = 1;
    private int insuranceWeight = 1;
    private int pdfWeight = 1;

    public int getSalaryWeight() {
        return salaryWeight;
    }

    public int getBonusWeight() {
        return bonusWeight;
    }

    public int getStockWeight() {
        return stockWeight;
    }

    public int getStipendWeight() {
        return stipendWeight;
    }

    public int getInsuranceWeight() {
        return insuranceWeight;
    }

    public int getPdfWeight() {
        return pdfWeight;
    }

    public void setToDefault() {
        salaryWeight = 1;
        bonusWeight = 1;
        stockWeight = 1;
        stipendWeight = 1;
        insuranceWeight = 1;
        pdfWeight = 1;
    }

    public int getTotalWeight() {
        return salaryWeight + bonusWeight + stockWeight + stipendWeight
                + insuranceWeight + pdfWeight;
    }

    public void setWeights(int salary,
                           int bonus,
                           int stock,
                           int stipend,
                           int insurance,
                           int pdf) {
        this.salaryWeight = salary;
        this.bonusWeight = bonus;
        this.stockWeight = stock;
        this.stipendWeight = stipend;
        this.insuranceWeight = insurance;
        this.pdfWeight = pdf;
    }
}
