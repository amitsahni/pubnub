package com.pubnubutil;

import java.io.Serializable;

/**
 * Created by clickapps on 10/8/17.
 */

public class PubnubConfiguration implements Serializable {

    private String publish_key,
            subscribe_key,
            senderId;
    private boolean ssl_on, enableGCM;

    public String getPublish_key() {
        return publish_key;
    }

    public String getSubscribe_key() {
        return subscribe_key;
    }

    public boolean isSsl_on() {
        return ssl_on;
    }

    public boolean isEnableGCM() {
        return enableGCM;
    }

    public static class Builder {
        private PubnubConfiguration pubnubConfiguration;

        public Builder() {
            pubnubConfiguration = new PubnubConfiguration();
        }

        public Builder keys(String publish_key, String subscribe_key) {
            pubnubConfiguration.publish_key = publish_key;
            pubnubConfiguration.subscribe_key = subscribe_key;
            return this;
        }

        public Builder isSsl(boolean ssl) {
            pubnubConfiguration.ssl_on = ssl;
            return this;
        }

        public Builder gcm(boolean gcm, String senderId) {
            pubnubConfiguration.enableGCM = gcm;
            pubnubConfiguration.senderId = senderId;
            return this;
        }

        public PubnubConfiguration build() {
            return pubnubConfiguration;
        }

    }
}
