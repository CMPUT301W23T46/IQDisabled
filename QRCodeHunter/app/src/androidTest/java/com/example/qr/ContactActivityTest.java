package com.example.qr;

import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.platform.app.InstrumentationRegistry;
import android.widget.ImageButton;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import android.widget.ImageView;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import com.robotium.solo.Solo;
/**
 * This class provides a set of automated tests for the ContactActivity class of the QR app.
 * It utilizes the Solo and ActivityTestRule classes to perform UI testing on the ContactActivity.
 *
 * @see Solo
 * @see ActivityTestRule
 * @see ContactActivity
 */
public class ContactActivityTest {
    private Solo solo;
    /**
     * The ActivityTestRule is a JUnit rule that launches the ContactActivity under test
     * before each test method in this class.
     */
    @Rule
    public ActivityTestRule<ContactActivity> rule =
            new ActivityTestRule<>(ContactActivity.class,true, true);

    /**
     * This method runs before each test method in this class and initializes the Solo object
     * with the instrumentation context and the ContactActivity under test.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * This test method verifies that the ContactActivity is started successfully.
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
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
