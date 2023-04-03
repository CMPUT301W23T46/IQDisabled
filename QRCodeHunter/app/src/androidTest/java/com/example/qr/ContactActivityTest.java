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

public class ContactActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<ContactActivity> rule =
            new ActivityTestRule<>(ContactActivity.class,true, true);

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


}
