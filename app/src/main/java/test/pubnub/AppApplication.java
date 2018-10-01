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
        new PubnubConfiguration.Builder("pub-c-89c02195-71ef-4c21-b9c9-074f90e4a575", "sub-c-996e94c8-9c87-11e8-b7e1-625ee96eadaa")
                //  .gcm(true, "senderId")
                .isSsl(false)
                .isDebug(true)
                .build();
    }
}
