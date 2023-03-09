package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcodes);

        Button statisticsButton = findViewById(R.id.bottom_button_1);
        Button rankingButton = findViewById(R.id.bottom_button_2);
        Button mapBtn = findViewById(R.id.bottom_button_3);
        ImageButton backButton = findViewById(R.id.imageButton13);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });



    }
}