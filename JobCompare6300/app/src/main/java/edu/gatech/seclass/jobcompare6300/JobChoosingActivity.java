package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class JobChoosingActivity extends AppCompatActivity {

    private CheckBox job1;
    private CheckBox job2;
    private CheckBox job3;
    private CheckBox job4;
    private CheckBox job5;
    private CheckBox job6;

    private ArrayList<RankedJob> rankedJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jobchoosing);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // error message section for if the user doesnt choose exactly two jobs
        TextView errorMessage = findViewById(R.id.errorMessage);

        // set each "# title company score" according to result of rankJobs()

        JobRankingService rankingService = new JobRankingService(this);
        ArrayList<RankedJob> rankedJobsList = rankingService.rankJobs();


        job1 = findViewById(R.id.job1);
        job2 = findViewById(R.id.job2);
        job3 = findViewById(R.id.job3);
        job4 = findViewById(R.id.job4);
        job5 = findViewById(R.id.job5);
        job6 = findViewById(R.id.job6);


        job1.setText("#1 "+rankedJobsList.get(0).getJob().getTitle()
                + " " + rankedJobsList.get(0).getJob().getCompany()
                + " " + rankedJobsList.get(0).getScore());
        job2.setText("#2 "+rankedJobsList.get(1).getJob().getTitle()
                + " " + rankedJobsList.get(1).getJob().getCompany()
                + " " + rankedJobsList.get(1).getScore());
        if (rankedJobsList.size() < 3) {
            job3.setVisibility(View.GONE);
        } else {
            job3.setText("#3 "+rankedJobsList.get(2).getJob().getTitle()
                    + " " + rankedJobsList.get(2).getJob().getCompany()
                    + " " + rankedJobsList.get(2).getScore());
        }
        if (rankedJobsList.size() < 4) {
            job4.setVisibility(View.GONE);
        } else {
            job4.setText("#4 "+rankedJobsList.get(3).getJob().getTitle()
                    + " " + rankedJobsList.get(3).getJob().getCompany()
                    + " " + rankedJobsList.get(3).getScore());
        }
        if (rankedJobsList.size() < 5) {
            job5.setVisibility(View.GONE);
        } else {
            job5.setText("#5 "+rankedJobsList.get(4).getJob().getTitle()
                    + " " + rankedJobsList.get(4).getJob().getCompany()
                    + " " + rankedJobsList.get(4).getScore());
        }
        if (rankedJobsList.size() < 6) {
            job6.setVisibility(View.GONE);
        } else {
            job6.setText("#6 "+rankedJobsList.get(5).getJob().getTitle()
                    + " " + rankedJobsList.get(5).getJob().getCompany()
                    + " " + rankedJobsList.get(5).getScore());
        }


        // cancel button

        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonCompare = findViewById(R.id.buttonCompare);

        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(JobChoosingActivity.this, MainActivity.class);
            startActivity(intent);
        });


        // feed chosen jobs into result activity if exactly two are chosen else throw error

        buttonCompare.setOnClickListener(v -> {
            CheckBox[] jobs = {job1, job2, job3, job4, job5, job6};

            int checkedCount = 0;
            for (CheckBox job : jobs) {
                if (job.getVisibility() == View.VISIBLE && job.isChecked()) {
                    checkedCount++;
                }
            }
            if (checkedCount == 2) {
                int[] selectedJobs = getSelectedJobs();

                int firstIndex = selectedJobs[0];
                int secondIndex = selectedJobs[1];

                Job firstJob = rankedJobsList.get(firstIndex).getJob();
                Job secondJob = rankedJobsList.get(secondIndex).getJob();

                Intent intent = new Intent(JobChoosingActivity.this, ResultActivity.class);
                intent.putExtra("job1", firstJob);
                intent.putExtra("job2", secondJob);
                startActivity(intent);

            } else {
                errorMessage.setText("You must choose exactly 2 jobs.");
                errorMessage.setVisibility(View.VISIBLE);
            }

        });
    }


    private int[] getSelectedJobs() {
        int[] selected = new int[2];
        int index = 0;

        if (job1 != null && job1.isChecked()) selected[index++] = 0;
        if (job2 != null && job2.isChecked()) selected[index++] = 1;
        if (job3 != null && job3.isChecked()) selected[index++] = 2;
        if (job4 != null && job4.isChecked()) selected[index++] = 3;
        if (job5 != null && job5.isChecked()) selected[index++] = 4;
        if (job6 != null && job6.isChecked()) selected[index++] = 5;

        return selected;
    }
}