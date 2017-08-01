package com.pubnubutil;


/**
 * Created by clickapps on 26/7/17.
 */

public class History {
    PubNubParam pubNubParam;

    public History(PubNubParam param) {
        pubNubParam = param;
    }

    public History historyCount(int count) {
        pubNubParam.count = count;
        return this;
    }

    public History includeTimeToken(boolean includeTimeToken) {
        pubNubParam.includeTimeToken = includeTimeToken;
        return this;
    }

    public History isHistoryReverse(boolean reverse) {
        pubNubParam.reverse = reverse;
        return this;
    }

    public History startDate(Long start) {
        pubNubParam.start = start;
        return this;
    }

    public History endDate(Long end) {
        pubNubParam.end = end;
        return this;
    }

    /**
     * Build.
     */
    public void build() {
        Pubnub pubNub = new Pubnub(pubNubParam);
        pubNub.handleEvent(pubNubParam);
    }
}
