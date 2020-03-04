package com.example.bigzero.alarmface;

import android.support.design.widget.BottomNavigationView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.FrameLayout;

import com.robotium.solo.Solo;

/**
 * Created by asus on 20/04/2017.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
    private Solo solo;

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    public MainActivityTest() {
        super(MainActivity.class);
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
    public void testExist(){
        frameLayout = (FrameLayout)solo.getView(R.id.main_container);
        bottomNavigationView = (BottomNavigationView)solo.getView(R.id.bottom_navigation);

        assertNotNull(frameLayout);
        assertNotNull(bottomNavigationView);
    }

    @SmallTest
    public void testFragmentHome(){
        solo.clickOnText("HOME");
        assertTrue(solo.waitForText("HOME"));
    }

    @SmallTest
    public void testFragmentAbout(){
        solo.clickOnText("ABOUT");
        assertTrue(solo.waitForText("ABOUT"));
    }

    @SmallTest
    public void testFragmentWeather(){
        solo.clickOnText("WEATHER");
        assertTrue(solo.waitForText("WEATHER"));
    }
}
