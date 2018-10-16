package com.pubnub.callback;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.pubnub.PubNubConstant;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

public class PresenceLiveData extends LiveData<PNPresenceEventResult> {
    private Context context;
    private String action;

    /**
     * @param context
     */
    public PresenceLiveData(Context context) {
        this.context = context;
        this.action = context.getPackageName() + ".pubnub.presence";
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
                PNPresenceEventResult pnPresenceEventResult = new Gson().fromJson(message, PNPresenceEventResult.class);
                postValue(pnPresenceEventResult);
            }

        }
    };

}
