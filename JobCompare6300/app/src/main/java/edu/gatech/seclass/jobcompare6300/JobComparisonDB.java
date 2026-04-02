package edu.gatech.seclass.jobcompare6300;

import android.provider.BaseColumns;

// Reference: https://developer.android.com/training/data-storage/sqlite#DefineContract
public final class JobComparisonDB {
    private JobComparisonDB() {}

    public static class CurrentJobDetails implements BaseColumns {
        public static final String TABLE_NAME = "current_job_details";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_COST_OF_LIVING = "cost_of_living";
        public static final String COLUMN_YEARLY_SALARY = "yearly_salary";
        public static final String COLUMN_YEARLY_BONUS = "yearly_bonus";
        public static final String COLUMN_STOCK_SHARES = "stock_shares";
        public static final String COLUMN_WELLNESS_STIPEND = "wellness_stipend";
        public static final String COLUMN_LIFE_INSURANCE = "life_insurance";
        public static final String COLUMN_PERSONAL_DEV_FUND = "personal_dev_fund";
    }

    public static class ComparisonSettings implements BaseColumns {
        public static final String TABLE_NAME = "comparison_settings";
        public static final String COLUMN_YEARLY_SALARY_WEIGHT = "salary_weight";
        public static final String COLUMN_YEARLY_BONUS_WEIGHT = "bonus_weight";
        public static final String COLUMN_STOCK_OPTION_SHARES_WEIGHT = "stock_weight";
        public static final String COLUMN_WELLNESS_STIPEND_WEIGHT = "wellness_weight";
        public static final String COLUMN_LIFE_INSURANCE_WEIGHT = "insurance_weight";
        public static final String COLUMN_PERSONAL_DEV_FUND_WEIGHT = "personal_dev_weight";
    }

    public static class JobOffers implements BaseColumns {
        public static final String TABLE_NAME = "job_offers";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_COST_OF_LIVING = "cost_of_living";
        public static final String COLUMN_YEARLY_SALARY = "yearly_salary";
        public static final String COLUMN_YEARLY_BONUS = "yearly_bonus";
        public static final String COLUMN_STOCK_SHARES = "stock_shares";
        public static final String COLUMN_WELLNESS_STIPEND = "wellness_stipend";
        public static final String COLUMN_LIFE_INSURANCE = "life_insurance";
        public static final String COLUMN_PERSONAL_DEV_FUND = "personal_dev_fund";
    }
}