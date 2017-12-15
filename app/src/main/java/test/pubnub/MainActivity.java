package test.pubnub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnubutil.PubNubManager;
import com.pubnubutil.PubNubParam;
import com.pubnubutil.PubnubConfiguration;

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
                .subScribe()
                .channels(channels)
                .callback(new PubNubParam.OnPushMessageListener() {
                    @Override
                    public void result(String channel, Object result, PNStatus status, int taskId) {

                    }

                    @Override
                    public void status(String channel, PNStatus status) {

                    }

                    @Override
                    public void message(String channel, Object message) {

                    }

                    @Override
                    public void presence(String channel, PNPresenceEventResult presence) {

                    }


                }).build();
    }

}
