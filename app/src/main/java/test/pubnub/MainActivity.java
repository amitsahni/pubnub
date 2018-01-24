package test.pubnub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pubnub.PubNubManager;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.callback.OnResultListener;
import com.pubnub.callback.OnSubscribeListener;

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
        String[] channels = new String[]{"channel1", "channel2"};
        PubNubManager.with(this)
                .subScribe(channels)
                .messageCallback(new OnSubscribeListener<PNMessageResult>() {
                    @Override
                    public void result(String channel, PNMessageResult result) {

                    }
                })
                .presenceCallback(new OnSubscribeListener<PNPresenceEventResult>() {
                    @Override
                    public void result(String channel, PNPresenceEventResult result) {

                    }
                })
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();

        PubNubManager.with(this)
                .history()
                .historyCount(10)
                .callback(new OnResultListener<PNHistoryResult>() {
                    @Override
                    public void result(PNHistoryResult result, PNStatus status) {

                    }
                })
                .build();
        PubNubManager.with(this)
                .publish("Message", "channel")
                .callback(new OnResultListener<PNPublishResult>() {
                    @Override
                    public void result(PNPublishResult result, PNStatus status) {

                    }
                })
                .build();

        PubNubManager.with(this)
                .unSubScribe("channel")
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();

        PubNubManager.with(this)
                .unSubScribeAll()
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();
    }

}
