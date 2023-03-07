package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
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



    }
}
