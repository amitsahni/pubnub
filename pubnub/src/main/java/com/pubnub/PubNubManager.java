package com.pubnub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pubnub.api.PubNub;


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

    @Nullable
    public PubNub getPubNub() {
        return Pubnub.getPubnub();
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


}
