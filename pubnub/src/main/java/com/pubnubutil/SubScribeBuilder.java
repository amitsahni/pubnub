package com.pubnubutil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by amit on 5/9/17.
 */

public class SubScribeBuilder extends PubSubBuilder {

    private PubNubParam pubNubParam;

    public SubScribeBuilder(PubNubParam pubNubParam) {
        super(pubNubParam);
        this.pubNubParam = pubNubParam;
    }

    @Override
    public SubScribeBuilder channels(@NonNull String... channels) {
        return (SubScribeBuilder) super.channels(channels);
    }

    @Override
    public SubScribeBuilder channels(@NonNull String channels) {
        return (SubScribeBuilder) super.channels(channels);
    }

    @Override
    public SubScribeBuilder channels(@NonNull List<String> channels) {
        return (SubScribeBuilder) super.channels(channels);
    }

    @Override
    public SubScribeBuilder callback(@Nullable PubNubParam.OnPushMessageListener l) {
        return (SubScribeBuilder) super.callback(l);
    }

    @Override
    public void build() {
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }


}
