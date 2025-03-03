package com.hackathon.offlinemaps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.hackathon.offlinemaps.SmsUtils.SmsHelper;

/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
public class MySMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
    
            if (status != null) {
                switch(status.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get SMS message contents
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        Log.e("MySMSBroadcastReceiver","Success in mysms "+message);
        
                        if (message != null && message.startsWith(SmsHelper.SMS_CONDITION)) {
                           // Log.e("MySMSBroadcastReceiver", "Sms with condition detected");
                            intent = new Intent(context, DirectionActivity.class);
                            intent.putExtra("smsbody", message);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        //extras.putString("message",message);
                        // Extract one-time code from the message and complete verification
                        // by sending the code back to your server.
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Waiting for SMS timed out (5 minutes)
                        // Handle the error ...
                        Log.e("MySMSBroadcastReceiver","Failed in mysms");
                        break;
                }
            }
        }
    }
}