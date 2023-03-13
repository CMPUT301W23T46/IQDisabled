package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**

 * The HighestScoreOfAllPlayerActivity class displays the highest score of all players in the game.

 * It extends the AppCompatActivity class.
 */
public class HighestScoreOfAllPlayerActivity extends AppCompatActivity {

    /**

     Called when the activity is starting. This is where most initialization should go.
     @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highest_score_player);
        ImageButton backButton = findViewById(R.id.high_score_back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighestScoreOfAllPlayerActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}