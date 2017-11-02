package com.pubnubutil;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pubnubutil.di.IPubnubProperty;

import java.util.List;

/**
 * Created by clickapps on 2/11/17.
 */

public class Builder implements IPubnubProperty {

    private PubNubParam pubNubParam;


    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     */
    public Builder(@NonNull Context context) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        PubNubConstant.BROADCAST = context.getPackageName() + ".pubnub";
        PubNubConstant.LOCAL_BROADCAST = context.getPackageName() + ".local.pubnub";
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     */
    public Builder(@NonNull Activity context) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        pubNubParam.activity = context;
        PubNubConstant.BROADCAST = context.getPackageName() + ".pubnub";
        PubNubConstant.LOCAL_BROADCAST = context.getPackageName() + ".local.pubnub";
    }

    @Override
    public List<String> getScribeList() {
        pubNubParam.event = PubNubParam.Event.SUB_LIST;
        Pubnub pubNub = new Pubnub(pubNubParam);
        return (List<String>) pubNub.handleEvent(pubNubParam);
    }

    @Override
    public SubScribeBuilder subScribe() {
        pubNubParam.event = PubNubParam.Event.SUB;
        return new SubScribeBuilder(pubNubParam);
    }

    @Override
    public SubScribeBuilder unSubScribe() {
        pubNubParam.event = PubNubParam.Event.UNSUB;
        return new SubScribeBuilder(pubNubParam);
    }

    @Override
    public SubScribeBuilder unSubScribeAll() {
        pubNubParam.event = PubNubParam.Event.UNSUBALL;
        return new SubScribeBuilder(pubNubParam);
    }

    @Override
    public PublishBuilder publish() {
        pubNubParam.event = PubNubParam.Event.PUB;
        return new PublishBuilder(pubNubParam);
    }

    @Override
    public HistoryBuilder history() {
        pubNubParam.event = PubNubParam.Event.CHAT_HISTORY;
        return new HistoryBuilder(pubNubParam);
    }

    @Override
    public void enableGCM() {
        pubNubParam.event = PubNubParam.Event.ENABLE_GCM;
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }

    @Override
    public void disableGCM() {
        pubNubParam.event = PubNubParam.Event.ENABLE_GCM;
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }
}
