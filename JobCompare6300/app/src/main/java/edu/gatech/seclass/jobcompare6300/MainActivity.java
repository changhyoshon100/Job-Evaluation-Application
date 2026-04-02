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

public class MainActivity extends AppCompatActivity {
    private JobComparisonDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new JobComparisonDbHelper(this);

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor dbCursor = database.query(JobComparisonDB.JobOffers.TABLE_NAME, null, null, null, null, null, null);

        boolean jobOfferEntered = false;

        if (dbCursor.getCount() > 0) {
            jobOfferEntered = true;
        }

        dbCursor.close();

        Button buttonEnterCurrent = findViewById(R.id.buttonEnterCurrent);
        Button buttonEnterOffer = findViewById(R.id.buttonEnterOffer);
        Button buttonComparisonSetting = findViewById(R.id.buttonAdjustSetting);
        Button buttonChooseJobs = findViewById(R.id.buttonChooseJobs);

        buttonChooseJobs.setEnabled(jobOfferEntered);

        buttonEnterCurrent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentJobActivity.class);
            startActivity(intent);
        });

        buttonEnterOffer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JobOfferActivity.class);
            startActivity(intent);
        });

        buttonComparisonSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        buttonChooseJobs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JobChoosingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}