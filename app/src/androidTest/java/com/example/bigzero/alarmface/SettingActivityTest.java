package com.example.bigzero.alarmface;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by asus on 20/04/2017.
 */

public class SettingActivityTest extends ActivityInstrumentationTestCase2<SettingActivity> {
    private Solo solo;

    private TextView tvColor;
    private ImageView img1, img2, img3, img4, img5, img6;

    public SettingActivityTest() {
        super(SettingActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    @SmallTest
    public void testValue(){
        tvColor = (TextView)solo.getView(R.id.tvColor);
        assertEquals("Color Background:", tvColor.getText());
    }

    @SmallTest
    public void testExist(){
        tvColor = (TextView)solo.getView(R.id.tvColor);
        assertNotNull(tvColor);

        img1 = (ImageView)solo.getView(R.id.imgColor1);
        img2 = (ImageView)solo.getView(R.id.imgColor2);
        img3 = (ImageView)solo.getView(R.id.imgColor3);
        img4 = (ImageView)solo.getView(R.id.imgColor4);
        img5 = (ImageView)solo.getView(R.id.imgColor5);
        img6 = (ImageView)solo.getView(R.id.imgColor6);
        assertNotNull(img1);
        assertNotNull(img2);
        assertNotNull(img3);
        assertNotNull(img4);
        assertNotNull(img5);
        assertNotNull(img6);
    }
}
