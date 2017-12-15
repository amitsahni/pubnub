package com.pubnubutil;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by amit on 5/9/17.
 */

public class HistoryBuilder extends PubSubBuilder {

    private PubNubParam pubNubParam;

    public HistoryBuilder(PubNubParam pubNubParam) {
        super(pubNubParam);
        this.pubNubParam = pubNubParam;
    }

    @Override
    public HistoryBuilder channels(@NonNull String... channels) {
        return (HistoryBuilder) super.channels(channels);
    }

    @Override
    public HistoryBuilder channels(@NonNull String channels) {
        return (HistoryBuilder) super.channels(channels);
    }

    @Override
    public HistoryBuilder channels(@NonNull List<String> channels) {
        return (HistoryBuilder) super.channels(channels);
    }

    @Override
    public HistoryBuilder callback(@Nullable PubNubParam.OnPushMessageListener l) {
        return (HistoryBuilder) super.callback(l);
    }

    @Override
    public HistoryBuilder taskId(int taskId) {
        return (HistoryBuilder) super.taskId(taskId);
    }

    public HistoryBuilder progressDialog(@NonNull Dialog progressDialog) {
        pubNubParam.dialog = progressDialog;
        return this;
    }

    public HistoryBuilder historyCount(int count) {
        pubNubParam.count = count;
        return this;
    }

    public HistoryBuilder includeTimeToken(boolean includeTimeToken) {
        pubNubParam.includeTimeToken = includeTimeToken;
        return this;
    }

    public HistoryBuilder isHistoryReverse(boolean reverse) {
        pubNubParam.reverse = reverse;
        return this;
    }

    public HistoryBuilder startDate(Long start) {
        pubNubParam.start = start;
        return this;
    }

    public HistoryBuilder endDate(Long end) {
        pubNubParam.end = end;
        return this;
    }


    @Override
    public void build() {
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }
}
