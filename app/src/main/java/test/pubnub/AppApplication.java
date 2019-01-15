package test.pubnub;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.pubnub.PubnubConfiguration;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.callback.LocalMessageLiveData;
import com.pubnub.callback.PresenceLiveData;
import com.pubnub.callback.PushMessage;

/**
 * Created by clickapps on 1/9/17.
 */

public class AppApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        new PubnubConfiguration.Builder("pub-c-842a68c4-7244-481f-b0eb-16b31a600d52", "sub-c-5d0358dc-aaff-11e6-a397-02ee2ddab7fe")
                .gcm(true, "123456")
                .isSsl(false)
                .isDebug(true)
                .build();

        new LocalMessageLiveData(this)
                .observeForever(new Observer<PushMessage>() {
                    @Override
                    public void onChanged(@Nullable PushMessage pushMessage) {
                        Log.i(AppApplication.class.getSimpleName(), "onChanged = " + pushMessage.getMessage());
                    }
                });

        new PresenceLiveData(this).observeForever(new Observer<PushMessage>() {
            @Override
            public void onChanged(@Nullable PushMessage pushMessage) {
                Log.i(AppApplication.class.getSimpleName(), "PresenceLiveData = " + pushMessage.getMessage());
            }
        });
    }
}
