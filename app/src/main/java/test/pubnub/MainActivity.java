package test.pubnub;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.pubnub.PubNubManager;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.callback.LocalMessageLiveData;
import com.pubnub.callback.OnResultListener;
import com.pubnub.callback.PushMessage;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by clickapps on 31/8/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String[] channels = new String[]{"jisr_10979_user"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LocalMessageLiveData messageLiveData = new LocalMessageLiveData(this);
        messageLiveData.observe(this, new Observer<PushMessage>() {
            @Override
            public void onChanged(@NonNull PushMessage pnMessageResult) {
                Log.i(getLocalClassName(), "Channel Observe = " + pnMessageResult.getChannel());
                Log.i(getLocalClassName(), "Message Observe = " + pnMessageResult.getMessage());
            }
        });
        Log.i(getLocalClassName(), "Channels = " + TextUtils.join(",",
                PubNubManager.with(this).subScribedChannel()));

        // Create a new dispatcher using the Google Play driver.
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));
//
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class) // the JobService that will be called
//                .setTag(getPackageName())
//                .setConstraints(Constraint.ON_ANY_NETWORK)
//                .build();
//
//        dispatcher.mustSchedule(myJob);
        WorkManager.getInstance().enqueue(OneTimeWorkRequest.from(PubnubWorker.class));

    }

    @OnClick({R.id.subscribe, R.id.unSubscribe, R.id.unSubscribeAll, R.id.publish, R.id.history})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subscribe:
                PubNubManager.with(this)
                        .subScribe(channels)
                        .build();
                break;
            case R.id.unSubscribe:
                PubNubManager.with(this)
                        .unSubScribe(channels)
                        .build();
                break;
            case R.id.unSubscribeAll:
                PubNubManager.with(this)
                        .unSubScribeAll()
                        .build();
                break;
            case R.id.publish:
                PubNubManager.with(this)
                        .publish("test", channels)
                        .callback(new OnResultListener<PNPublishResult>() {
                            @Override
                            public void result(PNPublishResult result, PNStatus status) {
                                Log.i(getLocalClassName(), "publish Channel = " + status.getAffectedChannels());
                            }
                        })
                        .build();
                break;
            case R.id.history:
                PubNubManager.with(this)
                        .history()
                        .channels(channels)
                        .historyCount(10)
                        .callback(new OnResultListener<PNHistoryResult>() {
                            @Override
                            public void result(PNHistoryResult result, PNStatus status) {
                                Log.i(getLocalClassName(), status.toString());
                            }
                        })
                        .build();
                break;
        }
    }
}
