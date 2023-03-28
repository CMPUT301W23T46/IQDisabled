package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

/**
 * The ContactActivity class extends the AppCompatActivity class and is responsible for displaying
 * the QRcodes of other users. The class retrieves the user's contact information from
 * shared preferences and sets up click listeners for navigation buttons to the home screen, map screen,
 * profile screen, and add code screen.
 */
public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "ContactACtivity";
    /**
     * Called when the activity is starting. Retrieves the user's contact information from shared preferences
     * and sets up click listeners for navigation buttons to the home screen, map screen, profile screen,
     * and add code screen.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then
     *                             this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                             Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");
//        System.out.println(username);
//        System.out.println(email);
//        System.out.println(phone);
        ImageButton homeBtn = findViewById(R.id.home_btn);
        ImageButton addBtn = findViewById(R.id.add_btn);
        ImageButton mapBtn = findViewById(R.id.map_btn);
        ImageButton profileBtn = findViewById(R.id.profile_btn);

        ListView playerListView = findViewById(R.id.contact_player_list);

        DataBaseHelper dbhelper = new DataBaseHelper();
        dbhelper.getAllPlayer(new OnGetAllPlayerListener() {
            @Override
            public void success(Player[] players) {

                PlayerArrayAdapter adapter = new PlayerArrayAdapter(ContactActivity.this, players);
                playerListView.setAdapter(adapter);
            }

            @Override
            public void failure(Exception e) {
                Log.e(TAG, "Error retrieving players from database", e);
                Toast.makeText(ContactActivity.this, "Failed to retrieve player data", Toast.LENGTH_SHORT).show();
            }
        });




        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this,AddCodeActivity.class);
                startActivity(intent);
            }
        });
    }
}
