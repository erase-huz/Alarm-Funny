package com.example.bigzero.alarmface;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bigzero.alarmface.db.database;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private String myString = "";

    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomnavigationview();
        changeColorActionBar();
    }
    public String getMyData(){
        return myString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent in=new Intent(MainActivity.this,SettingActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.add) {
            Intent inn=new Intent(MainActivity.this,AddActivity.class);
            startActivity(inn);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Hàm 3 nút menu dưới
    public void bottomnavigationview(){
        bottomNavigation=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.menublow);
        fragment = new FragmentHome();
        changeFragnment();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_weather:
                        fragment = new FragmentWeather();
                        break;
                    case R.id.action_home:
                        fragment = new FragmentHome();
                        break;
                    case R.id.action_about:
                        fragment = new FragmentAbout();
                        break;
                }
                changeFragnment();
                return true;
            }
        });
    }
    // thay đổi qua các fragment
    void changeFragnment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
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
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color1)));
                break;
            case 2:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color2)));
                break;
            case 3:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color3)));
                break;
            case 4:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color4)));
                break;
            case 5:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color5)));
                break;
            case 6:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                bottomNavigation.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color6)));
                break;
            default:
                break;
        }
    }
}
