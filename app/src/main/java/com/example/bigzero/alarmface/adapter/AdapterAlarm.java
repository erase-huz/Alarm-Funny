package com.example.bigzero.alarmface.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bigzero.alarmface.R;
import com.example.bigzero.alarmface.db.database;
import com.example.bigzero.alarmface.wake_up.Alarm_Receiver;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by asus on 28/03/2017.
 */

public class AdapterAlarm extends BaseAdapter{
    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;

    private ArrayList<alarm> list;
    private Context context;
    private alarm al;

    private Intent intent;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int hour = 0, minute = 0;
    private int Id;

    public AdapterAlarm(ArrayList<alarm> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.hone_one_row,null);

        TextView tvTime = (TextView)row.findViewById(R.id.tvTimeOneRow);
        final Switch sOn_Off = (Switch)row.findViewById(R.id.sOn_Off);
        //lay vi tri cua doi tuong trong list
        al = list.get(position);
        Id = al.getId();
        //lay thoi gian
        String strArr[] = al.getTime().split(":");
        hour = Integer.parseInt(strArr[0]);
        minute = Integer.parseInt(strArr[1]);

        tvTime.setText(al.getTime().toString());
        if(al.getStatus() == 1){
            sOn_Off.setText("ON");
            sOn_Off.setChecked(true);
            setAlarm(hour, minute, context);
        }else{
            sOn_Off.setText("OFF");
        }

        //bat su kien khi nguoi dung chon on/off
        sOn_Off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sOn_Off.setText("ON");
                    setAlarm(hour, minute, context);
                    updateDataToDatabase(1);
                }else {
                    sOn_Off.setText("OFF");
                    cancelAlarm();
                    updateDataToDatabase(0);
                }
            }
        });

        return row;
    }

    public void setAlarm(int hour, int minute, Context context){
        //khởi tạo đối tượng calender
        Calendar calendar = Calendar.getInstance();
        //set time
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 00);
        long startTime = calendar.getTimeInMillis();
        //khởi tạo intent
        intent = new Intent(context, Alarm_Receiver.class);
        //send data to receiver
        intent.putExtra("extra", "yes");
        intent.putExtra("vabrition", String.valueOf(al.getVibration()));
        intent.putExtra("ringtone", String.valueOf(al.getRingtone()));
        //khởi tạo đối tượng pending intent
        pendingIntent = PendingIntent.getBroadcast(context, 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //khởi tạo đối tường alarmmanager
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //repeat alarm everyday
        if (al.getRepeat() == 1){
            //bao thuc lap lai hang ngay
            if (System.currentTimeMillis() > startTime){
                startTime = startTime + (24*60*60*1000);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        }else{
            //no repeat alarm
            if (System.currentTimeMillis() > startTime){
                cancelAlarm();
            }else{
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
                //update status alarm
                updateDataToDatabase(0);
            }
        }

    }

    //tat bao thuc
    public void cancelAlarm(){
        intent.putExtra("extra", "no");
        intent.putExtra("vabrition", "0");
        intent.putExtra("ringtone", " ");
        alarmManager.cancel(pendingIntent);
        context.sendBroadcast(intent);
    }

    //update lai trang thai bao thuc
    public void updateDataToDatabase(int sta){
        ContentValues values = new ContentValues();
        values.put("Status", sta);
        Database = database.initDatabase((Activity) context, DATABASE_NAME);
        Database.update("Alarm", values, "ID = ?",new String[]{Id+""});
    }
}
