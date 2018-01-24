package com.pubnub;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by clickapps on 2/11/17.
 */

public class Builder {

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

    /***********************************/
    public Request.HistoryBuilder history() {
        pubNubParam.event = PubNubParam.Event.CHAT_HISTORY;
        return new Request.HistoryBuilder(pubNubParam);
    }

    /***********************************/
    public Request.PublishBuilder publish(@NonNull Object message, @NonNull String... channels) {
        pubNubParam.event = PubNubParam.Event.PUB;
        pubNubParam.message = message;
        pubNubParam.channels = channels;
        return new Request.PublishBuilder(pubNubParam);
    }

    public Request.PublishBuilder publish(@NonNull Object message) {
        pubNubParam.event = PubNubParam.Event.PUB;
        pubNubParam.message = message;
        return new Request.PublishBuilder(pubNubParam);
    }

    /***********************************/
    public Request.SubscribeBuilder subScribe(@NonNull String... channels) {
        pubNubParam.event = PubNubParam.Event.SUB;
        pubNubParam.channels = channels;
        return new Request.SubscribeBuilder(pubNubParam);
    }

    public Request.SubscribeBuilder subScribe() {
        pubNubParam.event = PubNubParam.Event.SUB;
        return new Request.SubscribeBuilder(pubNubParam);
    }

    /***********************************/
    public Request.UnSubscribeBuilder unSubScribe(@NonNull String... channels) {
        pubNubParam.event = PubNubParam.Event.UNSUB;
        pubNubParam.channels = channels;
        return new Request.UnSubscribeBuilder(pubNubParam);
    }

    public Request.UnSubscribeBuilder unSubScribe() {
        pubNubParam.event = PubNubParam.Event.UNSUB;
        return new Request.UnSubscribeBuilder(pubNubParam);
    }

    public Request.UnSubscribeAllBuilder unSubScribeAll() {
        pubNubParam.event = PubNubParam.Event.UNSUBALL;
        return new Request.UnSubscribeAllBuilder(pubNubParam);
    }

    /***********************************/
    private Request.HereNowBuilder hereNow() {
        pubNubParam.event = PubNubParam.Event.HERE_NOW;
        return new Request.HereNowBuilder(pubNubParam);
    }

    private Request.HereNowBuilder whereNow() {
        pubNubParam.event = PubNubParam.Event.WHERE_NOW;
        return new Request.HereNowBuilder(pubNubParam);
    }

    /***********************************/
    private Request.GetPresenceBuilder getPresence() {
        pubNubParam.event = PubNubParam.Event.GET_PRESENCE;
        return new Request.GetPresenceBuilder(pubNubParam);
    }

    private Request.HereNowBuilder setPresence() {
        pubNubParam.event = PubNubParam.Event.SET_PRESENCE;
        return new Request.HereNowBuilder(pubNubParam);
    }

}
