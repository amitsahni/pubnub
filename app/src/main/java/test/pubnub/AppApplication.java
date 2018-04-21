package test.pubnub;

import android.app.Application;

import com.pubnub.PubnubConfiguration;

/**
 * Created by clickapps on 1/9/17.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PubnubConfiguration.Builder("", "sub-c-67844f3e-3bca-11e8-a2e8-d2288b7dcaaf")
              //  .gcm(true, "senderId")
                .isSsl(false)
                .isDebug(true)
                .build();
    }
}
