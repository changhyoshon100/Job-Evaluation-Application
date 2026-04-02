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

import java.util.ArrayList;

public class JobOfferActivity extends AppCompatActivity {

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

    private void saveJobOffer(Job job) {
        JobComparisonDbHelper helper;

        helper = new JobComparisonDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JobComparisonDB.JobOffers.COLUMN_TITLE, job.getTitle());
        values.put(JobComparisonDB.JobOffers.COLUMN_COMPANY, job.getCompany());
        values.put(JobComparisonDB.JobOffers.COLUMN_LOCATION, job.getLocation());
        values.put(JobComparisonDB.JobOffers.COLUMN_COST_OF_LIVING, job.getCostOfLiving());
        values.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_SALARY, job.getYearlySalary());
        values.put(JobComparisonDB.JobOffers.COLUMN_YEARLY_BONUS, job.getYearlyBonus());
        values.put(JobComparisonDB.JobOffers.COLUMN_STOCK_SHARES, job.getStockOptionShares());
        values.put(JobComparisonDB.JobOffers.COLUMN_WELLNESS_STIPEND, job.getWellnessStipend());
        values.put(JobComparisonDB.JobOffers.COLUMN_LIFE_INSURANCE, job.getLifeInsurance());
        values.put(JobComparisonDB.JobOffers.COLUMN_PERSONAL_DEV_FUND, job.getPersonalDevelopmentFund());

        db.insert("job_offers", null, values);

        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_joboffer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(JobOfferActivity.this, JobOfferNextActivity.class);
            startActivity(intent);
        });

        buttonSave.setOnClickListener(v -> {
            // Validate input
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

            //Create new Job object
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

            saveJobOffer(job);

            Intent intent = new Intent(JobOfferActivity.this, JobOfferNextActivity.class);
            Job currentJob = getCurrentJob();
            Job secondJob = new Job(
                    title,
                    company,
                    location,
                    cost_of_living,
                    yearly_salary,
                    yearly_bonus,
                    stock_option_shares,
                    wellness_stipend,
                    life_insurance,
                    personal_development_fund
            );
            intent.putExtra("currentJob", currentJob);
            intent.putExtra("job2", secondJob);
            startActivity(intent);
        });
    }

    private Job getCurrentJob() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                JobComparisonDB.CurrentJobDetails.TABLE_NAME,
                null, null, null, null, null, null
        );

        Job currentJob = new Job();
        while (cursor.moveToNext()) {
            currentJob.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)));
            currentJob.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)));
            currentJob.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)));
            currentJob.setCostOfLiving(cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING)));
            currentJob.setYearlySalary(cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY)));
            currentJob.setYearlyBonus(cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS)));
            currentJob.setStockOptionShares(cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES)));
            currentJob.setWellnessStipend(cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND)));
            currentJob.setLifeInsurancePercentage(cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE)));
            currentJob.setPersonalDevelopmentFund(cursor.getFloat(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND)));
        }
        cursor.close();
        return currentJob;
    }
}