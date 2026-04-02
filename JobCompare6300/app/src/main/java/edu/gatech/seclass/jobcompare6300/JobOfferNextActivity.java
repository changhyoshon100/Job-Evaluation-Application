package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class JobOfferNextActivity extends AppCompatActivity {
    private JobComparisonDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_joboffernext);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new JobComparisonDbHelper(this);

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor dbCursor = database.query(JobComparisonDB.CurrentJobDetails.TABLE_NAME, null, null, null, null, null, null);

        boolean currentJobEntered = false;

        if (dbCursor.getCount() > 0) {
            currentJobEntered = true;
        }

        dbCursor.close();

        Button buttonReturnToMain = findViewById(R.id.buttonReturnToMain);
        Button buttonEnterOffer = findViewById(R.id.buttonEnterOffer);
        Button buttonCompareWithCurrent = findViewById(R.id.buttonCompareWithCurrent);

        buttonCompareWithCurrent.setEnabled(currentJobEntered);

        buttonReturnToMain.setOnClickListener(v -> {
            Intent intent = new Intent(JobOfferNextActivity.this, MainActivity.class);
            startActivity(intent);
        });


        buttonEnterOffer.setOnClickListener(v -> {
            Intent intent = new Intent(JobOfferNextActivity.this, JobOfferActivity.class);
            startActivity(intent);
        });

        buttonCompareWithCurrent.setOnClickListener(v -> {
            Intent jobOfferIntent = getIntent();
            Intent intent = new Intent(JobOfferNextActivity.this, ResultActivity.class);
            Job currentJob = (Job) jobOfferIntent.getSerializableExtra("currentJob");
            Job secondJob = (Job) jobOfferIntent.getSerializableExtra("job2");
            intent.putExtra("job1", currentJob);
            intent.putExtra("job2", secondJob);
            startActivity(intent);
        });
    }
}