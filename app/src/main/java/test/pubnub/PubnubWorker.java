package test.pubnub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PubnubWorker extends Worker {


    public PubnubWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("PubnubWorker", "Do Work");
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        getApplicationContext().startService(intent);
        return Worker.Result.success();
    }
}
