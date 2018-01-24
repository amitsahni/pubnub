package com.pubnub.callback;

/**
 * Created by clickapps on 24/1/18.
 */
public interface OnSubscribeListener<T> {
    void result(String channel, T result);
}
