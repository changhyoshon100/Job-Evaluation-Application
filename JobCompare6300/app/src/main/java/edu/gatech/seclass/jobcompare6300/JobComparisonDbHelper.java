package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.gatech.seclass.jobcompare6300.JobComparisonDB.*;

// Reference: https://developer.android.com/training/data-storage/sqlite#DefineContract
public class JobComparisonDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "JobComparison.db";

    public JobComparisonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CurrentJobDetails.TABLE_NAME + " (" +
                CurrentJobDetails._ID + " INTEGER PRIMARY KEY," +
                CurrentJobDetails.COLUMN_TITLE + " TEXT," +
                CurrentJobDetails.COLUMN_COMPANY + " TEXT," +
                CurrentJobDetails.COLUMN_LOCATION + " TEXT," +
                CurrentJobDetails.COLUMN_COST_OF_LIVING + " INTEGER," +
                CurrentJobDetails.COLUMN_YEARLY_SALARY + " REAL," +
                CurrentJobDetails.COLUMN_YEARLY_BONUS + " REAL," +
                CurrentJobDetails.COLUMN_STOCK_SHARES + " INTEGER," +
                CurrentJobDetails.COLUMN_WELLNESS_STIPEND + " REAL," +
                CurrentJobDetails.COLUMN_LIFE_INSURANCE + " INTEGER," +
                CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND + " REAL)");

        db.execSQL("CREATE TABLE " + JobComparisonDB.ComparisonSettings.TABLE_NAME + " (" +
                JobComparisonDB.ComparisonSettings._ID + " INTEGER PRIMARY KEY," +
                JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT + " INTEGER DEFAULT 1," +
                JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT + " INTEGER DEFAULT 1," +
                JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT + " INTEGER DEFAULT 1," +
                JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT + " INTEGER DEFAULT 1," +
                JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT + " INTEGER DEFAULT 1," +
                JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT + " INTEGER DEFAULT 1)");

        db.execSQL("INSERT INTO " + JobComparisonDB.ComparisonSettings.TABLE_NAME + " VALUES (1, 1, 1, 1, 1, 1, 1)");

        db.execSQL("CREATE TABLE " + JobOffers.TABLE_NAME + " (" +
                JobOffers._ID + " INTEGER PRIMARY KEY," +
                JobOffers.COLUMN_TITLE + " TEXT," +
                JobOffers.COLUMN_COMPANY + " TEXT," +
                JobOffers.COLUMN_LOCATION + " TEXT," +
                JobOffers.COLUMN_COST_OF_LIVING + " INTEGER," +
                JobOffers.COLUMN_YEARLY_SALARY + " REAL," +
                JobOffers.COLUMN_YEARLY_BONUS + " REAL," +
                JobOffers.COLUMN_STOCK_SHARES + " INTEGER," +
                JobOffers.COLUMN_WELLNESS_STIPEND + " REAL," +
                JobOffers.COLUMN_LIFE_INSURANCE + " INTEGER," +
                JobOffers.COLUMN_PERSONAL_DEV_FUND + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CurrentJobDetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JobComparisonDB.ComparisonSettings.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JobOffers.TABLE_NAME);
        onCreate(db);
    }
}