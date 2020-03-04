package com.example.bigzero.alarmface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.bigzero.alarmface.db.database;


public class SettingActivity extends ActionBarActivity {
    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;
    private ImageView img1, img2, img3, img4, img5, img6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Setting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        img1 = (ImageView)findViewById(R.id.imgColor1);
        img2 = (ImageView)findViewById(R.id.imgColor2);
        img3 = (ImageView)findViewById(R.id.imgColor3);
        img4 = (ImageView)findViewById(R.id.imgColor4);
        img5 = (ImageView)findViewById(R.id.imgColor5);
        img6 = (ImageView)findViewById(R.id.imgColor6);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Black");
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Grey");
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Green");
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Red");
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Purple");
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateColorTable("Orange");
            }
        });
        changeColorActionBar();
    }

    public void updateColorTable(String v){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        int color = 0;
        switch (v){
            case "Black":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));
                color = 1;
                break;
            case "Grey":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                color = 2;
                break;
            case "Green":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                color = 3;
                break;
            case "Red":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                color = 4;
                break;
            case "Purple":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                color = 5;
                break;
            case "Orange":
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                color = 6;
                break;
            default:
                break;
        }

        ContentValues values = new ContentValues();
        values.put("Color", color);
        Database = database.initDatabase(this,DATABASE_NAME);
        Database.update("Color", values, "ID = ?",new String[]{1+""});
    }

    public void changeColorActionBar(){
        Database = database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = Database.rawQuery("SELECT * FROM Color",null);
        cursor.moveToFirst();
        int color = cursor.getInt(1);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        switch (color){
            case 1:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));
                break;
            case 2:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                break;
            case 3:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                break;
            case 4:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                break;
            case 5:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                break;
            case 6:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                break;
            default:
                break;
        }
    }

}
