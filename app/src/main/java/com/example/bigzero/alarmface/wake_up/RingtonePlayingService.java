package com.example.bigzero.alarmface.wake_up;

/**
 * Created by asus on 23/03/2017.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import com.example.bigzero.alarmface.R;


public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;
    Vibrator v;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get data from receiver
        String status = intent.getExtras().getString("status");
        int va = Integer.parseInt(intent.getExtras().getString("va"));
        String ring = intent.getExtras().getString("ringtone");
        //create notification
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), wake_up_activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT
                | PendingIntent.FLAG_ONE_SHOT);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Wake up! Wake up!!!")
                .setContentText("Turn off alarm!")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();



        assert status != null;
        switch (status) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        switch (ring){
            case "Ringtone 1":
                media_song = MediaPlayer.create(this, R.raw.causeiloveyou);
                break;
            case "Ringtone 2":
                media_song = MediaPlayer.create(this, R.raw.face);
                break;
            case "Ringtone 3":
                media_song = MediaPlayer.create(this, R.raw.ringtone);
                break;
            case "Ringtone 4":
                media_song = MediaPlayer.create(this, R.raw.ringtone4);
                break;
            default:
                media_song = MediaPlayer.create(this, R.raw.causeiloveyou);
                break;
        }
        long[] pattern = { 0, 200, 500 };
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(!this.isRunning && startId == 1) {
            //play media

            media_song.start();
            media_song.setLooping(true);

            //show notification
            mNM.notify(0, mNotify);

            if (va == 1){
                v.vibrate(pattern , 0);
            }


            this.isRunning = true;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
        }

        else if (this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 0;
        }
        else {
            //stop media
            media_song.stop();
            media_song.reset();

            //cancel vibration
            v.cancel();

            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }

}
