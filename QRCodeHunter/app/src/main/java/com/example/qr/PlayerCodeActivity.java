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


public class PlayerCodeActivity extends AppCompatActivity {
    private static final String TAG = "PlayerCodeActivity";
    ImageButton backBtn;
    ListView codeListView;


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
            public void success(List<String> qrCodes) {
                PlayerCodeArrayAdapter adapter = new PlayerCodeArrayAdapter(PlayerCodeActivity.this, qrCodes);
                codeListView.setAdapter(adapter);
            }

            @Override
            public void failure(Exception e) {
                Log.e(TAG, "Error retrieving players'code from database", e);
                Toast.makeText(PlayerCodeActivity.this, "Failed to retrieve code data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}







