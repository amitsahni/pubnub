package com.pubnubutil;

import java.io.Serializable;

/**
 * Created by clickapps on 10/8/17.
 */

public class PubnubConfiguration implements Serializable {

    private static String publish_key,
            subscribe_key,
            senderId;
    private static boolean ssl_on, enableGCM;

    public static String getPublish_key() {
        return publish_key;
    }

    public static String getSubscribe_key() {
        return subscribe_key;
    }

    public static boolean isSsl_on() {
        return ssl_on;
    }

    public static boolean isEnableGCM() {
        return enableGCM;
    }

    public static class Builder {
        private String publish_key,
                subscribe_key,
                senderId;
        private boolean ssl_on, enableGCM;

        public Builder() {
        }

        public Builder keys(String publish_key, String subscribe_key) {
            this.publish_key = publish_key;
            this.subscribe_key = subscribe_key;
            return this;
        }

        public Builder isSsl(boolean ssl) {
            this.ssl_on = ssl;
            return this;
        }

        public Builder gcm(boolean gcm, String senderId) {
            this.enableGCM = gcm;
            this.senderId = senderId;
            return this;
        }

        public void build() {
            PubnubConfiguration.publish_key = publish_key;
            PubnubConfiguration.subscribe_key = subscribe_key;
            PubnubConfiguration.ssl_on = ssl_on;
            PubnubConfiguration.enableGCM = enableGCM;
            PubnubConfiguration.senderId = senderId;

        }

    }
}
