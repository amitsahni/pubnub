package com.pubnub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNPushType;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.push.PNPushAddChannelResult;
import com.pubnub.api.models.consumer.push.PNPushRemoveChannelResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;


/**
 * The type Pub nub.
 */
public class Pubnub {
    private static volatile PubNub sPubnub;
    private static volatile PubNubCallback sPubNubCallback;

    private Pubnub() {

    }


    /**
     * Instantiates a new Pub nub.
     *
     * @param pubNubParam the pub nub param
     */
    public Pubnub(PubNubParam pubNubParam) {
        if (sPubnub == null) {
            synchronized (Pubnub.class) {
                if (sPubnub == null) {
                    PNConfiguration pnConfiguration = new PNConfiguration();
                    pnConfiguration.setSubscribeKey(PubnubConfiguration.getSubscribe_key());
                    pnConfiguration.setPublishKey(PubnubConfiguration.getPublish_key());
                    pnConfiguration.setSecure(PubnubConfiguration.isSsl_on());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(PubnubConfiguration.isDebuggable() ?
                            HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
                    pnConfiguration.setHttpLoggingInterceptor(httpLoggingInterceptor);
                    sPubnub = new com.pubnub.api.PubNub(pnConfiguration);
                    sPubNubCallback = new PubNubCallback();
                    sPubnub.addListener(sPubNubCallback);
                }
            }
        }
        sPubNubCallback.addPubNubParam(pubNubParam);
        if (PubnubConfiguration.isEnableGCM()) {
            gcmRegister(pubNubParam);
        } else {
            //unRegisterInBackground(pubNubParam);
        }

    }

    public static PubNub getPubnub() {
        return sPubnub;
    }

    private void gcmRegister(PubNubParam pubNubParam) {
        if (checkPlayServices(pubNubParam.context)) {
            String gcmRegId = "";
            gcmRegId = getRegistrationId(pubNubParam.context);
            if (gcmRegId.isEmpty()) {
                registerInBackground(pubNubParam);
            } else {
                if (PubnubConfiguration.isDebuggable()) {
                    Log.d(getClass().getSimpleName(), "Registration ID already exists: " + gcmRegId);
                }
            }
        } else {
            if (PubnubConfiguration.isDebuggable()) {
                Log.e(getClass().getSimpleName(), "No valid Google Play Services APK found.");
            }
        }
    }

    private void registerInBackground(final PubNubParam pubNubParam) {
        new AsyncTask() {

            @Override
            protected String doInBackground(Object[] objects) {
                String token = "";
                String msg = "";
                try {
                    InstanceID instanceID = InstanceID.getInstance(pubNubParam.context);
                    token = instanceID.getToken(pubNubParam.senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    enablePushNotificationsOnChannel(token, pubNubParam);
                    storeRegistrationId(pubNubParam.context, token);
                    msg = "Device registered, registration ID: " + token;
                    if (PubnubConfiguration.isDebuggable()) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error :" + e.getMessage();
                    if (PubnubConfiguration.isDebuggable()) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                }
                return msg;
            }
        }.execute();
    }

    private void storeRegistrationId(@NonNull Context context, String regId) {
        SharedPreferences prefs = context.getSharedPreferences(PubNubConstant.CHAT_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PubNubConstant.GCM_REG_ID, regId);
        editor.apply();
    }

    private String getRegistrationId(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PubNubConstant.CHAT_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(PubNubConstant.GCM_REG_ID, "");
    }

    private void enablePushNotificationsOnChannel(String token, PubNubParam pubNubParam) {
        sPubnub.addPushNotificationsOnChannels()
                .channels(pubNubParam.channels == null ? new ArrayList<String>() : Arrays.asList(pubNubParam.channels))
                .pushType(PNPushType.GCM)
                .deviceId(token)
                .async(new PNCallback<PNPushAddChannelResult>() {
                    @Override
                    public void onResponse(PNPushAddChannelResult result, PNStatus status) {
                        if (PubnubConfiguration.isDebuggable()) {
                            Log.d(getClass().getSimpleName(), result.toString());
                        }
                    }
                });
    }

    private void disablePushNotificationsOnChannel(String token, PubNubParam pubNubParam) {
        sPubnub.removePushNotificationsFromChannels()
                .channels(pubNubParam.channels == null ? new ArrayList<String>() : Arrays.asList(pubNubParam.channels))
                .pushType(PNPushType.GCM)
                .deviceId(token)
                .async(new PNCallback<PNPushRemoveChannelResult>() {
                    @Override
                    public void onResponse(PNPushRemoveChannelResult result, PNStatus status) {
                        if (PubnubConfiguration.isDebuggable()) {
                            Log.d(getClass().getSimpleName(), result.toString());
                        }
                    }
                });
    }

    private void unRegisterInBackground(final PubNubParam pubNubParam) {
        new AsyncTask() {

            @Override
            protected String doInBackground(Object[] objects) {
                String token = "";
                String msg = "";
                try {
                    InstanceID instanceID = InstanceID.getInstance(pubNubParam.context);
                    instanceID.deleteToken(pubNubParam.senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                    token = getRegistrationId(pubNubParam.context);
                    disablePushNotificationsOnChannel(token, pubNubParam);
                    token = "";
                    storeRegistrationId(pubNubParam.context, token);
                    msg = "Device unRegistered";
                    if (PubnubConfiguration.isDebuggable()) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error :" + e.getMessage();
                    if (PubnubConfiguration.isDebuggable()) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                }
                return msg;
            }
        }.execute();
    }


    private boolean checkPlayServices(@NonNull Context context) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                if (context instanceof Activity) {
                    googleAPI.getErrorDialog((Activity) context, result,
                            PubNubConstant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                }
            } else {
                if (PubnubConfiguration.isDebuggable()) {
                    Log.d(getClass().getSimpleName(), "This device is not supported.");
                }
            }

            return false;
        }
        return true;

    }

    /**
     * Gets pub nub.
     *
     * @return the pub nub
     */
    public com.pubnub.api.PubNub getPubNub() {
        return sPubnub;
    }


    @SuppressWarnings("unchecked")
    private class PubNubCallback extends SubscribeCallback {
        private PubNubParam pubNubParam;
        private List<PubNubParam> list = new ArrayList<>();
        private static final String FORMAT = "Channel : {0}\nMessage = {1}";

        private PubNubCallback() {
        }

        public void addPubNubParam(PubNubParam param) {
            this.pubNubParam = param;
            if (pubNubParam.messageListener != null
                    || pubNubParam.presenceListener != null
                    || pubNubParam.resultListener != null) {
                list.add(pubNubParam);
            }
            Log.d("list", "" + list.size());
        }

        @Override
        public void status(com.pubnub.api.PubNub pubnub, PNStatus status) {
            for (PubNubParam pubNubParam : list) {
                if (pubNubParam.statusListener != null) {
                    String channel = TextUtils.join(",", status.getAffectedChannels());
                    pubNubParam.statusListener.result(channel, status);
                }
            }
            if (PubnubConfiguration.isDebuggable()) {
                Log.d("status", "---------------------------------------------------------------------------");
            }
            // the status object returned is always related to subscribe but could contain
            // information about subscribe, heartbeat, or errors
            // use the operationType to switch on different options
            switch (status.getOperation()) {
                // let's combine unsubscribe and subscribe handling for ease of use
                case PNSubscribeOperation:
                case PNUnsubscribeOperation:
                    // note: subscribe statuses never have traditional
                    // errors, they just have categories to represent the
                    // different issues or successes that occur as part of subscribe
                    switch (status.getCategory()) {
                        case PNConnectedCategory:
                            // this is expected for a subscribe, this means there is no error or issue whatsoever
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "PNConnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNReconnectedCategory:
                            // this usually occurs if subscribe temporarily fails but reconnects. This means
                            // there was an error but there is no longer any issue
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "PNReconnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNDisconnectedCategory:
                            // this is the expected category for an unsubscribe. This means there
                            // was no error in unsubscribing from everything
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "PNDisconnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNUnexpectedDisconnectCategory:
                            // this is usually an issue with the internet connection, this is an error, handle appropriately
                            pubnub.reconnect();
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "PNDisconnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNAccessDeniedCategory:
                            // this means that PAM does allow this client to subscribe to this
                            // channel and channel group configuration. This is another explicit error
                            break;
                        case PNAcknowledgmentCategory:
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "PNAcknowledgmentCategory = " + status.getAffectedChannels());
                            }
                            break;
                        default:
                            // More errors can be directly specified by creating explicit cases for other
                            // error categories of `PNStatusCategory` such as `PNTimeoutCategory` or `PNMalformedFilterExpressionCategory` or `PNDecryptionErrorCategory`
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.d(getClass().getSimpleName(), "default = " + status.getAffectedChannels());
                            }
                            break;
                    }
                    break;
                case PNHeartbeatOperation:
                    // heartbeat operations can in fact have errors, so it is important to check first for an error.
                    // For more information on how to configure heartbeat notifications through the status
                    // PNObjectEventListener callback, consult <link to the PNCONFIGURATION heartbeart config>
                    if (status.isError()) {
                        // There was an error with the heartbeat operation, handle here
                    } else {
                        // heartbeat operation was successful
                    }
                    break;
                default: {
                    // Encountered unknown status type
                }
            }
        }

        @Override
        public void message(com.pubnub.api.PubNub pubnub, PNMessageResult message) {
            if (pubNubParam.context != null) {

                pubNubParam.context.sendBroadcast(
                        new Intent(PubNubConstant.BROADCAST)
                                .putExtra(PubNubConstant.BUNDLE_MESSAGE, message.getMessage().toString())
                                .putExtra(PubNubConstant.BUNDLE_CHANNEL, message.getChannel()));
                // Local Broadcast
                //send broadcast for application
                LocalBroadcastManager.getInstance(pubNubParam.context)
                        .sendBroadcast(new Intent(PubNubConstant.LOCAL_BROADCAST)
                                .putExtra(PubNubConstant.BUNDLE_MESSAGE, message.getMessage().toString())
                                .putExtra(PubNubConstant.BUNDLE_CHANNEL, message.getChannel()));

            }
            for (PubNubParam pubNubParam : list) {
                if (pubNubParam.messageListener != null) {
                    pubNubParam.messageListener.result(message.getChannel(), message);
                }
            }
            if (PubnubConfiguration.isDebuggable()) {
                Log.d("message", "---------------------------------------------------------------------------");
            }
        }

        @Override
        public void presence(com.pubnub.api.PubNub pubnub, PNPresenceEventResult presence) {
            if (PubnubConfiguration.isDebuggable()) {
                Log.d("presence", "---------------------------------------------------------------------------");
            }
            for (PubNubParam pubNubParam : list) {
                if (pubNubParam.presenceListener != null) {
                    pubNubParam.presenceListener.result(presence.getChannel(), presence);
                }
            }
        }
    }
}
