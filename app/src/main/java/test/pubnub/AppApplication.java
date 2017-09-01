package test.pubnub;

import android.app.Application;

import com.pubnubutil.PubnubConfiguration;

/**
 * Created by clickapps on 1/9/17.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PubnubConfiguration.Builder().keys("publishKey","subscribeKey")
                .gcm(true,"senderId")
                .isSsl(false)
                .build();
    }
}
