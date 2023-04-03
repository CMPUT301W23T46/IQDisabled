package com.example.qr;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
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


    @Test
    public void checkSearch() {

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

    }

    @Test
    public void checkJumping() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.contact_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ContactActivity.class);
    }

    @Test
    public void checkJumping2() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
    }



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
        solo.waitForText("QRCode Information", 1, 2000);
        solo.enterText((EditText) solo.getView(R.id.edit_comment), "Comment5");
        ViewInteraction checkbox = onView(withId(R.id.checkbox_geo));
        checkbox.perform(click());
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        solo.clickOnButton("View QR Codes");
        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);
        MyQRCodeActivity activity = (MyQRCodeActivity) solo.getCurrentActivity();
        final ListView codeList = activity.findViewById(R.id.qrcodes); // Get the listview
        SystemClock.sleep(1000);
        String code = ((QRCode) codeList.getItemAtPosition(0)).getHashCode(); // Get item from first position
        assertNotNull(code);
    }
    @Test
    public void checkJumping7() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.map_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",MapActivity.class);
    }
    @Test
    public void checkJumping8() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.contact_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ContactActivity.class);
        ContactActivity activity = (ContactActivity) solo.getCurrentActivity();
        final ListView codeList = activity.findViewById(R.id.contact_player_list); // Get the listview
        SystemClock.sleep(1000);
        String code = ((Player) codeList.getItemAtPosition(0)).getPlayName(); // Get item from first position
        assertNotNull(code);
    }

    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    @Test
    public void checkJumping9() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.profile_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ProfileActivity.class);
        String temp = getText(withId(R.id.edit_user_name));
        onView(withId(R.id.home_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",SearchActivity.class);
        solo.clickOnButton("Search for Player");

        solo.enterText((EditText) solo.getView(R.id.search_fragment_name_input), temp);
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Wrong Activity",ShowOtherProfile.class);
    }

    @Test
    public void checkJumping10() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",SearchActivity.class);
        solo.clickOnButton("Search for QR Code");
        solo.enterText((EditText) solo.getView(R.id.search_fragment_latitude_input), "53");
        solo.enterText((EditText) solo.getView(R.id.search_fragment_longitude_input), "-113");
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Wrong Activity",SearchByCodeMapActivity.class);
    }
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}




