package test.pubnub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Receiver.class.getSimpleName(), "Receiver = " + intent.toString());
        String message = intent.getStringExtra("message");
        Log.i(getClass().getSimpleName(), "Message = " + message);
        Toast.makeText(context, intent.toString(), Toast.LENGTH_SHORT).show();
    }
}
