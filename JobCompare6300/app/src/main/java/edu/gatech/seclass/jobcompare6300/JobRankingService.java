package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class JobRankingService {
    private JobComparisonDbHelper helper;

    private Context context;

    public JobRankingService(Context context) {
        this.context = context;
        helper = new JobComparisonDbHelper(context);
    }

    /**
     *
     * @return ArrayList<RankedJob>
     *
     *    1. Iterates through the job offers db, creating a Job object out of each row
     *          and use it to create a rankedJob object with the score and adds that object
     *          to the list that will be returned
     *    2. iterates through the current job db and creates a Job object out of the single row
     *          and use it to create a rankedJob object with the score and and adds that object
     *          to the list that will be returned
     *    3. sorts the list by score
     */
    public ArrayList<RankedJob> rankJobs() {//ComparisonSettings comparisonSettings) {
        ArrayList<RankedJob> jobRanking = new ArrayList<>();
        SQLiteDatabase jobOffersDatabase = helper.getReadableDatabase();

        Cursor cursor = jobOffersDatabase.query(
                JobComparisonDB.JobOffers.TABLE_NAME,
                null, null, null, null, null,
                null
        );
        JobScorer scorer;
        while (cursor.moveToNext()) {
            Job job = new Job();
            job.setJob(
                    cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND))
            );
            RankedJob rankedJob = new RankedJob();
            rankedJob.setJob(job);

            scorer = new JobScorer(context);
            rankedJob.setScore(scorer.computeScore(job)); //, comparisonSettings));

            jobRanking.add(rankedJob);
        }
        cursor.close();

        SQLiteDatabase currJobDatabase = helper.getReadableDatabase();
        Cursor cursor2 = currJobDatabase.query(
                JobComparisonDB.CurrentJobDetails.TABLE_NAME,
                null, null, null, null, null,
                null
        );
        while (cursor2.moveToNext()) {
            Job job = new Job();
            job.setJob(
                    cursor2.getString(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_TITLE)),
                    cursor2.getString(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COMPANY)),
                    cursor2.getString(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LOCATION)),
                    cursor2.getInt(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_COST_OF_LIVING)),
                    cursor2.getDouble(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_SALARY)),
                    cursor2.getDouble(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_YEARLY_BONUS)),
                    cursor2.getInt(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_STOCK_SHARES)),
                    cursor2.getDouble(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_WELLNESS_STIPEND)),
                    cursor2.getInt(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_LIFE_INSURANCE)),
                    cursor2.getDouble(cursor2.getColumnIndexOrThrow(JobComparisonDB.CurrentJobDetails.COLUMN_PERSONAL_DEV_FUND))
            );

            RankedJob rankedJob = new RankedJob();
            rankedJob.setJob(job);

            scorer = new JobScorer(context);
            rankedJob.setScore(scorer.computeScore(job));//, comparisonSettings));

            jobRanking.add(rankedJob);
        }
        cursor2.close();

        Collections.sort(jobRanking, (a, b) -> Double.compare(b.getScore(), a.getScore()));
        return jobRanking;
    }

}
