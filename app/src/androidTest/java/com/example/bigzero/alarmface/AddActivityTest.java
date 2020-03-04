package com.example.bigzero.alarmface;

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

public class AddActivityTest extends ActivityInstrumentationTestCase2<AddActivity> {
    private Solo solo;

    private Button btnSetAlarm, btnCancel;
    private EditText edtTime;
    private Spinner spnRepeat, spnRintone;
    private CheckBox cbVibration;
    private TextView tvRingtone, tvVibration, tvRepeat;
    private ImageView imgChooseTime;
    private RelativeLayout relativeLayout;


    public AddActivityTest() {
        super(AddActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @SmallTest
    public void testValueControls() throws Exception{
        btnCancel = (Button)solo.getView(R.id.btnCancel);
        btnSetAlarm = (Button)solo.getView(R.id.btnOK);
        tvRepeat = (TextView)solo.getView(R.id.tvRepeat);
        tvRingtone = (TextView)solo.getView(R.id.tvRingTone);
        tvVibration = (TextView)solo.getView(R.id.tvVibration);

        assertEquals("Cancel", btnCancel.getText());
        assertEquals("Set Alarm", btnSetAlarm.getText());
        assertEquals("Vibration:", tvVibration.getText());
        assertEquals("Repeat:", tvRepeat.getText());
        assertEquals("Ringtone:", tvRingtone.getText());
    }

    @SmallTest
    public void testExistControls(){
        btnCancel = (Button)solo.getView(R.id.btnCancel);
        btnSetAlarm = (Button)solo.getView(R.id.btnOK);
        edtTime = (EditText)solo.getView(R.id.edtGio);
        spnRepeat = (Spinner)solo.getView(R.id.spnRepeat);
        spnRintone = (Spinner)solo.getView(R.id.spnRingTone);
        cbVibration = (CheckBox)solo.getView(R.id.cbVibration);
        tvRepeat = (TextView)solo.getView(R.id.tvRepeat);
        tvRingtone = (TextView)solo.getView(R.id.tvRingTone);
        tvVibration = (TextView)solo.getView(R.id.tvVibration);
        imgChooseTime = (ImageView)solo.getView(R.id.imgChooseTime);

        assertNotNull(btnCancel);
        assertNotNull(btnSetAlarm);
        assertNotNull(edtTime);
        assertNotNull(spnRepeat);
        assertNotNull(spnRintone);
        assertNotNull(cbVibration);
        assertNotNull(tvRepeat);
        assertNotNull(tvRingtone);
        assertNotNull(tvVibration);
        assertNotNull(imgChooseTime);
    }

    @SmallTest
    public void testHintEditText(){
        edtTime = (EditText)solo.getView(R.id.edtGio);
        assertEquals("HH:MM", edtTime.getHint());
    }

    @SmallTest
    public void testClickButtonSetAlarm(){
        solo.clickOnText("Set Alarm");
    }

    @SmallTest
    public void testClickButtonCancel(){
        solo.clickOnText("Cancel");
    }

    @SmallTest
    public void testLayoutExist(){
        relativeLayout = (RelativeLayout)solo.getView(R.id.rlAddActivity);
        assertNotNull(relativeLayout);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
