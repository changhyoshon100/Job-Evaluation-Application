package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class JobScorer {

    private JobComparisonDbHelper helper;

    public JobScorer(Context context) {
        helper = new JobComparisonDbHelper(context);
    }

    /**
     *
     * @param job
     * @return jobScore
     *
     *      calculates the job score. Called by the jobRankingService.
     *
     *      1. creates the job detail variables
     *      2. creates comparison settings object from comparisonsettings db
     *      3. calculates job score based on formula
     */
    public double computeScore(Job job) { //, ComparisonSettings settings) {
        double AYS = job.getAdjustedSalary();
        double AYB = job.getAdjustedBonus();
        int SOS = job.getStockOptionShares();
        double WS = job.getWellnessStipend();
        double ALI = job.getLifeInsurance(); // note: (LI/100 * YS) is computed in the Job class
        double PDF = job.getPersonalDevelopmentFund();


        ComparisonSettings settings = new ComparisonSettings();

        SQLiteDatabase settingsDatabase = helper.getReadableDatabase();
        try (Cursor cursor = settingsDatabase.query(JobComparisonDB.ComparisonSettings.TABLE_NAME,
                null, null, null, null, null, null, "1")) {

            if (cursor.moveToFirst()) {

                int salary = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT);
                int bonus = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT);
                int stock = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT);
                int stipend = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT);
                int insurance = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT);
                int pdf = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT);

                settings.setWeights(
                        salary,
                        bonus,
                        stock,
                        stipend,
                        insurance,
                        pdf
                );
                
            }
        }

        int total = settings.getTotalWeight();

        return ((float)((double)settings.getSalaryWeight()/total) * AYS)
                + ((float)((double)settings.getBonusWeight()/total) * AYB)
                + (float)((double)settings.getStockWeight()/total) * (float)(SOS / 3)
                + ((float)((double)settings.getStipendWeight()/total) * WS)
                + (float)((double)settings.getInsuranceWeight()/total) * (ALI)
                + ((float)((double)settings.getPdfWeight()/ total) * PDF);
    }
}
