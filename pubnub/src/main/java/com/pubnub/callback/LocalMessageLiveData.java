package com.pubnub.callback;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.pubnub.PubNubConstant;
import com.pubnub.PubnubConfiguration;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;

public class LocalMessageLiveData extends LiveData<PushMessage> {
    private Context context;
    private String action;

    /**
     * @param context
     */
    public LocalMessageLiveData(Context context) {
        this.context = context;
        this.action = context.getPackageName() + ".local.pubnub";
    }

    @Override
    protected void onActive() {
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(action));
    }

    @Override
    protected void onInactive() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(PubNubConstant.BUNDLE_MESSAGE);
            if (message != null) {
                PNMessageResult pnMessageResult = PubnubConfiguration.gson().fromJson(message, PNMessageResult.class);
                PushMessage pushMessage = new PushMessage();
                pushMessage.channel = pnMessageResult.getChannel();
                pushMessage.message = pnMessageResult.getMessage().toString();
                postValue(pushMessage);
            }
        }
    };

}
