package com.example.qr;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import com.robotium.solo.Solo;

import java.util.List;


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
    public void checkSignUp() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_user_name), "user5");
        solo.enterText((EditText) solo.getView(R.id.edit_user_pass), "123");
        solo.enterText((EditText) solo.getView(R.id.edit_phone_number), "123456");
        solo.enterText((EditText) solo.getView(R.id.edit_email), "123@yahoo.com");
        solo.clickOnButton("sign up");
        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);
        solo.clickOnImageButton(3);
        ProfileActivity profileActivity = (ProfileActivity) solo.getCurrentActivity();

    }


    @Test
    public void checkJump2Profile() {
        solo.assertCurrentActivity("Wrong Activity", HomeActivity.class);
        List<ImageButton> imageButtons = solo.getCurrentViews(ImageButton.class);
        if (imageButtons.size() >= 5) {
            solo.clickOnImageButton(4);
        }
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }



}




