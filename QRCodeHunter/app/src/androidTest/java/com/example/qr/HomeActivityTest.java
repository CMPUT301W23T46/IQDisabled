package com.example.qr;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import androidx.test.platform.app.InstrumentationRegistry;
import android.widget.ImageButton;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import android.widget.ImageView;
import org.junit.runner.RunWith;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import com.robotium.solo.Solo;

import java.util.List;

public class HomeActivityTest {
private Solo solo;

    @Rule
    public ActivityTestRule<HomeActivity> rule =
            new ActivityTestRule<>(HomeActivity.class,true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.qr", appContext.getPackageName());
    }

    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
    }
    @Test
    public void testSearchCancel(){
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",SearchActivity.class);
        onView(withId(R.id.cancel_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
    }



//    @Test
//    public void testViewQRCode() throws InterruptedException {
//        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
//
//        onView(withId(R.id.view_qr_codes_main_btn)).perform(click());
//        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);
//    }



    @Test
    public void testRanking(){
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.imageButton14)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", HighestScoreOfAllPlayerActivity.class);
    }


}
