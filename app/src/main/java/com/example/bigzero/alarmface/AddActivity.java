package com.example.bigzero.alarmface;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bigzero.alarmface.db.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends ActionBarActivity {
    //khai báo các control dùng trong activity

    EditText edtGio;
    Button btnOK, btnCancel;
    Calendar cal;
    Date date;
    Spinner spnRingTone, spnRepeat;
    CheckBox cbVibration;
    ImageView imgChooseTime;
    RelativeLayout relativeLayout;

    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;

    private boolean isRunning = false;
    private MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Set Alarm");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControls();
        getDataToSpinner();
        changeColorActionBar();

        //lấy giờ hệ thống đưa vào textView
        cal = Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng giờ phút
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        //đưa lên giao diện
        edtGio.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        edtGio.setTag(dft.format(cal.getTime()));

    }

    //ánh xạ và gán sự kiện
    private void addControls() {
        edtGio = (EditText) findViewById(R.id.edtGio);

        imgChooseTime = (ImageView)findViewById(R.id.imgChooseTime);
        imgChooseTime.setOnClickListener(showTimePicker);

        btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop mediaplayer before set alarm
                ring.stop();

                addAlarm();
            }
        });
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop mediaplayer
                ring.stop();

                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        spnRepeat = (Spinner)findViewById(R.id.spnRepeat);
        spnRingTone = (Spinner)findViewById(R.id.spnRingTone);

        cbVibration = (CheckBox)findViewById(R.id.cbVibration);

        relativeLayout = (RelativeLayout)findViewById(R.id.rlAddActivity);
    }

    //hiển thì timePicker khi nhấn vào button
    View.OnClickListener showTimePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //Xử lý lưu giờ
                    //set giờ ra textView
                    edtGio.setText(formatTime(hour + ":" + minute));
                    //lưu viết lại giờ
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    date = cal.getTime();
                }
            };
            String s = edtGio.getTag() + "";
            String strArr[] = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            int phut = Integer.parseInt(strArr[1]);
            TimePickerDialog time = new TimePickerDialog(AddActivity.this,
                    callback, gio, phut, true);
            time.show();
        }
    };

    public void getDataToSpinner(){
        List<String> arr = new ArrayList<String>();
        arr.add("Choose ringtone");   //  Initial dummy entry
        arr.add("Ringtone 1");
        arr.add("Ringtone 2");
        arr.add("Ringtone 3");
        arr.add("Ringtone 4");

        String arr2[] = {
                "Everyday",
                "No Repeat"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v = null;

                // If this is the initial dummy entry, make it hidden
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    // Pass convertView as null to prevent reuse of special case views
                    v = super.getDropDownView(position, null, parent);
                }

                // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRingTone.setAdapter(adapter);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr2);
        adp.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRepeat.setAdapter(adp);

        ring = MediaPlayer.create(AddActivity.this, R.raw.causeiloveyou);
        spnRingTone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(AddActivity.this, R.raw.causeiloveyou);
                        isRunning = true;
                        break;
                    case 2:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(AddActivity.this, R.raw.face);
                        isRunning = true;
                        break;
                    case 3:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(AddActivity.this, R.raw.ringtone);
                        isRunning = true;
                        break;
                    case 4:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(AddActivity.this, R.raw.ringtone4);
                        isRunning = true;
                        break;
                }

                if (isRunning == true){
                    ring.start();
                    CountDownTimer cntr_aCounter = new CountDownTimer((1000*10), 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            //code fire after finish
                            ring.stop();
                        }
                    };
                    cntr_aCounter.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addAlarm(){
        int flag = 0, flag2 = 0;
        if(cbVibration.isChecked()){
            flag = 1;
        }

        if(spnRepeat.getSelectedItem().toString().equalsIgnoreCase("Everyday")){
            flag2 = 1;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put("Time", formatTime(edtGio.getText().toString()));
        contentValues.put("RingTone", spnRingTone.getSelectedItem().toString());
        contentValues.put("Vibration", flag);
        contentValues.put("Repeat", flag2);
        contentValues.put("Status", 1);

        if (validationTextViewGetTime()){
            Database = database.initDatabase(this, DATABASE_NAME);
            Database.insert("Alarm", null, contentValues);
            Toast.makeText(this, "Set alarm sucessfully!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(AddActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this, "Not the time format!", Toast.LENGTH_SHORT).show();
        }
    }

    //Check input time from keyboard
    public boolean validationTextViewGetTime(){
        String str = edtGio.getText().toString();
        String strArr[] = str.split(":");

        String h = strArr[0];
        String m = strArr[1];
        if(!isNumeric(h) || !isNumeric(m)){
            return false;
        }

        int hour = Integer.parseInt(strArr[0]);
        int minute = Integer.parseInt(strArr[1]);
        if (hour < 0 || hour > 24){
            return false;
        }
        if (minute <0 || minute > 60){
            return false;
        }
        return true;
    }

    //menthod used check isNumeric
    public static boolean isNumeric(String str){
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    //Handle input time from keyboard
    public String formatTime(String time){
        String strArr[] = time.split(":");
        int hour = Integer.parseInt(strArr[0]);
        int minute = Integer.parseInt(strArr[1]);
        String m, h;
        //xử lý phút
        if(minute == 0){
            m = "00";
        }else if(minute < 10 ){
            m = "0" + minute;
        }else{
            m = minute + "";
        }

        //xử lý giờ
        if(hour == 0){
            h = "00";
        }else if(hour < 10){
            h = "0" + hour;
        }else{
            h = hour + "";
        }

        return h + ":" + m;
    }

    public void changeColorActionBar(){
        Database = database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = Database.rawQuery("SELECT * FROM Color",null);
        cursor.moveToFirst();
        int color = cursor.getInt(1);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        switch (color){
            case 1:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));

                break;
            case 2:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                break;
            case 3:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                break;
            case 4:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                break;
            case 5:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                break;
            case 6:
                btnOK.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                btnCancel.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                break;
            default:
                break;
        }
    }
}
