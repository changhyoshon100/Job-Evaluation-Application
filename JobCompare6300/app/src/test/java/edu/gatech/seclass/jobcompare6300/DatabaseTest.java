package edu.gatech.seclass.jobcompare6300;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

// Reference: https://developer.android.com/reference/android/database/Cursor
@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {
    private JobComparisonDbHelper helper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        helper = new JobComparisonDbHelper(RuntimeEnvironment.getApplication());
        db = helper.getWritableDatabase();
    }

    @After
    public void close() {
        helper.close();
    }

    @Test
    public void testEnterCurrentJobDetails() {
        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE, "Software Engineer");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY, "GATECH");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION, "Atlanta, GA");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING, 123213.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY, 120000.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS, 10000.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES, 100);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND, 500.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE, 5);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND, 1000.0f);

        long rowId = db.insert(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, values);
        // Test if current job details gets inserted successfully
        assertNotEquals(-1, rowId);

        // Now test if correct values for current job details gets saved successfully
        Cursor cursor = db.query(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, null, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Software Engineer", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)));
        assertEquals("GATECH", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)));
        assertEquals("Atlanta, GA", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)));
        assertEquals(123213.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING)), 0.0f);
        assertEquals(120000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY)), 0.0f);
        assertEquals(10000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS)), 0.0f);
        assertEquals(100, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES)));
        assertEquals(500.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND)), 0.0f);
        assertEquals(5, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE)));
        assertEquals(1000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND)), 0.0f);

        cursor.close();
    }

    @Test
    public void testEditCurrentJobDetails() {
        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE, "Software Engineer");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY, "GATECH");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION, "Atlanta, GA");
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING, 123213.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY, 120000.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS, 10000.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES, 100);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND, 500.0f);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE, 5);
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND, 1000.0f);

        db.insert(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, values);

        ContentValues updated_value = new ContentValues();
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE, "UI Designer");
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY, "Amazon");
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION, "Seattle, WA");
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING, 999999.0f);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY, 150000.0f);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS, 20000.0f);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES, 200);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND, 800.0f);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE, 8);
        updated_value.put(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND, 2000.0f);

        int updated = db.update(JobComparisonDB.CurrentJobDetails.TABLE_NAME, updated_value, null, null);

        // Test to see the update is working and not inserting
        assertEquals(1, updated);

        Cursor cursor = db.query(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, null, null, null, null, null);

        // Test to see if values are updated values
        assertTrue(cursor.moveToFirst());
        assertEquals("UI Designer", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)));
        assertEquals("Amazon", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)));
        assertEquals("Seattle, WA", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)));
        assertEquals(999999.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING)), 0.0f);
        assertEquals(150000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY)), 0.0f);
        assertEquals(20000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS)), 0.0f);
        assertEquals(200, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES)));
        assertEquals(800.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND)), 0.0f);
        assertEquals(8, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE)));
        assertEquals(2000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND)), 0.0f);

        cursor.close();
    }

    @Test
    public void testAdjustComparisonSettings() {
        // Verify if all values are 1 for default comparison settings
        Cursor cursor = db.query(JobComparisonDB.ComparisonSettings.TABLE_NAME,
                null, null, null, null, null, null);

        assertTrue(cursor.moveToFirst());
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT)));
        cursor.close();

        // Test updating the comparison setting
        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT, 5);
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT, 3);
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT, 4);
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT, 2);
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT, 1);
        values.put(JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT, 6);

        int updated = db.update(JobComparisonDB.ComparisonSettings.TABLE_NAME, values, null, null);
        // Test to see if values are updated values
        assertEquals(1, updated);

        Cursor newCursor = db.query(JobComparisonDB.ComparisonSettings.TABLE_NAME, null, null, null, null, null, null);
        assertTrue(newCursor.moveToFirst());
        assertEquals(5, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT)));
        assertEquals(3, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT)));
        assertEquals(4, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT)));
        assertEquals(2, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT)));
        assertEquals(1, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT)));
        assertEquals(6, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT)));

        newCursor.close();
    }

    @Test
    public void testEnterJobOffer() {
        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.JobOffers.COLUMN_TITLE, "Software Engineer");
        values.put(JobComparisonDB.JobOffers.COLUMN_COMPANY, "GATECH");
        values.put(JobComparisonDB.JobOffers.COLUMN_LOCATION, "Atlanta, GA");
        values.put(JobComparisonDB.JobOffers.COLUMN_COST_OF_LIVING, 123213.0f);
        values.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_SALARY, 120000.0f);
        values.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_BONUS, 10000.0f);
        values.put(JobComparisonDB.JobOffers.COLUMN_STOCK_SHARES, 100);
        values.put(JobComparisonDB.JobOffers.COLUMN_WELLNESS_STIPEND, 500.0f);
        values.put(JobComparisonDB.JobOffers.COLUMN_LIFE_INSURANCE, 5);
        values.put(JobComparisonDB.JobOffers.COLUMN_PERSONAL_DEV_FUND, 1000.0f);

        long rowId = db.insert(JobComparisonDB.JobOffers.TABLE_NAME, null, values);
        // Check if job offer saved successfully
        assertNotEquals(-1, rowId);

        Cursor cursor = db.query(JobComparisonDB.JobOffers.TABLE_NAME, null, null, null, null, null, null);

        // Checks if entered job offer has the correct values
        assertTrue(cursor.moveToFirst());
        assertEquals("Software Engineer", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_TITLE)));
        assertEquals("GATECH", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_COMPANY)));
        assertEquals("Atlanta, GA", cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_LOCATION)));
        assertEquals(123213.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_COST_OF_LIVING)), 0.0f);
        assertEquals(120000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_YEARLY_SALARY)), 0.0f);
        assertEquals(10000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_YEARLY_BONUS)), 0.0f);
        assertEquals(100, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_STOCK_SHARES)));
        assertEquals(500.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_WELLNESS_STIPEND)), 0.0f);
        assertEquals(5, cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_LIFE_INSURANCE)));
        assertEquals(1000.0f, cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_PERSONAL_DEV_FUND)), 0.0f);
        assertEquals(1, cursor.getCount());

        cursor.close();

        // Entering second job offer
        ContentValues newValues = new ContentValues();
        newValues.put(JobComparisonDB.JobOffers.COLUMN_TITLE, "UI Designer");
        newValues.put(JobComparisonDB.JobOffers.COLUMN_COMPANY, "Amazon");
        newValues.put(JobComparisonDB.JobOffers.COLUMN_LOCATION, "Seattle, WA");
        newValues.put(JobComparisonDB.JobOffers.COLUMN_COST_OF_LIVING, 123213.0f);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_SALARY, 120000.0f);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_BONUS, 10000.0f);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_STOCK_SHARES, 100);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_WELLNESS_STIPEND, 500.0f);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_LIFE_INSURANCE, 5);
        newValues.put(JobComparisonDB.JobOffers.COLUMN_PERSONAL_DEV_FUND, 1000.0f);

        long rowId2 = db.insert(JobComparisonDB.JobOffers.TABLE_NAME, null, newValues);
        // Check if second job offer saved successfully
        assertNotEquals(-1, rowId2);

        Cursor newCursor = db.query(JobComparisonDB.JobOffers.TABLE_NAME, null, null, null, null, null, null);
        assertEquals(2, newCursor.getCount());
        assertTrue(newCursor.moveToFirst());
        assertTrue(newCursor.moveToNext());
        assertEquals("UI Designer", newCursor.getString(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_TITLE)));
        assertEquals("Amazon", newCursor.getString(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_COMPANY)));
        assertEquals("Seattle, WA", newCursor.getString(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_LOCATION)));
        assertEquals(123213.0f, newCursor.getFloat(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_COST_OF_LIVING)), 0.0f);
        assertEquals(120000.0f, newCursor.getFloat(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_YEARLY_SALARY)), 0.0f);
        assertEquals(10000.0f, newCursor.getFloat(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_YEARLY_BONUS)), 0.0f);
        assertEquals(100, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_STOCK_SHARES)));
        assertEquals(500.0f, newCursor.getFloat(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_WELLNESS_STIPEND)), 0.0f);
        assertEquals(5, newCursor.getInt(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_LIFE_INSURANCE)));
        assertEquals(1000.0f, newCursor.getFloat(newCursor.getColumnIndexOrThrow(JobComparisonDB.JobOffers.COLUMN_PERSONAL_DEV_FUND)), 0.0f);

        newCursor.close();
    }
}
