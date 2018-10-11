package test.pubnub;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.pubnub.PubNubManager;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.callback.MessageLiveData;
import com.pubnub.callback.OnResultListener;
import com.pubnub.callback.OnSubscribeListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by clickapps on 31/8/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MessageLiveData messageLiveData = new MessageLiveData(this, getPackageName());
        messageLiveData.observe(this, new Observer<PNMessageResult>() {
            @Override
            public void onChanged(@NonNull PNMessageResult pnMessageResult) {
                Log.i(getLocalClassName(), "Channel Observe = " + pnMessageResult.getChannel());
                Log.i(getLocalClassName(), "Message Observe = " + pnMessageResult.getMessage().getAsString());
            }
        });
        PubNubManager.with(this).unSubScribeAll();
        List<String> channelList = PubNubManager.with(this).subScribedChannel();
        Log.i(getLocalClassName(), "Channels = " + TextUtils.join(",", channelList));
        String[] channels = new String[]{"pubnubTesting"};
        PubNubManager.with(this)
                .subScribe(channels)
                .build();

        channelList = PubNubManager.with(this).subScribedChannel();
        Log.i(getLocalClassName(), "Channels = " + TextUtils.join(",", channelList));
        PubNubManager.with(this)
                .publish("test", channels)
                .callback(new OnResultListener<PNPublishResult>() {
                    @Override
                    public void result(PNPublishResult result, PNStatus status) {
                        Log.i(getLocalClassName(), "publish Channel = " + status.getAffectedChannels());
                    }
                })
                .build();
//        PubNubManager.with(this)
//                .history()
//                .historyCount(10)
//                .callback(new OnResultListener<PNHistoryResult>() {
//                    @Override
//                    public void result(PNHistoryResult result, PNStatus status) {
//
//                    }
//                })
//                .build();
//        PubNubManager.with(this)
//                .publish("Message", "channel")
//                .callback(new OnResultListener<PNPublishResult>() {
//                    @Override
//                    public void result(PNPublishResult result, PNStatus status) {
//
//                    }
//                })
//                .build();
//
//        PubNubManager.with(this)
//                .unSubScribe("channel")
//                .statusCallback(new OnSubscribeListener<PNStatus>() {
//                    @Override
//                    public void result(String channel, PNStatus result) {
//
//                    }
//                })
//                .build();
//
//        PubNubManager.with(this)
//                .unSubScribeAll()
//                .statusCallback(new OnSubscribeListener<PNStatus>() {
//                    @Override
//                    public void result(String channel, PNStatus result) {
//
//                    }
//                })
//                .build();
    }

}
