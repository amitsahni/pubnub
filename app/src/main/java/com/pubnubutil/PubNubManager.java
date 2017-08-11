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
     * @param event   the event
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Context context,
                                           @NonNull PubNubParam.Event event, @NonNull PubnubConfiguration pubnubConfiguration) {
        return new PubNubParam.Builder(context, event, pubnubConfiguration);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param event   the event
     * @return the builder
     */
    public static PubNubParam.Builder with(@NonNull Activity context,
                                           @NonNull PubNubParam.Event event, @NonNull PubnubConfiguration pubnubConfiguration) {
        return new PubNubParam.Builder(context, event, pubnubConfiguration);
    }

}
