package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The ContactActivity class extends the AppCompatActivity class and is responsible for displaying
 * the QRcodes of other users. The class retrieves the user's contact information from
 * shared preferences and sets up click listeners for navigation buttons to the home screen, map screen,
 * profile screen, and add code screen.
 */
public class ContactActivity extends AppCompatActivity {
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
