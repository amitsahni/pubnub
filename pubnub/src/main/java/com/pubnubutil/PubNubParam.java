package com.pubnubutil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.io.Serializable;


/**
 * The type Pub nub param.
 */
public class PubNubParam implements Serializable {
    protected Context context;
    protected Activity activity;
    protected Event event = Event.SUB;
    protected String[] channels;
    protected OnPushMessageListener listener;
    protected Object message;
    protected String senderId;
    protected String uuid = "";
    protected int taskId;

    /*History*/
    protected int count;
    protected boolean includeTimeToken = true;
    protected boolean reverse = false;
    protected Long start = 0L;
    protected Long end = 0L;
    protected Dialog dialog;

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
        void result(String channel, Object result, PNStatus status, int taskId);

        void status(String channel, PNStatus status);

        void message(String channel, Object message);

        void presence(String channel, PNPresenceEventResult presence);
    }

}
