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
                .build();
```       
### unSubScribeAll
```        
        PubNubManager.with(this)
                .unSubScribeAll()
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
#### Handle Message

Using inside Activity/Fragment
```aidl
LocalMessageLiveData messageLiveData = new LocalMessageLiveData(this);
        messageLiveData.observe(this, new Observer<PNMessageResult>() {
            @Override
            public void onChanged(@NonNull PNMessageResult pnMessageResult) {
                Log.i(getLocalClassName(), "Channel Observe = " + pnMessageResult.getChannel());
                Log.i(getLocalClassName(), "Message Observe = " + pnMessageResult.getMessage().getAsString());
            }
        });
```
Using inside Application Class
```aidl
GlobalMessageLiveData globalMessageLiveData = GlobalMessageLiveData.getInstance();
        messageLiveData.observeForever(new Observer<PNMessageResult>() {
            @Override
            public void onChanged(@NonNull PNMessageResult pnMessageResult) {
                Log.i(TAG, "Channel Observe = " + pnMessageResult.getChannel());
                Log.i(TAG, "Message Observe = " + pnMessageResult.getMessage().getAsString());
            }
        });
```

#### Handle Presence
```aidl
PresenceLiveData presenceLiveData = new PresenceLiveData(this);
        presenceLiveData.observe(this, new Observer<PNPresenceEventResult>() {
            @Override
            public void onChanged(@NonNull PNPresenceEventResult pnPresenceEventResult) {
                Log.i(getLocalClassName(), "Channel Observe = " + pnPresenceEventResult.getChannel());
            }
        });
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