package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonChooseJobs = findViewById(R.id.buttonChooseJobs);
        Button buttonReturnToMain = findViewById(R.id.buttonReturnToMain);

        TextView job1Header = findViewById(R.id.job1Header);
        TextView job2Header = findViewById(R.id.job2Header);

        TextView job1Title = findViewById(R.id.job1Title);
        TextView job2Title = findViewById(R.id.job2Title);

        TextView job1Company = findViewById(R.id.job1Company);
        TextView job2Company = findViewById(R.id.job2Company);

        TextView job1Location = findViewById(R.id.job1Location);
        TextView job2Location = findViewById(R.id.job2Location);

        TextView job1Salary = findViewById(R.id.job1Salary);
        TextView job2Salary = findViewById(R.id.job2Salary);

        TextView job1Bonus = findViewById(R.id.job1Bonus);
        TextView job2Bonus = findViewById(R.id.job2Bonus);

        TextView job1Stock = findViewById(R.id.job1Stock);
        TextView job2Stock = findViewById(R.id.job2Stock);

        TextView job1Wellness = findViewById(R.id.job1Wellness);
        TextView job2Wellness = findViewById(R.id.job2Wellness);

        TextView job1Insurance = findViewById(R.id.job1Insurance);
        TextView job2Insurance = findViewById(R.id.job2Insurance);

        TextView job1Pdf = findViewById(R.id.job1Pdf);
        TextView job2Pdf = findViewById(R.id.job2Pdf);

        TextView job1Score = findViewById(R.id.job1Score);
        TextView job2Score = findViewById(R.id.job2Score);

        Intent intent = getIntent();
        JobScorer scorer = new JobScorer(this);

        Job firstJob = (Job) intent.getSerializableExtra("job1");
        Job secondJob = (Job) intent.getSerializableExtra("job2");

        job1Header.setText("Job 1");
        job2Header.setText("Job 2");

        if (firstJob != null) {
            firstJob.computeAdjustedSalary(firstJob);
            firstJob.computeAdjustedBonus(firstJob);
            firstJob.lifeInsurance(firstJob);

            job1Title.setText(firstJob.getTitle());
            job1Company.setText(firstJob.getCompany());
            job1Location.setText(firstJob.getLocation());
            job1Salary.setText(String.valueOf(firstJob.getAdjustedSalary()));
            job1Bonus.setText(String.valueOf(firstJob.getAdjustedBonus()));
            job1Stock.setText(String.valueOf(firstJob.getStockOptionShares()));
            job1Wellness.setText(String.valueOf(firstJob.getWellnessStipend()));
            job1Insurance.setText(String.format("%.0f", firstJob.getLifeInsurance()));
            job1Pdf.setText(String.valueOf(firstJob.getPersonalDevelopmentFund()));
            double firstScore = scorer.computeScore(firstJob);
            job1Score.setText(String.format("%.1f", firstScore));
        } else {
            clearJobColumn(
                    job1Title, job1Company, job1Location, job1Salary, job1Bonus,
                    job1Stock, job1Wellness, job1Insurance, job1Pdf, job1Score
            );
        }

        if (secondJob != null) {
            secondJob.computeAdjustedSalary(secondJob);
            secondJob.computeAdjustedBonus(secondJob);
            secondJob.lifeInsurance(secondJob);

            job2Title.setText(secondJob.getTitle());
            job2Company.setText(secondJob.getCompany());
            job2Location.setText(secondJob.getLocation());
            job2Salary.setText(String.valueOf(secondJob.getAdjustedSalary()));
            job2Bonus.setText(String.valueOf(secondJob.getAdjustedBonus()));
            job2Stock.setText(String.valueOf(secondJob.getStockOptionShares()));
            job2Wellness.setText(String.valueOf(secondJob.getWellnessStipend()));
            job2Insurance.setText(String.format("%.0f", secondJob.getLifeInsurance()));
            job2Pdf.setText(String.valueOf(secondJob.getPersonalDevelopmentFund()));
            double secondScore = scorer.computeScore(secondJob);
            job2Score.setText(String.format("%.1f", secondScore));
        } else {
            clearJobColumn(
                    job2Title, job2Company, job2Location, job2Salary, job2Bonus,
                    job2Stock, job2Wellness, job2Insurance, job2Pdf, job2Score
            );
        }

        buttonChooseJobs.setOnClickListener(v -> {
            Intent chooseAgain = new Intent(ResultActivity.this, JobChoosingActivity.class);
            startActivity(chooseAgain);
        });

        buttonReturnToMain.setOnClickListener(v -> {
            Intent backToMain = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(backToMain);
        });
    }

    private void clearJobColumn(TextView title, TextView company, TextView location,
                                TextView salary, TextView bonus, TextView stock,
                                TextView wellness, TextView insurance, TextView pdf,
                                TextView score) {
        title.setText("");
        company.setText("");
        location.setText("");
        salary.setText("");
        bonus.setText("");
        stock.setText("");
        wellness.setText("");
        insurance.setText("");
        pdf.setText("");
        score.setText("");
    }
}