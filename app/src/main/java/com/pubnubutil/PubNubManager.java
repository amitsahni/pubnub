package com.pubnubutil;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;


/**
 * The type Pub nub manager.
 */
public class PubNubManager {
    private static volatile PubNubManager sPubNubManager;

    private PubNubManager() {

    }

    /**
     * Get pub nub manager.
     *
     * @return the pub nub manager
     */
    public static PubNubManager get() {
        if (sPubNubManager == null) {
            synchronized (PubNubManager.class) {
                if (sPubNubManager == null) {
                    sPubNubManager = new PubNubManager();
                }
            }
        }
        return sPubNubManager;
    }

    public void setPackageName(String packageName) {
        PubNubConstant.BROADCAST = packageName + ".pubnub";
    }

    /**
     * With builder.
     *
     * @param context the context
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Context context) {
        return new PubNubParam.Builder(context);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Activity context) {
        return new PubNubParam.Builder(context);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param event   the event
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Context context, @NonNull PubNubParam.Event event) {
        return new PubNubParam.Builder(context, event);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param event   the event
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Activity context, @NonNull PubNubParam.Event event) {
        return new PubNubParam.Builder(context, event);
    }

    /**
     * With builder.
     *
     * @param context       the context
     * @param publish_key   the publish key
     * @param subscribe_key the subscribe key
     * @param secret_key    the secret key
     * @param cipher_key    the cipher key
     * @param ssl_on        the ssl on
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Context context, String publish_key, String subscribe_key, String secret_key,
                               String cipher_key, boolean ssl_on) {
        return new PubNubParam.Builder(context, publish_key, subscribe_key, secret_key, cipher_key, ssl_on);
    }

    /**
     * With builder.
     *
     * @param context       the context
     * @param publish_key   the publish key
     * @param subscribe_key the subscribe key
     * @param secret_key    the secret key
     * @param ssl_on        the ssl on
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Activity context, String publish_key, String subscribe_key, String secret_key,
                               boolean ssl_on) {
        return new PubNubParam.Builder(context, publish_key, subscribe_key, secret_key, ssl_on);
    }
}
