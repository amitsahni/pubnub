package com.pubnubutil;

import android.app.Activity;
import android.content.Context;


/**
 * The type Pub nub param.
 */
public class PubNubParam {
    /**
     * The Context.
     */
    protected Context context;
    /**
     * The Activity.
     */
    protected Activity activity;
    /**
     * The Publish key.
     */
    protected String publish_key, /**
     * The Subscribe key.
     */
    subscribe_key, /**
     * The Secret key.
     */
    secret_key, /**
     * The Cipher key.
     */
    cipher_key;
    /**
     * The Ssl on.
     */
    protected boolean ssl_on, /**
     * The Enable gcm.
     */
    enableGCM;
    /**
     * The Event.
     */
    protected Event event = Event.SUB;
    /**
     * The Channels.
     */
    protected String[] channels;
    /**
     * The Listener.
     */
    protected OnPushMessageListener listener;
    /**
     * Publish Message
     */
    protected Object message;

    protected String senderId;

    protected String uuid = "";

    /*History*/
    protected int count;

    protected boolean includeTimeToken = true;

    protected boolean reverse = false;

    protected Long start = 0L;

    protected Long end = 0L;

    /**
     * The enum Event.
     */
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

}
