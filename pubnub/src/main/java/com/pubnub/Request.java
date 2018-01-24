package com.pubnub;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.presence.PNGetStateResult;
import com.pubnub.api.models.consumer.presence.PNHereNowResult;
import com.pubnub.api.models.consumer.presence.PNWhereNowResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.callback.OnResultListener;
import com.pubnub.callback.OnSubscribeListener;
import com.pubnub.di.IPubnubProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by clickapps on 24/1/18.
 */
@SuppressWarnings("unchecked")
public class Request {
    /********************************************************************/
    public static class HistoryBuilder<T extends HistoryBuilder> implements IPubnubProperty<T> {
        private PubNubParam param;

        public HistoryBuilder(PubNubParam param) {
            this.param = param;
        }


        @Override
        public T channels(@NonNull String... channels) {
            param.channels = channels;
            return (T) this;
        }

        @Override
        public T channels(@NonNull List<String> channels) {
            List<String> list = new ArrayList<>(channels);
            if (!list.isEmpty()) {
                String[] temp = new String[list.size()];
                temp = list.toArray(temp);
                param.channels = temp;
            }
            return (T) this;
        }

        /**
         * <PNHistoryResult>
         */
        public T callback(@NonNull OnResultListener<PNHistoryResult> l) {
            param.resultListener = l;
            return (T) this;
        }

        public T progressDialog(@NonNull Dialog progressDialog) {
            param.dialog = progressDialog;
            return (T) this;
        }

        public T historyCount(int count) {
            param.count = count;
            return (T) this;
        }

        public T includeTimeToken(boolean includeTimeToken) {
            param.includeTimeToken = includeTimeToken;
            return (T) this;
        }

        public T isHistoryReverse(boolean reverse) {
            param.reverse = reverse;
            return (T) this;
        }

        public T startDate(Long start) {
            param.start = start;
            return (T) this;
        }

        public T endDate(Long end) {
            param.end = end;
            return (T) this;
        }

        @Override
        public void build() {
            if (param.dialog != null &&
                    !param.dialog.isShowing()) {
                param.dialog.show();
            }
            final int size = param.channels == null ? 0 : param.channels.length;
            Pubnub pubNub = new Pubnub(param);
            for (int i = 0; i < size; i++) {
                pubNub.getPubNub().history()
                        .channel(param.channels[i])
                        .count(param.count > 100 ? 100 : param.count)
                        .includeTimetoken(param.includeTimeToken)
                        .reverse(param.reverse)
                        .start(param.start > 0L ? param.start : null)
                        .end(param.end > 0L ? param.end : null)
                        .async(new PNCallback<PNHistoryResult>() {
                            @Override
                            public void onResponse(PNHistoryResult result, PNStatus status) {
                                if (param.dialog != null
                                        && param.dialog.isShowing()) {
                                    param.dialog.dismiss();
                                }
                                if (status.isError()) {
                                    // handle error
                                    if (PubnubConfiguration.isDebuggable()) {
                                        Log.e(getClass().getSimpleName(), "PNStatus:" + status.toString());
                                    }
                                    return;
                                }
                                String channel = TextUtils.join(",", status.getAffectedChannels());
                                if (PubnubConfiguration.isDebuggable()) {
                                    Log.i(getClass().getSimpleName(), "Channel : " + channel);
                                }
                                if (param.resultListener != null) {
                                    param.resultListener.result(result, status);
                                }
                            }
                        });
            }
        }
    }

    /********************************************************************/
    public static class PublishBuilder<T extends PublishBuilder> implements IPubnubProperty<T> {
        private PubNubParam param;

        public PublishBuilder(PubNubParam param) {
            this.param = param;
        }

        @Override
        public T channels(@NonNull String... channels) {
            param.channels = channels;
            return (T) this;
        }

        @Override
        public T channels(@NonNull List<String> channels) {
            List<String> list = new ArrayList<>(channels);
            if (!list.isEmpty()) {
                String[] temp = new String[list.size()];
                temp = list.toArray(temp);
                param.channels = temp;
            }
            return (T) this;
        }

        /*
         *  <PNPublishResult>
         */
        public T callback(@NonNull OnResultListener<PNPublishResult> l) {
            param.resultListener = l;
            return (T) this;
        }

        @Override
        public void build() {
            Pubnub pubNub = new Pubnub(param);
            for (String channel : param.channels) {
                pubNub.getPubNub().publish().message(param.message)
                        .shouldStore(true)
                        .usePOST(true)
                        .channel(channel)
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                if (status.isError()) {
                                    // handle error
                                    if (PubnubConfiguration.isDebuggable()) {
                                        Log.e(getClass().getSimpleName(), "PNStatus:" + status.toString());
                                    }
                                    return;
                                }
                                String channel = TextUtils.join(",", status.getAffectedChannels());
                                if (PubnubConfiguration.isDebuggable()) {
                                    Log.i(getClass().getSimpleName(), "Channel : " + channel);
                                }
                                if (param.resultListener != null) {
                                    param.resultListener.result(result, status);
                                }
                            }
                        });
            }
        }
    }

    /********************************************************************/
    public static class SubscribeBuilder<T extends SubscribeBuilder> implements IPubnubProperty<T> {
        private PubNubParam param;

        public SubscribeBuilder(PubNubParam param) {
            this.param = param;
        }

        @Override
        public T channels(@NonNull String... channels) {
            param.channels = channels;
            return (T) this;
        }

        @Override
        public T channels(@NonNull List<String> channels) {
            List<String> list = new ArrayList<>(channels);
            if (!list.isEmpty()) {
                String[] temp = new String[list.size()];
                temp = list.toArray(temp);
                param.channels = temp;
            }
            return (T) this;
        }

        /*
         *  <PNStatus>
         */
        public T statusCallback(@NonNull OnSubscribeListener<PNStatus> l) {
            param.statusListener = l;
            return (T) this;
        }

        /*
         *  <PNMessageResult>
         */
        public T messageCallback(@NonNull OnSubscribeListener<PNMessageResult> l) {
            param.messageListener = l;
            return (T) this;
        }

        /*
         *  <PNPresenceEventResult>
         */
        public T presenceCallback(@NonNull OnSubscribeListener<PNPresenceEventResult> l) {
            param.presenceListener = l;
            return (T) this;
        }

        @Override
        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub().subscribe()
                    .channels(param.channels == null ? new ArrayList<String>() : Arrays.asList(param.channels))
                    .withPresence()
                    .execute();
        }
    }

    /********************************************************************/
    public static class UnSubscribeBuilder<T extends UnSubscribeBuilder> implements IPubnubProperty<T> {
        private PubNubParam param;

        public UnSubscribeBuilder(PubNubParam param) {
            this.param = param;
        }

        @Override
        public T channels(@NonNull String... channels) {
            param.channels = channels;
            return (T) this;
        }

        @Override
        public T channels(@NonNull List<String> channels) {
            List<String> list = new ArrayList<>(channels);
            if (!list.isEmpty()) {
                String[] temp = new String[list.size()];
                temp = list.toArray(temp);
                param.channels = temp;
            }
            return (T) this;
        }

        public T statusCallback(@NonNull OnSubscribeListener<PNStatus> l) {
            param.statusListener = l;
            return (T) this;
        }

        @Override
        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub().unsubscribe()
                    .channels(param.channels == null ? new ArrayList<String>() : Arrays.asList(param.channels))
                    .execute();
            if (PubnubConfiguration.isDebuggable()) {
                Log.d(getClass().getSimpleName(), "All SubScribed:" + pubNub.getPubNub().getSubscribedChannels().toString());
            }
        }
    }

    /********************************************************************/
    public static class UnSubscribeAllBuilder<T extends UnSubscribeAllBuilder> {
        private PubNubParam param;

        public UnSubscribeAllBuilder(PubNubParam param) {
            this.param = param;
        }

        public T statusCallback(@NonNull OnSubscribeListener<PNStatus> l) {
            param.statusListener = l;
            return (T) this;
        }

        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub().unsubscribeAll();
            if (PubnubConfiguration.isDebuggable()) {
                Log.d(getClass().getSimpleName(), "All SubScribed:" + pubNub.getPubNub().getSubscribedChannels().toString());
            }
        }
    }

    /********************************************************************/
    public static class SubscribedBuilder<T extends SubscribedBuilder> {
        private PubNubParam param;

        public SubscribedBuilder(PubNubParam param) {
            this.param = param;
        }


        public T statusCallback(@NonNull OnSubscribeListener<PNStatus> l) {
            param.statusListener = l;
            return (T) this;
        }

        public List<String> build() {
            Pubnub pubNub = new Pubnub(param);
            List<String> list = pubNub.getPubNub().getSubscribedChannels();
            if (PubnubConfiguration.isDebuggable()) {
                Log.d(getClass().getSimpleName(), "All SubScribed:" + list.toString());
            }
            return list;
        }
    }

    /********************************************************************/
    public static class HereNowBuilder<T extends HereNowBuilder> implements IPubnubProperty<T> {
        private PubNubParam param;

        public HereNowBuilder(PubNubParam param) {
            this.param = param;
        }

        @Override
        public T channels(@NonNull String... channels) {
            param.channels = channels;
            return (T) this;
        }

        @Override
        public T channels(@NonNull List<String> channels) {
            List<String> list = new ArrayList<>(channels);
            if (!list.isEmpty()) {
                String[] temp = new String[list.size()];
                temp = list.toArray(temp);
                param.channels = temp;
            }
            return (T) this;
        }

        /*
        *  <PNHereNowResult>
        */
        public T callback(@NonNull OnResultListener<PNHereNowResult> l) {
            param.resultListener = l;
            return (T) this;
        }

        @Override
        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub().hereNow()
                    .channels(param.channels == null ? new ArrayList<String>() : Arrays.asList(param.channels))
                    .includeUUIDs(true)
                    .includeState(true)
                    .async(new PNCallback<PNHereNowResult>() {
                        @Override
                        public void onResponse(PNHereNowResult result, PNStatus status) {
                            if (status.isError()) {
                                // handle error
                                if (PubnubConfiguration.isDebuggable()) {
                                    Log.d(getClass().getSimpleName(), "PNStatus:" + status.toString());
                                }
                                return;
                            }
                            String channel = TextUtils.join(",", status.getAffectedChannels());
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.i(getClass().getSimpleName(), "Channel : " + channel);
                            }
                            if (param.resultListener != null) {
                                param.resultListener.result(result, status);
                            }
                        }
                    });
        }
    }

    /********************************************************************/
    public static class WhereNowBuilder<T extends WhereNowBuilder> {
        private PubNubParam param;

        public WhereNowBuilder(PubNubParam param) {
            this.param = param;
        }

        /*
        *  <PNWhereNowResult>
        */
        public T callback(@NonNull OnResultListener<PNWhereNowResult> l) {
            param.resultListener = l;
            return (T) this;
        }

        public T uuid(@NonNull String uuid) {
            param.uuid = uuid;
            return (T) this;
        }

        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub().whereNow()
                    .uuid(param.uuid)
                    .async(new PNCallback<PNWhereNowResult>() {
                        @Override
                        public void onResponse(PNWhereNowResult result, PNStatus status) {
                            if (status.isError()) {
                                // handle error
                                if (PubnubConfiguration.isDebuggable()) {
                                    Log.d(getClass().getSimpleName(), "PNStatus:" + status.toString());
                                }
                                return;
                            }
                            String channel = TextUtils.join(",", status.getAffectedChannels());
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.i(getClass().getSimpleName(), "Channel : " + channel);
                            }
                            if (param.resultListener != null) {
                                param.resultListener.result(result, status);
                            }
                        }
                    });
        }
    }

    /********************************************************************/
    public static class GetPresenceBuilder<T extends GetPresenceBuilder> {
        private PubNubParam param;

        public GetPresenceBuilder(PubNubParam param) {
            this.param = param;
        }

        /*
         *  <PNGetStateResult>
         */
        public T callback(@NonNull OnResultListener<PNGetStateResult> l) {
            param.resultListener = l;
            return (T) this;
        }

        public T uuid(@NonNull String uuid) {
            param.uuid = uuid;
            return (T) this;
        }

        public void build() {
            Pubnub pubNub = new Pubnub(param);
            pubNub.getPubNub()
                    .getPresenceState()
                    .channels(param.channels == null ? new ArrayList<String>() : Arrays.asList(param.channels)) // channels to fetch state for
                    .uuid(param.uuid) // uuid of user to fetch, or omit own uuid
                    .async(new PNCallback<PNGetStateResult>() {
                        @Override
                        public void onResponse(PNGetStateResult result, PNStatus status) {
                            if (status.isError()) {
                                // handle error
                                if (PubnubConfiguration.isDebuggable()) {
                                    Log.d(getClass().getSimpleName(), "PNStatus:" + status.toString());
                                }
                                return;
                            }
                            String channel = TextUtils.join(",", status.getAffectedChannels());
                            if (PubnubConfiguration.isDebuggable()) {
                                Log.i(getClass().getSimpleName(), "Channel : " + channel);
                            }
                            if (param.resultListener != null) {
                                param.resultListener.result(result, status);
                            }
                        }
                    });
        }
    }
}
