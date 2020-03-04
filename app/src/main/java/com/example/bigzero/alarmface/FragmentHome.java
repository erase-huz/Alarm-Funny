package com.example.bigzero.alarmface;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigzero.alarmface.adapter.AdapterAlarm;
import com.example.bigzero.alarmface.adapter.alarm;
import com.example.bigzero.alarmface.db.database;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private String DATABASE_NAME = "Alarm.sqlite";
    private SQLiteDatabase Database;

    private ListView listView;
    private ArrayList<alarm> list;
    private AdapterAlarm adapterAlarm;

    private TextView tvNotification;

    public FragmentHome() {}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_home, container, false);

        tvNotification = (TextView)view.findViewById(R.id.tvNotification);

        listView = (ListView)view.findViewById(R.id.lv);
        //khoi tao arraylist va adapter
        list = new ArrayList<alarm>();
        adapterAlarm = new AdapterAlarm(list, getActivity());
        //set adapter len listview
        listView.setAdapter(adapterAlarm);
        //đăng ký context menu
        registerForContextMenu(view.findViewById(R.id.lv));
        getData();
        return  view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getActivity().getMenuInflater().inflate(R.menu.menu_listview_home, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        alarm al = list.get(info.position);
        int id = al.getId();
        switch(item.getItemId()) {
            case R.id.edit:
                //sửa báo thức
                Intent in = new Intent(getActivity(), EditActivity.class);
                Bundle b = new Bundle();
                b.putInt("Id", id);
                in.putExtra("data", b);
                startActivity(in);
                break;
            case R.id.delete:
                //xóa báo thức
                deleteItem(id);
                break;
        }
        return true;
    }

    public void getData(){
        //read data from database to listview
        Database = database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = Database.rawQuery("SELECT*FROM Alarm", null);
        list.clear();

        for(int i=0; i<cursor.getCount(); i++){
            //move to cursor i
            cursor.moveToPosition(i);
            //add list
            list.add(new alarm(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }

        //notify data
        adapterAlarm.notifyDataSetChanged();
        //show text "NO alarms set"
        if (list.size() == 0){
            tvNotification.setVisibility(View.VISIBLE);
        }
    }

    private void deleteItem(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure, Do you want delete?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Database = database.initDatabase(getActivity(), DATABASE_NAME);
                Database.delete("Alarm", "ID = ?", new String[]{id+""});

                //doc lại dữ liệu cho listview
                Database = database.initDatabase(getActivity(), DATABASE_NAME);
                Cursor cursor = Database.rawQuery("SELECT*FROM Alarm", null);
                list.clear();
                for(int i=0; i<cursor.getCount(); i++){
                    //di chuyển cursor đến vị trí thứ i
                    cursor.moveToPosition(i);
                    //thêm đối tượng vào list
                    list.add(new alarm(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                            , cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
                }
                //bat su kien khi co thay doi du lieu tren adapter
                adapterAlarm.notifyDataSetChanged();

                if (list.size() <= 0){
                    tvNotification.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getActivity(), "Delete sucessfully!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setIcon(R.drawable.logo);
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
