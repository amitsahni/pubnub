package com.pubnubutil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Pub nub param.
 */
public class PubNubParam implements Serializable {
    private Context context;
    private Activity activity;
    private String publish_key,
            subscribe_key,
            secret_key,
            cipher_key;
    private boolean ssl_on,
            enableGCM;
    private Event event = Event.SUB;
    private String[] channels;
    private OnPushMessageListener listener;
    private Object message;
    private String senderId;
    private String uuid = "";

    /*History*/
    private int count;
    private boolean includeTimeToken = true;
    private boolean reverse = false;
    private Long start = 0L;
    private Long end = 0L;
    private Dialog dialog;

    public enum Event {
        /**
         * Sub event.
         */
        SUB, /**
         * Unsub event.
         */
        UNSUB, /**
         * Unsuball event.
         */
        UNSUBALL,
        /**
         * Get SubList
         */
        SUB_LIST,
        /**
         * PUB
         */
        PUB,
        /**
         * EnableGCM
         */
        ENABLE_GCM,
        /**
         * Disable GCM
         */
        DISABLE_GCM,
        /**
         * CHAT_HISTORY
         */
        CHAT_HISTORY,
        /**
         *
         */
        HERE_NOW,
        /**
         *
         */
        WHERE_NOW,
        /**
         *
         */
        GET_PRESENCE,
        /**
         *
         */
        SET_PRESENCE
    }

    /**
     * The interface On push message listener.
     */
    public interface OnPushMessageListener {
        /**
         * On success.
         *
         * @param channel the channel
         * @param data    the data
         */
        void onSuccess(String channel, Object data);

        /**
         * On failure.
         *
         * @param channel   the channel
         * @param exception the exception
         */
// If there is an error, don't just keep trying to register.
        void onFailure(String channel, String exception);
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getPublish_key() {
        return publish_key;
    }

    public String getSubscribe_key() {
        return subscribe_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public String getCipher_key() {
        return cipher_key;
    }

    public boolean isSsl_on() {
        return ssl_on;
    }

    public boolean isEnableGCM() {
        return enableGCM;
    }

    public Event getEvent() {
        return event;
    }

    public String[] getChannels() {
        return channels;
    }

    public OnPushMessageListener getListener() {
        return listener;
    }

    public Object getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getUuid() {
        return uuid;
    }

    public int getCount() {
        return count;
    }

    public boolean isIncludeTimeToken() {
        return includeTimeToken;
    }

    public boolean isReverse() {
        return reverse;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * The type Builder.
     */
    public static class Builder {

        private PubNubParam pubNubParam;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(@NonNull Context context) {
            pubNubParam = new PubNubParam();
            pubNubParam.context = context;
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

        public Builder progressDialog(@NonNull Dialog progressDialog) {
            pubNubParam.dialog = progressDialog;
            return this;
        }

        public History fetchHistory() {
            return new History(pubNubParam);
        }

        public PubNubParam getParam() {
            return pubNubParam;
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


    public static class History {
        PubNubParam pubNubParam;

        public History(PubNubParam param) {
            pubNubParam = param;
        }

        public History historyCount(int count) {
            pubNubParam.count = count;
            return this;
        }

        public History includeTimeToken(boolean includeTimeToken) {
            pubNubParam.includeTimeToken = includeTimeToken;
            return this;
        }

        public History isHistoryReverse(boolean reverse) {
            pubNubParam.reverse = reverse;
            return this;
        }

        public History startDate(Long start) {
            pubNubParam.start = start;
            return this;
        }

        public History endDate(Long end) {
            pubNubParam.end = end;
            return this;
        }

        public PubNubParam getParam() {
            return pubNubParam;
        }

        /**
         * Build.
         */
        public void build() {
            Pubnub pubNub = new Pubnub(pubNubParam);
            pubNub.handleEvent(pubNubParam);
        }
    }

}
