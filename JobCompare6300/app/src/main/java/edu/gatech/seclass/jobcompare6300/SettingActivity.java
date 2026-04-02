package edu.gatech.seclass.jobcompare6300;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Reference: https://developer.android.com/training/data-storage/sqlite#DefineContract
public class SettingActivity extends AppCompatActivity {
    private JobComparisonDbHelper helper;

    EditText input_salary;
    EditText input_bonus;
    EditText input_stock_option;
    EditText input_wellness_stipend;
    EditText input_life_insurance;
    EditText input_personal_dev_fund;

    private void saveSettingsToDB(ComparisonSettings settings) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues comparison_setting_values = new ContentValues();

        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT, settings.getSalaryWeight());
        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT, settings.getBonusWeight());
        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT, settings.getStockWeight());
        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT, settings.getStipendWeight());
        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT, settings.getInsuranceWeight());
        comparison_setting_values.put(JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT, settings.getPdfWeight());

        db.update(JobComparisonDB.ComparisonSettings.TABLE_NAME, comparison_setting_values, null, null);
    }

    private void loadSettingsFromDB() {
        SQLiteDatabase db = helper.getReadableDatabase();

        try (Cursor cursor = db.query(JobComparisonDB.ComparisonSettings.TABLE_NAME,
                null, null, null, null, null, null, "1")) {

            if (cursor.moveToFirst()) {

                int salaryCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_SALARY_WEIGHT);
                int bonusCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_YEARLY_BONUS_WEIGHT);
                int stockCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_STOCK_OPTION_SHARES_WEIGHT);
                int stipendCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_WELLNESS_STIPEND_WEIGHT);
                int insuranceCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_LIFE_INSURANCE_WEIGHT);
                int pdfCol = cursor.getColumnIndexOrThrow(
                        JobComparisonDB.ComparisonSettings.COLUMN_PERSONAL_DEV_FUND_WEIGHT);

                input_salary.setText(String.valueOf(cursor.getInt(salaryCol)));
                input_bonus.setText(String.valueOf(cursor.getInt(bonusCol)));
                input_stock_option.setText(String.valueOf(cursor.getInt(stockCol)));
                input_wellness_stipend.setText(String.valueOf(cursor.getInt(stipendCol)));
                input_life_insurance.setText(String.valueOf(cursor.getInt(insuranceCol)));
                input_personal_dev_fund.setText(String.valueOf(cursor.getInt(pdfCol)));
            }
        }
    }

    private Integer parseWeight(EditText field, String errorMsg) {
        try {
            int value = Integer.parseInt(field.getText().toString());

            if (value < 0 || value > 9) {
                field.setError("Must be between 0 and 9");
                return null;
            }

            return value;

        } catch (NumberFormatException e) {
            field.setError(errorMsg);
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helper = new JobComparisonDbHelper(this);

        input_salary = findViewById(R.id.inputYearlySalaryWeight);
        input_bonus = findViewById(R.id.inputYearlyBonusWeight);
        input_stock_option = findViewById(R.id.inputStockOptionSharesWeight);
        input_wellness_stipend = findViewById(R.id.inputWellnessStipendWeight);
        input_life_insurance = findViewById(R.id.inputLifeInsuranceWeight);
        input_personal_dev_fund = findViewById(R.id.inputPersonalDevelopmentFundWeight);

        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonSave = findViewById(R.id.buttonSave);

        loadSettingsFromDB();

        //Cancel button click listeners
        buttonCancel.setOnClickListener(v -> {
            finish();
        });

        //Save button click listeners
        buttonSave.setOnClickListener(v -> {

            //Validate setting input
            Integer salary = parseWeight(input_salary, "Salary weight required");
            Integer bonus = parseWeight(input_bonus, "Bonus weight required");
            Integer stock = parseWeight(input_stock_option, "Stock weight required");
            Integer stipend = parseWeight(input_wellness_stipend, "Stipend weight required");
            Integer insurance = parseWeight(input_life_insurance, "Insurance weight required");
            Integer pdf = parseWeight(input_personal_dev_fund, "Personal development fund weight required");

            if (salary != null && bonus != null && stock != null &&
                    stipend != null && insurance != null && pdf != null) {

                ComparisonSettings settings = new ComparisonSettings();

                settings.setWeights(
                        salary,
                        bonus,
                        stock,
                        stipend,
                        insurance,
                        pdf
                );

                saveSettingsToDB(settings);

                finish();
            }
        });
    }

    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}