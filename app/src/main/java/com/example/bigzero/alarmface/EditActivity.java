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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bigzero.alarmface.adapter.alarm;
import com.example.bigzero.alarmface.db.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditActivity extends ActionBarActivity {
    //khai báo các control dùng trong activity

    private EditText edtGio_edit;
    private ImageView imgChooseTime_edit;
    private Button btnOK, btnCancel;
    private Calendar cal;
    private Date date;
    private Spinner spnRingTone_edt, spnRepeat_edt;
    private CheckBox cbVibration_edt, cbStatus;

    private ArrayList<alarm> list;

    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;

    private boolean isRunning = false;
    private MediaPlayer ring;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Edit Alarm");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //anh xa cac control va gan su kien
        addControls();

        //lay gio he thong
        cal = Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng giờ phút
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        //đưa lên giao diện
        edtGio_edit.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        edtGio_edit.setTag(dft.format(cal.getTime()));

        //dua du lieu vao spinner
        getDataToSpinner();
        //lay du lieu tu db dua vao widget tren activity
        getDataToActivity();
        changeColorActionBar();
    }

    //ánh xạ và gán sự kiện
    public void addControls() {

        edtGio_edit = (EditText) findViewById(R.id.edtGio_edit);

        imgChooseTime_edit = (ImageView)findViewById(R.id.imgChooseTime_edit);
        imgChooseTime_edit.setOnClickListener(showTimePickerEdit);

        btnOK = (Button)findViewById(R.id.btnOK_edt);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ring.stop();
//                ring2.stop();
//                ring3.stop();
//                ring4.stop();

                updateDataToDatabase();
            }
        });
        btnCancel = (Button)findViewById(R.id.btnCancel_edt);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ring.stop();
//                ring2.stop();
//                ring3.stop();
//                ring4.stop();

                Intent i = new Intent(EditActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        spnRepeat_edt = (Spinner)findViewById(R.id.spnRepeat_edt);
        spnRingTone_edt = (Spinner)findViewById(R.id.spnRingTone_edt);

        cbVibration_edt = (CheckBox)findViewById(R.id.cbVibration_edt);
        cbStatus = (CheckBox)findViewById(R.id.cbStatus);
    }

    //hiển thì timePicker khi nhấn vào button
    View.OnClickListener showTimePickerEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //Xử lý lưu giờ
                    edtGio_edit.setText(formatTime(hour + ":" + minute));
                    //lưu vết lại giờ
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    date = cal.getTime();
                }
            };
            String s = edtGio_edit.getTag() + "";
            String strArr[] = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            int phut = Integer.parseInt(strArr[1]);
            TimePickerDialog time = new TimePickerDialog(EditActivity.this,
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
        spnRingTone_edt.setAdapter(adapter);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr2);
        adp.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRepeat_edt.setAdapter(adp);

        ring = MediaPlayer.create(EditActivity.this, R.raw.causeiloveyou);
        spnRingTone_edt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(EditActivity.this, R.raw.causeiloveyou);
                        isRunning = true;
                        break;
                    case 2:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(EditActivity.this, R.raw.face);
                        isRunning = true;
                        break;
                    case 3:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(EditActivity.this, R.raw.ringtone);
                        isRunning = true;
                        break;
                    case 4:
                        ring.stop();
                        ring.release();
                        ring = MediaPlayer.create(EditActivity.this, R.raw.ringtone4);
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

    public void getDataToActivity(){
        list = new ArrayList<alarm>();
        Database = database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = Database.rawQuery("SELECT*FROM Alarm", null);

        list.clear();
       for(int i=0; i<cursor.getCount(); i++){
           cursor.moveToPosition(i);
           //lay thoi gian do ra textView
           edtGio_edit.setText(cursor.getString(1));

           //neu la 1 thi Rung, 0 thi khong...
           if (cursor.getInt(3) == 1){
               cbVibration_edt.setChecked(true);
           }

           if(cursor.getInt(5) == 1){
               cbStatus.setChecked(true);
           }
       }

    }

    public void updateDataToDatabase(){
        int flag=0, flag_2=0, flag_3=0;
        if(cbVibration_edt.isChecked()){
            flag = 1;
        }

        if(cbStatus.isChecked()){
            flag_3 = 1;
        }

        if(spnRepeat_edt.getSelectedItem().equals("Everyday")){
            flag_2 = 1;
        }

        ContentValues values = new ContentValues();
        values.put("Time", formatTime(edtGio_edit.getText().toString()));
        values.put("Ringtone", spnRingTone_edt.getSelectedItem().toString());
        values.put("Vibration", flag);
        values.put("Repeat", flag_2);
        values.put("Status", flag_3);

        //lay du lieu tu fragmentHome gui qua
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("data");
        id = b.getInt("Id");

        if (validationTextViewGetTime()){
            Database = database.initDatabase(this, DATABASE_NAME);
            Database.update("Alarm", values, "ID = ?",new String[]{id+""});

            Toast.makeText(this, "Update sucessfully!", Toast.LENGTH_SHORT).show();

            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }else{
            Toast.makeText(this, "Not the time format!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validationTextViewGetTime(){
        String str = edtGio_edit.getText().toString();
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
        }else if(minute<10 ){
            m = "0" + minute;
        }else{
            m = minute + "";
        }

        //xử lý giờ
        if(hour == 0){
            h = "00";
        }else if(hour<10){
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
