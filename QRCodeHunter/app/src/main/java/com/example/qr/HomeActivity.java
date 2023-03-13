package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;

/**
 * The HomeActivity class extends the AppCompatActivity class and is responsible for displaying
 * the entry to show user's qrcodes and the entry for searching up other user's profile and qrcodes
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Retrieves the user's contact information from shared preferences
     * and sets up click listeners for navigation buttons to the home screen, map screen, profile screen,
     * and add code screen.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then
     *                             this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                             Otherwise, it is null.
     */
    ImageButton searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");
//        System.out.println(username);
//        System.out.println(email);
//        System.out.println(phone);
        ImageButton contactBtn = findViewById(R.id.contact_btn);
        ImageButton addBtn = findViewById(R.id.add_btn);
        ImageButton mapBtn = findViewById(R.id.map_btn);
        ImageButton profileBtn = findViewById(R.id.profile_btn);
        ImageButton highestScoreBtn = findViewById(R.id.imageButton14);
        Button viewQRCodesBtn = findViewById(R.id.view_qr_codes_main_btn);
        ImageButton searchBtn = findViewById(R.id.home_search_btn);






//        Toast.makeText(this, player1.getEmail()+player1.getPhone_number(), Toast.LENGTH_SHORT).show();
                contactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                        startActivity(intent);
                    }
                });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddCodeActivity.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        viewQRCodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyQRCodeActivity.class);
                startActivity(intent);
            }
        });
        highestScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HighestScoreOfAllPlayerActivity.class);
                startActivity(intent);
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
