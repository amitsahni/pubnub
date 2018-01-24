package com.pubnub.callback;

import com.pubnub.api.models.consumer.PNStatus;

/**
 * Created by clickapps on 24/1/18.
 */

public interface OnResultListener<T> {
    void result(T result, PNStatus status);
}
