package com.example.qr;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageButton;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import android.widget.ImageView;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import com.robotium.solo.Solo;

import java.util.List;

/**
 * The MainActivityTest class contains tests for the main activity of the application.
 */
public class MainActivityTest {
    private Solo solo;
    @Rule
    /**
     * The rule to launch the main activity before each test.
     */
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true, true);
    /**
     * Sets up the Solo object with the activity under test.
     * @throws Exception if an error occurs during setup.
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }
    /**
     * Tests that the main activity starts successfully.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    /**
     * Tests that clicking the "View QR Codes" button starts the MyQRCodeActivity.
     * @throws InterruptedException if an error occurs during the test.
     */
    @Test
    public void checkJump2MyQRCode() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("View QR Codes");
        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);
    }
    /**
     * Tests that clicking the search button starts the SearchActivity.
     */
    @Test
    public void checkSearch() {

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

    }
    /**
     * Tests that clicking the contact button starts the ContactActivity.
     */
    @Test
    public void checkJumping() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.contact_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ContactActivity.class);
    }
    /**
     * Tests that clicking the add button starts the AddCodeActivity.
     */
    @Test
    public void checkJumping2() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
    }
    /**
     * Tests that clicking the profile button starts the ProfileActivity.
     */
    @Test
    public void checkJumping4() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.profile_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ProfileActivity.class);
    }
}