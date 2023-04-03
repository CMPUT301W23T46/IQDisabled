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
 * The HighestScoreOfAllPlayerActivity class is responsible for displaying the highest, lowest, and total scores for
 * a player's QR codes, as well as the highest score across all players' QR codes.
 * It retrieves the necessary data from a Firestore database and displays it in the UI.
 */
public class HighestScoreOfAllPlayerActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting. It initializes the layout and sets up the UI components.
     * It retrieves the necessary data from a Firestore database, such as the player's QR codes and their scores,
     * and displays it in the UI.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state
     */
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
            /**
             * This method is called when a Firestore database operation is completed successfully. It retrieves an array of
             * QR code hashcodes from the task result, and then performs an action, such as retrieving the scores for each QR code.
             *
             * @param hashcodes an array of strings representing the hashcodes for each QR code retrieved from the database
             */
            public void onSuccess(String[] hashcodes) {
                List<Integer> scoresList = new ArrayList<>();
                for (String hashcode : hashcodes) {
                    dbhelper.getQRcodeScore(hashcode, new OnGetQRCodeScoreListener() {
                        @Override
                        /**
                         * This method is called when a Firestore database operation is completed successfully. It retrieves an integer score
                         * from the task result, and then performs an action, such as adding the score to a list or displaying it in the UI.
                         *
                         * @param score an integer representing the score retrieved from the database
                         */
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
            /**
             * This method is called when a Firestore database operation is completed successfully. It retrieves an array of
             * QR code hashcodes from the task result, and then performs an action, such as retrieving the scores for each QR code.
             * The method throws an InterruptedException if there is an error with the thread.
             *
             * @param hashcodes an array of strings representing the hashcodes for each QR code retrieved from the database
             * @throws InterruptedException if there is an error with the thread
             */
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