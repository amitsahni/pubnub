package test.pubnub;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pubnub.PubnubConfiguration;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.callback.MessageLiveData;

/**
 * Created by clickapps on 1/9/17.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PubnubConfiguration.Builder("pub-c-89c02195-71ef-4c21-b9c9-074f90e4a575", "sub-c-996e94c8-9c87-11e8-b7e1-625ee96eadaa")
                //  .gcm(true, "senderId")
                .isSsl(false)
                .isDebug(false)
                .build();

        new MessageLiveData(this, getPackageName())
                .observeForever(new Observer<PNMessageResult>() {
                    @Override
                    public void onChanged(@NonNull PNMessageResult pnMessageResult) {
                        Log.i(AppApplication.class.getSimpleName(), "onChanged = " + pnMessageResult.getMessage().getAsString());
                    }
                });
    }
}
