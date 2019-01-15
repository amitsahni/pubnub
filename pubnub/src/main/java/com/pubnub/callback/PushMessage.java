package com.pubnub.callback;

public class PushMessage {
    protected String channel = "";
    protected String message = "";

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }
}