package edu.gatech.seclass.jobcompare6300;
import java.io.Serializable;

public class Job implements Serializable {

    private String title;
    private String company;
    private String location;
    private int costOfLiving;
    private double yearlySalary;
    private double yearlyBonus;
    private int stockOptionShares;
    private double wellnessStipend;
    private int lifeInsurancePercentage;
    private double personalDevelopmentFund;
    private boolean isCurrentJob;
    private double adjustedSalary;
    private double adjustedBonus;
    private double lifeInsurance;

    public void setJob(String title, String company, String location, int costOfLiving, double yearlySalary,
               double yearlyBonus, int stockOptionShares, double wellnessStipend,
               int lifeInsurancePercentage, double personalDevelopmentFund) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.costOfLiving = costOfLiving;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.stockOptionShares = stockOptionShares;
        this.wellnessStipend = wellnessStipend;
        this.lifeInsurancePercentage = lifeInsurancePercentage;
        this.personalDevelopmentFund = personalDevelopmentFund;
    }
      
    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public double getCostOfLiving() {
        return costOfLiving;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    public double getYearlyBonus() {
        return yearlyBonus;
    }

    public int getStockOptionShares() {
        return stockOptionShares;
    }

    public double getWellnessStipend() {
        return wellnessStipend;
    }

    public double getPersonalDevelopmentFund() {
        return personalDevelopmentFund;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    public double getAdjustedSalary() {
        return adjustedSalary;
    }

    public void setAdjustedSalary(double adjustedSalary) {
        this.adjustedSalary = adjustedSalary;
    }

    public double getAdjustedBonus() {
        return adjustedBonus;
    }

    public void setAdjustedBonus(double adjustedBonus) {
        this.adjustedBonus = adjustedBonus;
    }

    public double getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(double lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public void computeAdjustedSalary(Job job) {
        adjustedSalary = (job.yearlySalary * 100) / job.costOfLiving;

    }
    public void computeAdjustedBonus(Job job) {
        adjustedBonus = (job.yearlyBonus * 100) / job.costOfLiving;
    }
    public void lifeInsurance(Job job) {
        if (job.lifeInsurancePercentage == 0) {
            lifeInsurance = 0;
        }
        lifeInsurance = ((float) job.lifeInsurancePercentage/ 100) * yearlySalary;
    }
}
