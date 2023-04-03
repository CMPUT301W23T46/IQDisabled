package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * This class represents the activity to display the QR codes of a specific player.
 * It retrieves the player's name from the intent extras and retrieves the player's QR codes from the database.
 */
public class PlayerCodeActivity extends AppCompatActivity {
    private static final String TAG = "PlayerCodeActivity";
    ImageButton backBtn;
    ListView codeListView;

    /**
     * Overrides the onCreate method of the AppCompatActivity class to initialize the activity and retrieve the player's QR codes from the database.
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String playerName;
        if (extras != null) {
            playerName = extras.getString("name");
        } else {
            playerName = "Unavailable";
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_qrcode);
        backBtn = findViewById(R.id.player_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerCodeActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        codeListView = findViewById(R.id.player_qrcodes);

        DataBaseHelper dbhelper = new DataBaseHelper();

        dbhelper.getPlayerQRCodes(playerName, new OnGetPlayerQRCodesListener() {

            /**
             * Overrides the success method of the OnGetPlayerQRCodesListener interface to set the adapter of the QR code list view with the retrieved codes.
             *
             * @param qrCodes a list of String objects containing the QR codes of the player
             */
            @Override
            public void success(List<String> qrCodes) {
                PlayerCodeArrayAdapter adapter = new PlayerCodeArrayAdapter(PlayerCodeActivity.this, qrCodes);
                codeListView.setAdapter(adapter);
            }
            /**
             * Overrides the failure method of the OnGetPlayerQRCodesListener interface to display an error message if the retrieval of the QR codes fails.
             *
             * @param e the exception that caused the failure of the operation
             */
            @Override
            public void failure(Exception e) {
                Log.e(TAG, "Error retrieving players'code from database", e);
                Toast.makeText(PlayerCodeActivity.this, "Failed to retrieve code data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}







