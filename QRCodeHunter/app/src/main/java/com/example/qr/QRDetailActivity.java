package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
/**
 * The QRDetailActivity displays details about a specific QR code, including its name, representative,
 * score, and any comments associated with it. It also provides a button to view the QR code on a map.
 */
public class QRDetailActivity extends AppCompatActivity {
    ImageButton backBtn;
    Button mapBtn;
    TextView qrName;
    TextView qrRep;
    TextView qrScore;
    TextView qrComment;

    @Override
    /**
     * Initializes the UI elements and sets the QR code's name. Retrieves the QR code's representative, score,
     * and comments from the database, and displays them in the corresponding text views.
     *
     * @param savedInstanceState saved instance state of the activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String qr_name = extras.getString("qrName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_detail);
        mapBtn = findViewById(R.id.view_on_map_btn);
        qrName = findViewById(R.id.qrcode_detail_name);
        qrRep = findViewById(R.id.qrcode_detail_rep);
        qrScore = findViewById(R.id.qrcode_detail_score);
        qrComment = findViewById(R.id.qrcode_detail_comment);
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

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dbHelper = new DataBaseHelper();
                dbHelper.getGeoByName(qr_name, new OnGetGeoListener() {
                    @Override
                    public void success(List<String> geoPoint) {
                        Intent intent = new Intent(QRDetailActivity.this, MapActivity.class);
                        intent.putExtra("qrName", qr_name);
                        intent.putExtra("qrLat", geoPoint.get(0));
                        intent.putExtra("qrLng", geoPoint.get(1));
                        System.out.println(geoPoint.get(0)+"/"+geoPoint.get(1));
                        startActivity(intent);
                    }
                    @Override
                    public void failure(Exception e) {
                    }
                });


            }
        });
    }
}
