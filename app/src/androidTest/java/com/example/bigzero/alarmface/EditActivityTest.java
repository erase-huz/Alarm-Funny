package com.example.bigzero.alarmface;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by asus on 20/04/2017.
 */

public class EditActivityTest extends ActivityInstrumentationTestCase2<EditActivity> {
    private Solo solo;

    private Button btnSave_edt, btnCancel_edt;
    private EditText edtTime_edt;
    private Spinner spnRepeat_edt, spnRintone_edt;
    private CheckBox cbVibration_edt;
    private TextView tvRingtone_edt, tvVibration_edt, tvRepeat_edt, tvSetAlarm_edt;
    private ImageView imgChooseTime_edt;
    private RelativeLayout relativeLayout;

    public EditActivityTest() {
        super(EditActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @SmallTest
    public void testValue() throws Exception{
        btnCancel_edt = (Button)solo.getView(R.id.btnCancel_edt);
        btnSave_edt = (Button)solo.getView(R.id.btnOK_edt);
        tvRepeat_edt = (TextView)solo.getView(R.id.tvRepeat_edt);
        tvRingtone_edt = (TextView)solo.getView(R.id.tvRingTone_edt);
        tvVibration_edt = (TextView)solo.getView(R.id.tvVibration_edt);
        tvSetAlarm_edt = (TextView)solo.getView(R.id.tvSetAlarm_edt);

        assertEquals("Cancel", btnCancel_edt.getText());
        assertEquals("Save", btnSave_edt.getText());
        assertEquals("Vibration:", tvVibration_edt.getText());
        assertEquals("Repeat:", tvRepeat_edt.getText());
        assertEquals("Ringtone:", tvRingtone_edt.getText());
        assertEquals("Set Alarm:", tvSetAlarm_edt.getText());
    }

    @SmallTest
    public void testExist(){
        btnCancel_edt = (Button)solo.getView(R.id.btnCancel_edt);
        btnSave_edt = (Button)solo.getView(R.id.btnOK_edt);
        edtTime_edt = (EditText)solo.getView(R.id.edtGio_edit);
        spnRepeat_edt = (Spinner)solo.getView(R.id.spnRepeat_edt);
        spnRintone_edt = (Spinner)solo.getView(R.id.spnRingTone_edt);
        cbVibration_edt = (CheckBox)solo.getView(R.id.cbVibration_edt);
        tvRepeat_edt = (TextView)solo.getView(R.id.tvRepeat_edt);
        tvRingtone_edt = (TextView)solo.getView(R.id.tvRingTone_edt);
        tvVibration_edt = (TextView)solo.getView(R.id.tvVibration_edt);
        imgChooseTime_edt = (ImageView)solo.getView(R.id.imgChooseTime_edit);

        assertNotNull(btnCancel_edt);
        assertNotNull(btnSave_edt);
        assertNotNull(edtTime_edt);
        assertNotNull(spnRepeat_edt);
        assertNotNull(spnRintone_edt);
        assertNotNull(cbVibration_edt);
        assertNotNull(tvRepeat_edt);
        assertNotNull(tvRingtone_edt);
        assertNotNull(tvVibration_edt);
        assertNotNull(imgChooseTime_edt);
    }

    @SmallTest
    public void testHint(){
        edtTime_edt = (EditText)solo.getView(R.id.edtGio_edit);
        assertEquals("HH:MM", edtTime_edt.getHint());
    }

    @SmallTest
    public void testClickButtonSave(){
        Intent in = new Intent(getActivity(), EditActivity.class);
        Bundle b = new Bundle();
        b.putInt("Id", 1);
        in.putExtra("data", b);
        getActivity().startActivity(in);
        solo.clickOnText("Save");
    }

    @SmallTest
    public void testClickButtonCancel(){
        solo.clickOnText("Cancel");
    }

    @SmallTest
    public void testLayoutExist(){
        relativeLayout = (RelativeLayout) solo.getView(R.id.rlEditActivity);
        assertNotNull(relativeLayout);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
