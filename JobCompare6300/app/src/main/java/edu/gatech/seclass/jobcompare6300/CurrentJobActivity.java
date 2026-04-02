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
public class CurrentJobActivity extends AppCompatActivity {

    private JobComparisonDbHelper helper;

    EditText titleInput;
    EditText companyInput;
    EditText locationInput;
    EditText costOfLivingInput;
    EditText salaryInput;
    EditText bonusInput;
    EditText stockInput;
    EditText wellnessInput;
    EditText lifeInsuranceInput;
    EditText developmentInput;

    private void saveCurrentJob(Job job) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE, job.getTitle());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY, job.getCompany());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION, job.getLocation());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING, job.getCostOfLiving());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY, job.getYearlySalary());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS, job.getYearlyBonus());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES, job.getStockOptionShares());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND, job.getWellnessStipend());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE, job.getLifeInsurance());
        values.put(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND, job.getPersonalDevelopmentFund());

        db.insert(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, values);
    }

    private void loadCurrentJob() {
        SQLiteDatabase db = helper.getReadableDatabase();

        try (Cursor cursor = db.query(JobComparisonDB.CurrentJobDetails.TABLE_NAME,
                null, null, null, null, null, null, "1")){
            if (cursor.moveToFirst()) {
                titleInput.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)));
                companyInput.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)));
                locationInput.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)));
                costOfLivingInput.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING))));
                salaryInput.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY))));
                bonusInput.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS))));
                stockInput.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES))));
                wellnessInput.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND))));
                lifeInsuranceInput.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE))));
                developmentInput.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND))));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_currentjob);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helper = new JobComparisonDbHelper(this);

        titleInput = findViewById(R.id.inputTitle);
        companyInput = findViewById(R.id.inputCompany);
        locationInput = findViewById(R.id.inputLocation);
        costOfLivingInput = findViewById(R.id.inputCostOfLiving);
        salaryInput = findViewById(R.id.inputYearlySalary);
        bonusInput = findViewById(R.id.inputYearlyBonus);
        stockInput = findViewById(R.id.inputStockOptionShares);
        wellnessInput = findViewById(R.id.inputWellnessStipend);
        lifeInsuranceInput = findViewById(R.id.inputLifeInsurance);
        developmentInput = findViewById(R.id.inputPersonalDevelopmentFund);

        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonSave = findViewById(R.id.buttonSave);

        loadCurrentJob(); // display current job detail

        buttonCancel.setOnClickListener(v -> {
            finish();
        });

        buttonSave.setOnClickListener(v -> {
            // Validate user input
            if (!JobValidator.validate(
                    titleInput,
                    companyInput,
                    locationInput,
                    costOfLivingInput,
                    salaryInput,
                    bonusInput,
                    stockInput,
                    wellnessInput,
                    lifeInsuranceInput,
                    developmentInput)) {
                return;
            }

            // Save input to Job object
            Job job = new Job();
            job.setJob(
                    titleInput.getText().toString(),
                    companyInput.getText().toString(),
                    locationInput.getText().toString(),
                    Integer.parseInt(costOfLivingInput.getText().toString()),
                    Double.parseDouble(salaryInput.getText().toString()),
                    Double.parseDouble(bonusInput.getText().toString()),
                    Integer.parseInt(stockInput.getText().toString()),
                    Double.parseDouble(wellnessInput.getText().toString()),
                    Integer.parseInt(lifeInsuranceInput.getText().toString()),
                    Double.parseDouble(developmentInput.getText().toString())
            );

            saveCurrentJob(job);

            finish();
        });
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}