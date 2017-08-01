package com.pubnubutil;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Builder.
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
        defaultParam();
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
        defaultParam();
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     * @param event   the event
     */
    public Builder(@NonNull Context context, @NonNull PubNubParam.Event event) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        pubNubParam.event = event;
        defaultParam();
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     * @param event   the event
     */
    public Builder(@NonNull Activity context, @NonNull PubNubParam.Event event) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        pubNubParam.activity = context;
        pubNubParam.event = event;
        defaultParam();
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context       the context
     * @param publish_key   the publish key
     * @param subscribe_key the subscribe key
     * @param secret_key    the secret key
     * @param cipher_key    the cipher key
     * @param ssl_on        the ssl on
     */
    public Builder(@NonNull Context context, String publish_key, String subscribe_key, String secret_key,
                   String cipher_key, boolean ssl_on) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        pubNubParam.secret_key = secret_key;
        pubNubParam.cipher_key = cipher_key;
        pubNubParam.subscribe_key = subscribe_key;
        pubNubParam.publish_key = publish_key;
        pubNubParam.ssl_on = ssl_on;
    }

    /**
     * Instantiates a new Builder.
     *
     * @param context       the context
     * @param publish_key   the publish key
     * @param subscribe_key the subscribe key
     * @param secret_key    the secret key
     * @param ssl_on        the ssl on
     */
    public Builder(@NonNull Activity context, String publish_key, String subscribe_key, String secret_key,
                   boolean ssl_on) {
        pubNubParam = new PubNubParam();
        pubNubParam.context = context;
        pubNubParam.activity = context;
        pubNubParam.secret_key = secret_key;
        pubNubParam.subscribe_key = subscribe_key;
        pubNubParam.publish_key = publish_key;
        pubNubParam.ssl_on = ssl_on;
    }

    /**
     * Default param.
     */
    public void defaultParam() {
        pubNubParam.secret_key = PubNubConstant.SECRET_KEY;
        pubNubParam.cipher_key = PubNubConstant.CIPHER_KEY;
        pubNubParam.subscribe_key = PubNubConstant.SUBSCRIBE_KEY;
        pubNubParam.publish_key = PubNubConstant.PUBLISH_KEY;
        pubNubParam.ssl_on = true;
    }

    /**
     * Enable gcm builder.
     *
     * @param enableGCM the enable gcm
     * @return the builder
     */
    public Builder enableGCM(boolean enableGCM, String senderId) {
        pubNubParam.enableGCM = enableGCM;
        pubNubParam.senderId = senderId;
        return this;
    }

    /**
     * Callback builder.
     *
     * @param callback the callback
     * @return the builder
     */
    public Builder callback(@NonNull PubNubParam.OnPushMessageListener callback) {
        pubNubParam.listener = callback;
        return this;
    }

    /**
     * Event builder.
     *
     * @param event the event
     * @return the builder
     */
    public Builder event(@NonNull PubNubParam.Event event) {
        pubNubParam.event = event;
        return this;
    }

    /**
     * Channels builder.
     *
     * @param channels the channels
     * @return the builder
     */
    public Builder channels(@NonNull String[] channels) {
        pubNubParam.channels = channels;
        return this;
    }

    /**
     * Channels builder.
     *
     * @param channels the channels
     * @return the builder
     */
// comma seprate
    public Builder channels(@NonNull String channels) {
        pubNubParam.channels = TextUtils.split(channels, ",");
        return this;
    }

    public Builder message(@NonNull Object message) {
        pubNubParam.message = message;
        return this;
    }

    /**
     * Channels builder.
     *
     * @param channels the channels
     * @return the builder
     */
    public Builder channels(@NonNull List<String> channels) {
        List<String> list = new ArrayList<>(channels);
        if (!list.isEmpty()) {
            String[] temp = new String[list.size()];
            temp = list.toArray(temp);
            pubNubParam.channels = temp;
        }
        return this;
    }

    public History fetchHistory(){
        return new History(pubNubParam);
    }

    /**
     * Build.
     */
    public void build() {
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }

    public List<String> getSubscribedList() {
        pubNubParam.event = PubNubParam.Event.SUB_LIST;
        Pubnub pubNub = new Pubnub(pubNubParam);
        return (List<String>) pubNub.handleEvent(pubNubParam);
    }
}
