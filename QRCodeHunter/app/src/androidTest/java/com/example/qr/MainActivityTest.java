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

/**
 * This class provides a set of automated tests for the MainActivity class of the QR app.
 * It utilizes the Solo, ActivityTestRule, and Espresso classes to perform UI testing on the MainActivity.
 *
 * @see Solo
 * @see ActivityTestRule
 * @see Espresso
 * @see MainActivity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Solo solo;
    /**
     * The ActivityTestRule is a JUnit rule that launches the MainActivity under test
     * before each test method in this class.
     */
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true, true);
    /**
     * This method runs before each test method in this class and initializes the Solo object
     * with the instrumentation context and the MainActivity under test.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }
    /**
     * This test method verifies that the MainActivity is started successfully.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();

    }
    /**
     * This test method verifies that the "View QR Codes" button launches the MyQRCodeActivity.
     */

    @Test
    public void checkJump2MyQRCode() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("View QR Codes");
        solo.assertCurrentActivity("Wrong Activity", MyQRCodeActivity.class);
    }


    /**
     * This test method verifies that the search button launches the SearchActivity.
     */
    @Test
    public void checkSearch() {

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

        onView(withId(R.id.home_search_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

    }
    /**
     * This test method verifies that the contact button launches the ContactActivity.
     */
    @Test
    public void checkJumping() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.contact_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ContactActivity.class);
    }
    /**
     * This test method verifies that the add button launches the AddCodeActivity.
     */
    @Test
    public void checkJumping2() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
    }
    /**
     * This test method verifies that the profile button launches the ProfileActivity.
     */
    @Test
    public void checkJumping4() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.profile_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",ProfileActivity.class);
    }
    /**
     * This test method verifies that the "Back" button in the AddCodeActivity returns the user to the HomeActivity.
     */
    @Test
    public void checkJumping5() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.add_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",AddCodeActivity.class);
        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity",HomeActivity.class);
    }
    /**
     * This test method verifies that a new QR code is added successfully.
     * It first launches the AddCodeActivity, then simulates scanning a QR code
     * and entering comment and geolocation information before submitting the code.
     * Finally, it verifies that the code is added to the user's QR codes in the MyQRCodeActivity.
     */
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
    /**
     * This test method verifies that the map button launches the MapActivity.
     */
    @Test
    public void checkJumping7() {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        onView(withId(R.id.map_btn)).perform(click());
        solo.assertCurrentActivity("Wrong Activity",MapActivity.class);
    }
    /**
     * This test method verifies that the contact button launches the ContactActivity,
     * and that the list of players is displayed correctly in the ContactActivity.
     */
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
    /**
     * This test method verifies that the profile button launches the ProfileActivity,
     * and that the user's name is correctly displayed in the ProfileActivity.
     */
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
    /**
     * This test method verifies that the search for QR code button launches the SearchByCode
     * activity with correct longitude and latitude information entered by the user.
     */
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
}




