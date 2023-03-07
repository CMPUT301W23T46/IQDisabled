package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button remove_btn;
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