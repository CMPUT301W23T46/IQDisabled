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
 * An activity used to display a ListView of QR codes associated with a particular player.
 */
public class PlayerCodeActivity extends AppCompatActivity {
    private static final String TAG = "PlayerCodeActivity";
    ImageButton backBtn;
    ListView codeListView;

    /**
     * Creates the PlayerCodeActivity and sets up the ListView of QR codes associated with the player.
     *
     * @param savedInstanceState the previously saved state of the activity
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

            @Override
            /**
             * Called when the QR codes associated with the player are successfully retrieved from the database.
             * This method creates a new PlayerCodeArrayAdapter and sets it as the adapter for the ListView.
             *
             * @param qrCodes a List of strings representing the QR codes associated with the player
             */
            public void success(List<String> qrCodes) {
                PlayerCodeArrayAdapter adapter = new PlayerCodeArrayAdapter(PlayerCodeActivity.this, qrCodes);
                codeListView.setAdapter(adapter);
            }

            @Override
            /**
             * Called when an error occurs while retrieving the QR codes associated with the player from the database.
             *
             * @param e an Exception object representing the error that occurred during the database operation
             */
            public void failure(Exception e) {
                Log.e(TAG, "Error retrieving players'code from database", e);
                Toast.makeText(PlayerCodeActivity.this, "Failed to retrieve code data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}