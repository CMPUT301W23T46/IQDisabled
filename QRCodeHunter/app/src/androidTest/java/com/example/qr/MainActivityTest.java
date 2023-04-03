package com.example.qr;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageButton;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import android.widget.ImageView;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.robotium.solo.Solo;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();

    }

    @Test
    public void checkJump2MyQRCode() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("View QR Codes");
        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);

    }


//    @Test
//    public void checkJump2MyQRCode() throws InterruptedException {
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        solo.clickOnButton("View QR Codes");
//        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);
//    }
//
//
//    @Test
//    public void checkSearch() {
//
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//
//        onView(withId(R.id.home_search_btn)).perform(click());
//        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
//
//    }
//
//    @Test
//    public void checkJumping() {
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        onView(withId(R.id.contact_btn)).perform(click());
//        solo.assertCurrentActivity("Wrong Activity",ContactActivity.class);
//    }
//
//    @Test
//    public void checkJumping2() {
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        onView(withId(R.id.add_btn)).perform(click());
//        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
//    }
//
//
//    @Test
//    public void checkJumping4() {
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        onView(withId(R.id.profile_btn)).perform(click());
//        solo.assertCurrentActivity("Wrong Activity",ProfileActivity.class);
//    }
    @Test
    public void checkJumping4() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.profile_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ProfileActivity.class);
    }
    @Test
    public void checkJumping5() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
    }
    @Test
    public void checkJumping6() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
//        solo.clickOnButton("btn_scan");
        onView(withId(R.id.btn_scan)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ShowQRCodeActivity.class);
    }


}




