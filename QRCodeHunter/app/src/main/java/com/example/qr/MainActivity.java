package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * The MainActivity class extends the AppCompatActivity class and is responsible for displaying
 * signing in page. And if it's logged in before, don't show this page again.
 */
public class MainActivity extends AppCompatActivity {
    private Button remove_btn;

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
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        boolean loggedIn = sharedPref.getBoolean("loggedIn", false);

        if (loggedIn) {
            // 如果已经登录，则启动HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }


        Button signUpButton = (Button) findViewById(R.id.sign_up_button);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
//                Button cancel = (Button) findViewById(R.id.cancel_button);

                
            }
        });
    }

}