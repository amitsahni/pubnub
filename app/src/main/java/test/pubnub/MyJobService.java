package test.pubnub;

import android.content.Intent;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    Intent intent;

    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        Log.i("MyJobService", "onStartJob");
        intent = new Intent(this, MyService.class);
        startService(intent);
        return true; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
//        Log.i(MyJobService.class.getSimpleName(), job.getTag());
//        if (intent != null)
//            stopService(intent);
        return false; // Answers the question: "Should this job be retried?"
    }
}