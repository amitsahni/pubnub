------
### PubnubConfiguration
#### If gcm enable then add gcm key in pubnub console
```
        new PubnubConfiguration.Builder("publish_key", "subscribe_key")
                .gcm(true, "senderId")
                .isDebug(BuildConfig.DEBUG)
                .build();
```
### SubScribe
```
        String[] channels = new String[]{"channel1", "channel2"};
        PubNubManager.with(this)
                .subScribe(channels)
                .messageCallback(new OnSubscribeListener<PNMessageResult>() {
                    @Override
                    public void result(String channel, PNMessageResult result) {

                    }
                })
                .presenceCallback(new OnSubscribeListener<PNPresenceEventResult>() {
                    @Override
                    public void result(String channel, PNPresenceEventResult result) {

                    }
                })
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();
```
### History
```
        PubNubManager.with(this)
                .history()
                .historyCount(10)
                .callback(new OnResultListener<PNHistoryResult>() {
                    @Override
                    public void result(PNHistoryResult result, PNStatus status) {

                    }
                })
                .build();
```
### Publish
```
        PubNubManager.with(this)
                .publish("Message", "channel")
                .callback(new OnResultListener<PNPublishResult>() {
                    @Override
                    public void result(PNPublishResult result, PNStatus status) {

                    }
                })
                .build();
```
### UnSubScribe        
```
        PubNubManager.with(this)
                .unSubScribe("channel")
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();
```       
### unSubScribeAll
```        
        PubNubManager.with(this)
                .unSubScribeAll()
                .statusCallback(new OnSubscribeListener<PNStatus>() {
                    @Override
                    public void result(String channel, PNStatus result) {

                    }
                })
                .build();
```

### Following Points have to take care before using this
--------
#### Whenever PushNotification received this broadcast notified
```
<!--  Broadcast receiver for PubNub Message -->
        <receiver android:name="NameOfBroadcast">
            <intent-filter>
                <action android:name="yourpackageName.pubnub" />
            </intent-filter>
        </receiver>
```
#### When Internet on/off this broadcast notified here have to subscribe all channel again. Because when internet off all channels are auto unSubscribed. For Subscribe again Either use Intent Service from BroadCastReceiver
```
<receiver android:name=".InternetBraodcast">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
        </receiver>
```
#### If need Local Broadcast on specific screen use below code
```
private BroadcastReceiver localBroadCast= new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {

    }
};
```
#### Register
```
IntentFilter intentFilter = new IntentFilter(PubNubConstant.LOCAL_BROADCAST);
LocalBroadcastManager.getInstance(this).registerReceiver(localBroadCast, intentFilter);
```


#### UnRegister
```
LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadCast);
```
Download
--------
Add the JitPack repository to your root build.gradle

```groovy
	allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```
Add the Gradle dependency:
```groovy
	dependencies {
		compile 'com.github.amitsahni:pubnub:1.0.0-aplha'
	}
```