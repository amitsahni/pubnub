package com.pubnubutil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 5/9/17.
 */

public abstract class PubSubBuilder {

    private PubNubParam pubNubParam;

    private PubSubBuilder() {

    }

    public PubSubBuilder(PubNubParam pubNubParam) {
        this.pubNubParam = pubNubParam;
    }

    public PubSubBuilder channels(@NonNull String... channels) {
        this.pubNubParam.channels = channels;
        return this;
    }

    public PubSubBuilder channels(@NonNull String channels) {
        pubNubParam.channels = TextUtils.split(channels, ",");
        return this;
    }

    public PubSubBuilder channels(@NonNull List<String> channels) {
        List<String> list = new ArrayList<>(channels);
        if (!list.isEmpty()) {
            String[] temp = new String[list.size()];
            temp = list.toArray(temp);
            pubNubParam.channels = temp;
        }
        return this;
    }

    public PubSubBuilder callback(@Nullable PubNubParam.OnPushMessageListener l) {
        pubNubParam.listener = l;
        return this;
    }

    public abstract void build();
}
