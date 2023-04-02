package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**

 The ProfileActivity displays the user's profile information, which is retrieved from shared preferences
 and displayed in the UI elements. It also sets up click listeners for the navigation buttons that will
 take the user to other parts of the app.
 */

public class ProfileActivity extends AppCompatActivity {
    /**
     onCreate method is called when the activity is created. It sets the layout, retrieves the user's
     profile information from shared preferences, and displays the information in the UI elements.
     It also sets up click listeners for the navigation buttons.
     @param savedInstanceState Bundle object containing the activity's previously saved state.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        ImageButton contactBtn = findViewById(R.id.contact_btn);

        TextView u_profile_name = findViewById(R.id.edit_user_name);
        TextView u_profile_phone = findViewById(R.id.edit_phone_number);
        TextView u_profile_email = findViewById(R.id.edit_email_address);

        u_profile_email.setText(email);
        u_profile_name.setText(username);
        u_profile_phone.setText(phone);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,AddCodeActivity.class);
                startActivity(intent);
            }
        });




    }

}
