package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText username = findViewById(R.id.edit_user_name);
        EditText password = findViewById(R.id.edit_user_pass);
        EditText email = findViewById(R.id.edit_email);
        EditText phoneNum = findViewById(R.id.edit_phone_number);

        Button cancel = findViewById(R.id.cancel_button);
        Button signUp = findViewById(R.id.sign_up_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                String emailString = email.getText().toString();
                String phoneString = phoneNum.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username",usernameString);
                editor.putString("password",passwordString);
                editor.putString("email",emailString);
                editor.putString("phone",phoneString);
                editor.putBoolean("loggedIn", true);
                editor.apply();

                Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}