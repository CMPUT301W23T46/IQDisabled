package com.example.qr;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Activity for uploading photos
 */
public class UploadPhotoActivity extends AppCompatActivity {
    /**
     * Called when the activity is created
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo);

        Button upload_btn = findViewById(R.id.upload_btn);
        ImageButton back_btn = findViewById(R.id.upload_back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadPhotoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo
            }
        });
    }
}
