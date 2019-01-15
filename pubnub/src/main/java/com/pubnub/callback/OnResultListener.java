package com.pubnub.callback;

import android.support.annotation.Nullable;

import com.pubnub.api.models.consumer.PNStatus;

/**
 * Created by clickapps on 24/1/18.
 */

public interface OnResultListener<T> {
    void result(@Nullable T result, PNStatus status);
}
