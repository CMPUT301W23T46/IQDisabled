package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ShowOtherProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);

        Intent intent = getIntent();

        String username = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        TextView tw = findViewById(R.id.e_phone_number);
        TextView tw2 = findViewById(R.id.e_email_address);
        TextView tw3 = findViewById(R.id.e_user_name);

        tw.setText(phone);
        tw2.setText(email);
        tw3.setText(username);

        Button back_btn = findViewById(R.id.go_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowOtherProfile.this,HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
