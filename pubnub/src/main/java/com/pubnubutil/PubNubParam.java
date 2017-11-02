package com.pubnubutil;

import android.app.Activity;
import android.app.Dialog;
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
    protected Context context;
    protected Activity activity;
    protected Event event = Event.SUB;
    protected String[] channels;
    protected OnPushMessageListener listener;
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

//    public Context getContext() {
//        return context;
//    }
//
//    public Activity getActivity() {
//        return activity;
//    }
//
//    public Event getEvent() {
//        return event;
//    }
//
//    public String[] getChannels() {
//        return channels;
//    }
//
//    public OnPushMessageListener getListener() {
//        return listener;
//    }
//
//    public Object getMessage() {
//        return message;
//    }
//
//    public String getSenderId() {
//        return senderId;
//    }
//
//    public String getUuid() {
//        return uuid;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public boolean isIncludeTimeToken() {
//        return includeTimeToken;
//    }
//
//    public boolean isReverse() {
//        return reverse;
//    }
//
//    public Long getStart() {
//        return start;
//    }
//
//    public Long getEnd() {
//        return end;
//    }
//
//    public Dialog getDialog() {
//        return dialog;
//    }
}
