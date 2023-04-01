package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QRDetailActivity extends AppCompatActivity {
    ImageButton backBtn;
    Button mapBtn;
    TextView qrName;
    TextView qrRep;
    TextView qrScore;
    TextView qrComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String qr_name = extras.getString("qrName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_detail);
        backBtn = findViewById(R.id.detail_back_btn);
        mapBtn = findViewById(R.id.view_on_map_btn);
        qrName = findViewById(R.id.qrcode_detail_name);
        qrRep = findViewById(R.id.qrcode_detail_rep);
        qrScore = findViewById(R.id.qrcode_detail_score);
        qrComment = findViewById(R.id.qrcode_detail_comment);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRDetailActivity.this, PlayerCodeActivity.class);
                intent.putExtra("qrName",qr_name);
                startActivity(intent);
            }
        });

        qrName.setText(qr_name);

        DataBaseHelper dbhelper = new DataBaseHelper();
        dbhelper.getQRRepByName(qr_name, new OnGetQRRepListener() {
            @Override
            public void success(List<String> qrReps) {
                qrRep.setText(qrReps.get(0));
            }
            @Override
            public void failure(Exception e) {
            }
        });

        dbhelper.getQRScoreByName(qr_name, new OnGetQRScoreListener() {
            @Override
            public void success(List<String> qrScores) {
                qrScore.setText(qrScores.get(0));

            }
            @Override
            public void failure(Exception e) {

            }
        });

        dbhelper.getQRCommentByName(qr_name, new OnGetQRCommentListener() {
            @Override
            public void success(List<String> qrComs) {
                if (qrComs.size() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : qrComs) {
                        stringBuilder.append(s).append("\n");
                    }
                    String combinedString = stringBuilder.toString();
                    qrComment.setText(combinedString);
                }
            }
            @Override
            public void failure(Exception e) {
            }
        });
    }
}
