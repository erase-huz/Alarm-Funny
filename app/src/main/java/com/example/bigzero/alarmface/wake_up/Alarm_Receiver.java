package com.example.bigzero.alarmface.wake_up;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by asus on 23/03/2017.
 */

public class Alarm_Receiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getExtras().getString("extra");
        String va = intent.getExtras().getString("vabrition");
        String ringtone = intent.getExtras().getString("ringtone");
        Intent intent_service = new Intent(context, RingtonePlayingService.class);
        intent_service.putExtra("status", status);
        intent_service.putExtra("va", va);
        intent_service.putExtra("ringtone", ringtone);
        context.startService(intent_service);
    }

}