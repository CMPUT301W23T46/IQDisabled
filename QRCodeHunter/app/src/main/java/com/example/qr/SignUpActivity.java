package com.example.qr;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
/**
 The SignUpActivity class is responsible for allowing the user to sign up for the application
 */
public class SignUpActivity extends AppCompatActivity {

    Player player = new Player("","","");
    /**
     This method is called when the activity is first created.
     It sets the content view and initializes the UI elements such as EditText and Button views.
     @param savedInstanceState - a Bundle object containing data about the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText username = findViewById(R.id.edit_user_name);
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
                String emailString = email.getText().toString();
                String phoneString = phoneNum.getText().toString();
                player.setPlayName(usernameString);
                player.setEmail(emailString);
                player.setPhone_number(phoneString);
                QRCode[] qrCodeList = new QRCode[0];
                DataBaseHelper dbHelper = new DataBaseHelper();
                OnCheckQRCodeExistListener listener = new OnCheckQRCodeExistListener() {
                    @Override
                    public void onSuccess(boolean exists) {
                        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (exists) {
                            editor.putBoolean("loggedIn", false);
                            editor.apply();
                            Intent intent = new Intent(SignUpActivity.this,SignUpActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUpActivity.this, "The user name exists, please try other names", Toast.LENGTH_SHORT).show();
                        } else {
                            editor.putString("username",usernameString);
                            editor.putString("email",emailString);
                            editor.putString("phone",phoneString);
                            editor.putBoolean("loggedIn", true);
                            editor.apply();

                            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                            startActivity(intent);
                            dbHelper.pushPlayer(player, qrCodeList);
                        }
                    }
                };
                try {
                    dbHelper.checkUserNameExist(player.getPlayName(), listener);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
