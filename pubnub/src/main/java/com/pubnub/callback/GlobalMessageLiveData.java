package com.pubnub.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.pubnub.PubNubConstant;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;

public class GlobalMessageLiveData extends MutableLiveData<PNMessageResult> {

    private static volatile GlobalMessageLiveData instance;

    private GlobalMessageLiveData() {

    }

    public static GlobalMessageLiveData getInstance() {
        if (instance == null) {
            synchronized (GlobalMessageLiveData.class) {
                if (instance == null) {
                    instance = new GlobalMessageLiveData();
                }
            }
        }
        return instance;
    }

    @Deprecated
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<PNMessageResult> observer) {
        super.observe(owner, observer);
    }
}
