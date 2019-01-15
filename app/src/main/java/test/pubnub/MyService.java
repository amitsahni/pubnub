package test.pubnub;

import android.app.Service;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.pubnub.PubNubConstant;
import com.pubnub.PubNubManager;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.callback.LocalMessageLiveData;
import com.pubnub.callback.PushMessage;

public class MyService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(MyService.class.getSimpleName(), "onStartCommand");
        PubNubManager.with(this)
                .subScribe("jisr_10979_user")
                .build();
        LocalMessageLiveData messageLiveData = new LocalMessageLiveData(this);
        messageLiveData.observeForever(new Observer<PushMessage>() {
            @Override
            public void onChanged(@NonNull PushMessage pnMessageResult) {
                Log.i(MyService.class.getSimpleName(), "Channel Observe = " + pnMessageResult.getChannel());
                Log.i(MyService.class.getSimpleName(), "Message Observe = " + pnMessageResult.getMessage());
            }
        });
        return START_STICKY;
    }
}
