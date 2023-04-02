package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");
        ImageButton backButton = findViewById(R.id.high_score_back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighestScoreOfAllPlayerActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        DataBaseHelper dbhelper = new DataBaseHelper();
        dbhelper.getAllQRCode(new OnGetAllQRCodeListener() {
            @Override
            public void onSuccess(String[] hashcodes) {
                List<Integer> scoresList = new ArrayList<>();
                for (String hashcode : hashcodes) {
                    dbhelper.getQRcodeScore(hashcode, new OnGetQRCodeScoreListener() {
                        @Override
                        public void onSuccess(Integer score) {
                            scoresList.add(score);
                            if (scoresList.size() == hashcodes.length) {
                                int[] scoresArray = scoresList.stream().mapToInt(Integer::intValue).toArray();
                                Arrays.sort(scoresArray);
                                TextView game_wide = findViewById(R.id.game_wide);
                                game_wide.setText(String.valueOf(scoresArray[hashcodes.length-1]));
                            }
                        }
                    });
                }
            }
        });

        dbhelper.getQRCodeByName_hash(username, new OnGetHashByUsernameListener() {
            @Override
            public void onSuccess(String[] hashcodes) throws InterruptedException {
                // Declare an ArrayList to store the scores
                List<Integer> scoresList = new ArrayList<>();
                TextView num_scan = findViewById(R.id.num_scan);
                num_scan.setText(String.valueOf(hashcodes.length));

                // Iterate through the hashcodes and retrieve the scores
                for (String hashcode : hashcodes) {
                    dbhelper.getQRcodeScore(hashcode, new OnGetQRCodeScoreListener() {
                        @Override
                        public void onSuccess(Integer score) {
                            // Add the score to the scoresList
                            scoresList.add(score);

                            // Check if all the scores have been retrieved
                            if (scoresList.size() == hashcodes.length) {
                                // All scores have been retrieved, do something with them
                                int[] scoresArray = scoresList.stream().mapToInt(Integer::intValue).toArray();
                                // Call a method that depends on scoresArray
                                Arrays.sort(scoresArray);
                                TextView highest = findViewById(R.id.highest_score);
                                TextView lowest = findViewById(R.id.lowest_score);
                                highest.setText(String.valueOf(scoresArray[hashcodes.length-1]));
                                lowest.setText(String.valueOf(scoresArray[0]));
                                int sumUp = 0;
                                for (int s: scoresArray) {
                                    sumUp+=s;
                                }
                                TextView scoreSum = findViewById(R.id.score_sum);
                                scoreSum.setText(String.valueOf(sumUp));
                            }
                        }
                    });
                }
            }
        });

    }
}