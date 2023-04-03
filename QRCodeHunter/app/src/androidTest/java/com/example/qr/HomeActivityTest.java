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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import com.robotium.solo.Solo;

import java.util.List;
/**
 * This class provides a set of automated tests for the HomeActivity class of the QR app.
 * It utilizes the Solo, ActivityTestRule, and Espresso classes to perform UI testing on the HomeActivity.
 *
 * @see Solo
 * @see ActivityTestRule
 * @see Espresso
 * @see HomeActivity
 */
public class HomeActivityTest {
private Solo solo;
    /**
     * The ActivityTestRule is a JUnit rule that launches the HomeActivity under test
     * before each test method in this class.
     */
    @Rule
    public ActivityTestRule<HomeActivity> rule =
            new ActivityTestRule<>(HomeActivity.class,true, true);
    /**
     * This method runs before each test method in this class and initializes the Solo object
     * with the instrumentation context and the HomeActivity under test.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * This test method verifies that the HomeActivity is started successfully.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    /**
     * This test method verifies that the app context is retrieved successfully
     * and the package name matches the expected value.
     */
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.qr", appContext.getPackageName());
    }
    /**
     * This test method verifies that the search button launches the SearchActivity.
     */
    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
    }
    /**
     * This test method verifies that the cancel button in the SearchActivity
     * returns the user to the HomeActivity.
     */
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


    /**
     * This test method verifies that the ranking button launches the HighestScoreOfAllPlayerActivity.
     */
    @Test
    public void testRanking(){
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.imageButton14)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", HighestScoreOfAllPlayerActivity.class);
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
