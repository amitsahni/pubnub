package com.pubnub;

import android.app.Dialog;
import android.content.Context;

import com.pubnub.callback.OnResultListener;
import com.pubnub.callback.OnSubscribeListener;


/**
 * The type Pub nub param.
 */
public class PubNubParam {
    protected Context context;
    protected Event event = Event.SUB;
    protected String[] channels;
    protected Object message;
    protected String senderId;
    protected String uuid = "";

    /*History*/
    protected int count;
    protected boolean includeTimeToken = true;
    protected boolean reverse = false;
    protected Long start = 0L;
    protected Long end = 0L;
    protected Dialog dialog;

    enum Event {
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

    OnResultListener resultListener;
    OnSubscribeListener statusListener;
    OnSubscribeListener messageListener;
    OnSubscribeListener presenceListener;

}
