PubnubUtil ![](https://jitpack.io/v/amitclickapps/pubnub-util.svg?style=flat-square)
------
### PubnubConfiguration
#### If gcm enable then add gcm key in pubnub console
```
        new PubnubConfiguration.Builder()
                .keys("publish_key", "subscribe_key")
                .gcm(true, "senderId")
                .isDebug(BuildConfig.DEBUG)
                .build();
```
### PubnubManager Subscribe
```
        String[] channel = new String[]{"channel1", "channel2"};
        PubNubManager.with(this)
                        .subScribe()
                        .channels(channels)
                        .callback(new PubNubParam.OnPushMessageListener() {
                                            @Override
                                            public void result(String channel, Object result, PNStatus status, int taskId) {
                                                
                                            }
                        
                                            @Override
                                            public void status(String channel, PNStatus status) {
                        
                                            }
                        
                                            @Override
                                            public void message(String channel, Object message) {
                        
                                            }
                        
                                            @Override
                                            public void presence(String channel, PNPresenceEventResult presence) {
                        
                                            }
                        }).build();
```
### PubnubManager publish
```
        String[] channel = new String[]{"channel1", "channel2"};
        PubNubManager.with(this)
                        .publish()
                        .channels(channels)
                        .message(object)
                        .callback(new PubNubParam.OnPushMessageListener() {
                                            @Override
                                            public void result(String channel, Object result, PNStatus status, int taskId) {
                                                
                                            }
                        
                                            @Override
                                            public void status(String channel, PNStatus status) {
                        
                                            }
                        
                                            @Override
                                            public void message(String channel, Object message) {
                        
                                            }
                        
                                            @Override
                                            public void presence(String channel, PNPresenceEventResult presence) {
                        
                                            }
                        }).build();
```
### PubnubManager unSubscribe Channel
```
        String[] channel = new String[]{"channel1", "channel2"};
        PubNubManager.with(this)
                        .unSubScribe()
                        .channels(channels)
                        .callback(new PubNubParam.OnPushMessageListener() {
                                            @Override
                                            public void result(String channel, Object result, PNStatus status, int taskId) {
                                                
                                            }
                        
                                            @Override
                                            public void status(String channel, PNStatus status) {
                        
                                            }
                        
                                            @Override
                                            public void message(String channel, Object message) {
                        
                                            }
                        
                                            @Override
                                            public void presence(String channel, PNPresenceEventResult presence) {
                        
                                            }
                        }).build();
```
### PubnubManager unSubscribeAllChannel
```
        PubNubManager.with(this)
                        .unSubScribeAll()
                        .callback(new PubNubParam.OnPushMessageListener() {
                                            @Override
                                            public void result(String channel, Object result, PNStatus status, int taskId) {
                                                
                                            }
                        
                                            @Override
                                            public void status(String channel, PNStatus status) {
                        
                                            }
                        
                                            @Override
                                            public void message(String channel, Object message) {
                        
                                            }
                        
                                            @Override
                                            public void presence(String channel, PNPresenceEventResult presence) {
                        
                                            }
                        }).build();
```
### PubnubManager SubscribedList
```
       List<String> subScribedList = PubNubManager.with(this)
                       .getScribeList();
```
### PubnubManager history
#### To enable history Storage & Playback in Pubnub
```
        String[] channel = new String[]{"channel1"};
        PubNubManager.with(this)
                        .history()
                        .channels(channels)
                        .historyCount(100)
                        .progressDialog(dialog)
                        .callback(new PubNubParam.OnPushMessageListener() {
                                            @Override
                                            public void result(String channel, Object result, PNStatus status, int taskId) {
                                                
                                            }
                        
                                            @Override
                                            public void status(String channel, PNStatus status) {
                        
                                            }
                        
                                            @Override
                                            public void message(String channel, Object message) {
                        
                                            }
                        
                                            @Override
                                            public void presence(String channel, PNPresenceEventResult presence) {
                        
                                            }
                        }).build();
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
Add the JitPack repository to your root build.gradle: ![](https://jitpack.io/v/amitclickapps/pubnub-util.svg?style=flat-square)

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
		compile 'com.github.amitclickapps:pubnub-util:latest'
	}
```