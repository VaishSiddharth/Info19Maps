package com.hackathon.offlinemaps.SmsUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.hackathon.offlinemaps.DirectionActivity;
import com.hackathon.offlinemaps.MainActivity;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsBody += smsMessage.getMessageBody();
                }
            }
    
            if (smsBody.startsWith(SmsHelper.SMS_CONDITION)) {
                Log.e(TAG, "Sms with condition detected");
                Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
                intent = new Intent(context, DirectionActivity.class);
                intent.putExtra("smsbody", smsBody);
                context.startActivity(intent);
            }
            Log.e(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
        }
    }
}