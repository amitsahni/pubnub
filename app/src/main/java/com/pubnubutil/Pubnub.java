package com.pubnubutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
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
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.presence.PNGetStateResult;
import com.pubnub.api.models.consumer.presence.PNHereNowResult;
import com.pubnub.api.models.consumer.presence.PNSetStateResult;
import com.pubnub.api.models.consumer.presence.PNWhereNowResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.push.PNPushAddChannelResult;
import com.pubnub.api.models.consumer.push.PNPushRemoveChannelResult;
import com.punubutil.BuildConfig;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;


/**
 * The type Pub nub.
 */
public class Pubnub {
    private static volatile PubNub sPubnub;

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
                    pnConfiguration.setSubscribeKey(pubNubParam.getSubscribe_key());
                    pnConfiguration.setPublishKey(pubNubParam.getPublish_key());
                    pnConfiguration.setSecure(false);
                    sPubnub = new com.pubnub.api.PubNub(pnConfiguration);
                    sPubnub.addListener(new PubNubCallback(pubNubParam));
                }
            }
        }
        if (pubNubParam.isEnableGCM()) {
            gcmRegister(pubNubParam);
        } else {
            //unRegisterInBackground(pubNubParam);
        }

    }

    private void gcmRegister(PubNubParam pubNubParam) {
        if (checkPlayServices(pubNubParam.getContext())) {
            String gcmRegId = "";
            gcmRegId = getRegistrationId(pubNubParam.getContext());
            if (gcmRegId.isEmpty()) {
                registerInBackground(pubNubParam);
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(getClass().getSimpleName(), "Registration ID already exists: " + gcmRegId);
                }
            }
        } else {
            if (BuildConfig.DEBUG) {
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
                    InstanceID instanceID = InstanceID.getInstance(pubNubParam.getContext());
                    token = instanceID.getToken(pubNubParam.getSenderId(),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    enablePushNotificationsOnChannel(token, pubNubParam);
                    storeRegistrationId(pubNubParam.getContext(), token);
                    msg = "Device registered, registration ID: " + token;
                    if (BuildConfig.DEBUG) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error :" + e.getMessage();
                    if (BuildConfig.DEBUG) {
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
                .channels(Arrays.asList(pubNubParam.getChannels()))
                .pushType(PNPushType.GCM)
                .deviceId(token)
                .async(new PNCallback<PNPushAddChannelResult>() {
                    @Override
                    public void onResponse(PNPushAddChannelResult result, PNStatus status) {
                        if (BuildConfig.DEBUG) {
                            Log.d(getClass().getSimpleName(), result.toString());
                        }
                    }
                });
    }

    private void disablePushNotificationsOnChannel(String token, PubNubParam pubNubParam) {
        sPubnub.removePushNotificationsFromChannels()
                .channels(Arrays.asList(pubNubParam.getChannels()))
                .pushType(PNPushType.GCM)
                .deviceId(token)
                .async(new PNCallback<PNPushRemoveChannelResult>() {
                    @Override
                    public void onResponse(PNPushRemoveChannelResult result, PNStatus status) {
                        if (BuildConfig.DEBUG) {
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
                    InstanceID instanceID = InstanceID.getInstance(pubNubParam.getContext());
                    instanceID.deleteToken(pubNubParam.getSenderId(),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                    token = getRegistrationId(pubNubParam.getContext());
                    disablePushNotificationsOnChannel(token, pubNubParam);
                    token = "";
                    storeRegistrationId(pubNubParam.getContext(), token);
                    msg = "Device unRegistered";
                    if (BuildConfig.DEBUG) {
                        Log.d(getClass().getSimpleName(), msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error :" + e.getMessage();
                    if (BuildConfig.DEBUG) {
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
                if (BuildConfig.DEBUG) {
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

    /**
     * Handle event.
     *
     * @param pubNubParam the pub nub param
     */
    public Object handleEvent(final PubNubParam pubNubParam) {
        switch (pubNubParam.getEvent()) {
            case SUB:
                sPubnub.subscribe()
                        .channels(Arrays.asList(pubNubParam.getChannels()))
                        .withPresence()
                        .execute();
                break;
            case UNSUB:
                sPubnub.unsubscribe()
                        .channels(Arrays.asList(pubNubParam.getChannels()))
                        .execute();
                if (BuildConfig.DEBUG) {
                    Log.d(getClass().getSimpleName(), "All SubScribed:" + sPubnub.getSubscribedChannels().toString());
                }
                break;
            case UNSUBALL:
                sPubnub.unsubscribeAll();
                break;
            case SUB_LIST:
                return sPubnub.getSubscribedChannels();
            case PUB:
                for (String channel : pubNubParam.getChannels()) {
                    sPubnub.publish().message(pubNubParam.getMessage())
                            .shouldStore(true)
                            .usePOST(true)
                            .channel(channel)
                            .async(new PNCallback<PNPublishResult>() {
                                @Override
                                public void onResponse(PNPublishResult result, PNStatus status) {
                                    if (status.isError()) {
                                        // handle error
                                        if (BuildConfig.DEBUG) {
                                            Log.d(getClass().getSimpleName(), "Publish:" + status.toString());
                                        }
                                        return;
                                    }
                                    if (pubNubParam.getListener() != null) {
                                        pubNubParam.getListener().onSuccess(status.toString(), result);
                                    }
                                }
                            });
                }
                break;
            case ENABLE_GCM:
                gcmRegister(pubNubParam);
                break;
            case DISABLE_GCM:
                unRegisterInBackground(pubNubParam);
                break;
            case CHAT_HISTORY:
                if (pubNubParam.getDialog() != null &&
                        !pubNubParam.getDialog().isShowing()) {
                    pubNubParam.getDialog().show();
                }
                sPubnub.history()
                        .channel(pubNubParam.getChannels()[0])
                        .count(pubNubParam.getCount() > 100 ? 100 : pubNubParam.getCount())
                        .includeTimetoken(pubNubParam.isIncludeTimeToken())
                        .reverse(pubNubParam.isReverse())
                        .start(pubNubParam.getStart() > 0L ? pubNubParam.getStart() : null)
                        .end(pubNubParam.getEnd() > 0L ? pubNubParam.getEnd() : null)
                        .async(new PNCallback<PNHistoryResult>() {
                            @Override
                            public void onResponse(PNHistoryResult result, PNStatus status) {
                                if (pubNubParam.getDialog() != null
                                        && pubNubParam.getDialog().isShowing()) {
                                    pubNubParam.getDialog().dismiss();
                                }
                                if (status.isError()) {
                                    // handle error
                                    if (BuildConfig.DEBUG) {
                                        Log.d(getClass().getSimpleName(), "Publish:" + status.toString());
                                    }
                                    return;
                                }
                                if (pubNubParam.getListener() != null) {
                                    pubNubParam.getListener().onSuccess(status.toString(), result);
                                }
                            }
                        });
                break;
            case HERE_NOW:
                sPubnub.hereNow()
                        // tailor the next two lines to example
                        .channels(Arrays.asList(pubNubParam.getChannels()))
                        .includeUUIDs(true)
                        .includeState(true)
                        .async(new PNCallback<PNHereNowResult>() {
                            @Override
                            public void onResponse(PNHereNowResult result, PNStatus status) {
                                if (status.isError()) {
                                    // handle error
                                    return;
                                }
                                if (pubNubParam.getListener() != null) {
                                    pubNubParam.getListener().onSuccess(status.toString(), result);
                                }
                            }
                        });
                break;
            case WHERE_NOW:
                sPubnub.whereNow()
                        .uuid(pubNubParam.getUuid())
                        .async(new PNCallback<PNWhereNowResult>() {
                            @Override
                            public void onResponse(PNWhereNowResult result, PNStatus status) {
                                if (status.isError()) {
                                    // handle error
                                    return;
                                }
                                if (pubNubParam.getListener() != null) {
                                    pubNubParam.getListener().onSuccess(status.toString(), result);
                                }
                            }
                        });
                break;
            case GET_PRESENCE:
                sPubnub.getPresenceState()
                        .channels(Arrays.asList(pubNubParam.getChannels())) // channels to fetch state for
                        .uuid(pubNubParam.getUuid()) // uuid of user to fetch, or omit own uuid
                        .async(new PNCallback<PNGetStateResult>() {
                            @Override
                            public void onResponse(PNGetStateResult result, PNStatus status) {
                                if (status.isError()) {
                                    // handle error
                                    return;
                                }
                                if (pubNubParam.getListener() != null) {
                                    pubNubParam.getListener().onSuccess(status.toString(), result);
                                }
                            }
                        });
                break;
            case SET_PRESENCE:
                sPubnub.setPresenceState()
                        .channels(Arrays.asList(pubNubParam.getChannels()))  // apply on those channel groups
                        .state(pubNubParam.getMessage()) // the new state
                        .async(new PNCallback<PNSetStateResult>() {
                            @Override
                            public void onResponse(PNSetStateResult result, PNStatus status) {
                                if (status.isError()) {
                                    // handle error
                                    return;
                                }
                                if (pubNubParam.getListener() != null) {
                                    pubNubParam.getListener().onSuccess(status.toString(), result);
                                }
                            }
                        });
                break;
            default:
                break;
        }
        return null;
    }

    private class PubNubCallback extends SubscribeCallback {
        private PubNubParam pubNubParam;
        private static final String FORMAT = "Channel : {0}\nMessage = {1}";

        private PubNubCallback(PubNubParam param) {
            this.pubNubParam = param;
        }

        @Override
        public void status(com.pubnub.api.PubNub pubnub, PNStatus status) {
//            ApplicationUtils.Log.i(MessageFormat.format(FORMAT, status.getStatusCode(), status.getErrorData().getInformation()));
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
                            if (BuildConfig.DEBUG) {
                                Log.d(getClass().getSimpleName(), "PNConnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNReconnectedCategory:
                            // this usually occurs if subscribe temporarily fails but reconnects. This means
                            // there was an error but there is no longer any issue
                            if (BuildConfig.DEBUG) {
                                Log.d(getClass().getSimpleName(), "PNReconnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNDisconnectedCategory:
                            // this is the expected category for an unsubscribe. This means there
                            // was no error in unsubscribing from everything
                            if (BuildConfig.DEBUG) {
                                Log.d(getClass().getSimpleName(), "PNDisconnectedCategory = " + status.getAffectedChannels());
                            }
                            break;
                        case PNUnexpectedDisconnectCategory:
                            // this is usually an issue with the internet connection, this is an error, handle appropriately
                            break;
                        case PNAccessDeniedCategory:
                            // this means that PAM does allow this client to subscribe to this
                            // channel and channel group configuration. This is another explicit error
                            break;
                        case PNAcknowledgmentCategory:
                            if (BuildConfig.DEBUG) {
                                Log.d(getClass().getSimpleName(), "PNAcknowledgmentCategory = " + status.getAffectedChannels());
                            }
                            break;
                        default:
                            // More errors can be directly specified by creating explicit cases for other
                            // error categories of `PNStatusCategory` such as `PNTimeoutCategory` or `PNMalformedFilterExpressionCategory` or `PNDecryptionErrorCategory`
                            if (BuildConfig.DEBUG) {
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
            if (BuildConfig.DEBUG) {
                Log.d(getClass().getSimpleName(), "Message = " + MessageFormat.format(FORMAT, message.getChannel(), message.getMessage().toString()));
            }
            if (pubNubParam.getListener() != null) {
                pubNubParam.getListener().onSuccess(message.getChannel(), message.getMessage());
            }
            if (pubNubParam.getContext() != null) {

                pubNubParam.getContext().sendBroadcast(
                        new Intent(PubNubConstant.BROADCAST)
                                .putExtra(PubNubConstant.BUNDLE_MESSAGE, message.getMessage().toString())
                                .putExtra(PubNubConstant.BUNDLE_CHANNEL, message.getChannel()));
                // Local Broadcast
                //send broadcast for application
                LocalBroadcastManager.getInstance(pubNubParam.getContext())
                        .sendBroadcast(new Intent(PubNubConstant.BROADCAST)
                                .putExtra(PubNubConstant.BUNDLE_MESSAGE, message.getMessage().toString())
                                .putExtra(PubNubConstant.BUNDLE_CHANNEL, message.getChannel()));

            }
            if (BuildConfig.DEBUG) {
                Log.i("------", "---------------------------------------------------------------------------");
            }
        }

        @Override
        public void presence(com.pubnub.api.PubNub pubnub, PNPresenceEventResult presence) {
//            ApplicationUtils.Log.i(MessageFormat.format(FORMAT, presence.getChannel(), presence.getState().toString()));
        }
    }
}
