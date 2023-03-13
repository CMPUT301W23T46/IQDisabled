package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * The MyQRCodeActivity class extends the AppCompatActivity class and is responsible for displaying the qrcodes I have
 * added to my account.
 */
public class MyQRCodeActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Retrieves the user's contact information from shared preferences
     * and sets up click listeners for navigation buttons to the home screen, map screen, profile screen,
     * and add code screen.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcodes);

        Button statisticsButton = findViewById(R.id.bottom_button_1);
        Button RankingButton = findViewById(R.id.bottom_button_2);
        Button RemoveButton = findViewById(R.id.bottom_button_3);
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

        RankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, MyRankingActivity.class);
                startActivity(intent);
            }
        });



    }
}