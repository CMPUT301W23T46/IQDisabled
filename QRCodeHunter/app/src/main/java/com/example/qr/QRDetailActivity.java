package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
/**
 *Represents an activity that displays details of a QR code.
 *The activity displays the name of the QR code, its reputation, score, and comments.
 *It also allows users to add comments and view the QR code location on a map.
 */
public class QRDetailActivity extends AppCompatActivity {
    ImageButton backBtn;
    Button mapBtn;
    TextView qrName;
    TextView qrRep;
    TextView qrScore;
    TextView qrComment;

    EditText editComment;

    Button submitBtn;

    /**
     * Called when the activity is starting. Retrieves the details of the QR code from the database and
     * displays them on the screen.
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    @Override
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
        editComment = findViewById(R.id.add_comment);
        submitBtn = findViewById(R.id.submit_btn);


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
                    /**
                     *Called when the "View on Map" button is clicked. Retrieves the location data for the QR code from the
                     *database and launches the ViewOnMapActivity to display the QR code's location on a map.
                     *
                     *@param geoPoint the list of latitude and longitude coordinates for the QR code's location
                     */
                    @Override
                    public void success(List<String> geoPoint) {
                        Intent intent = new Intent(QRDetailActivity.this, ViewOnMapActivity.class);
                        Double la = Double.parseDouble(geoPoint.get(0));
                        Double lo = Double.parseDouble(geoPoint.get(1));
                        if (la >= -90 && la <= 90 && lo >= -180 && lo <= 180) {
                            intent.putExtra("qrName", qr_name);
                            intent.putExtra("qrLa", geoPoint.get(0));
                            intent.putExtra("qrLo", geoPoint.get(1));
                            System.out.println(geoPoint.get(0)+"/"+geoPoint.get(1));
                            startActivity(intent);
                        } else{
                            Toast.makeText(QRDetailActivity.this, "No location data", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void failure(Exception e) {
                    }
                });
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editComment.getText().toString();
                if (comment.length() != 0) {
                    DataBaseHelper dbHelper = new DataBaseHelper();
                    dbHelper.add_comment(qr_name, comment, new OnAddCommentListener() {
                        @Override
                        public void success() {
                            Toast.makeText(QRDetailActivity.this, "Comment added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(Exception e) {
                            Toast.makeText(QRDetailActivity.this, "Comment failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(QRDetailActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
                }

                dbhelper.getQRCommentByName(qr_name, new OnGetQRCommentListener() {
                    /**
                     *Called when the comments for the QR code are successfully retrieved from the database. Formats the comments
                     *as a single string and displays them on the screen.
                     *
                     *@param qrComs the list of comments for the QR code
                     */
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
    });
    }
}
