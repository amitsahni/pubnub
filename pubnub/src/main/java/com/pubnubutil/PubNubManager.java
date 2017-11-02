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
    @Deprecated
    public static Builder with(@NonNull Context context,
                                           @NonNull PubNubParam.Event event) {
        return new Builder(context);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param event   the event
     * @return the builder
     */
    @Deprecated
    public static Builder with(@NonNull Activity context,
                                           @NonNull PubNubParam.Event event) {
        return new Builder(context);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @return the builder
     */
    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @return the builder
     */
    public static Builder with(@NonNull Activity context) {
        return new Builder(context);
    }

}
