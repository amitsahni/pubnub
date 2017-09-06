package com.pubnubutil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 5/9/17.
 */

public class PublishBuilder extends PubSubBuilder {
    private PubNubParam pubNubParam;

    public PublishBuilder(PubNubParam pubNubParam) {
        super(pubNubParam);
        this.pubNubParam = pubNubParam;
    }

    @Override
    public PublishBuilder channels(@NonNull String... channels) {
        return (PublishBuilder) super.channels(channels);
    }

    @Override
    public PublishBuilder channels(@NonNull String channels) {
        return (PublishBuilder) super.channels(channels);
    }

    @Override
    public PublishBuilder channels(@NonNull List<String> channels) {
        return (PublishBuilder) super.channels(channels);
    }

    public <T> PublishBuilder message(@NonNull T message) {
        pubNubParam.message = message;
        return this;
    }

    @Override
    public PublishBuilder callback(@Nullable PubNubParam.OnPushMessageListener l) {
        return (PublishBuilder) super.callback(l);
    }

    public void build() {
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }
}
