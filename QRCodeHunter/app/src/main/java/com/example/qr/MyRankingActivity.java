package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The MyRankingActivity class extends AppCompatActivity and is responsible for displaying the ranking page
 * which shows the ranking by scores in descending order.
 */
public class MyRankingActivity extends AppCompatActivity {
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
        setContentView(R.layout.my_ranking);
        ImageView backBtn = findViewById(R.id.my_ranking_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRankingActivity.this, MyQRCodeActivity.class);
                startActivity(intent);
            }
        });

    }

}
