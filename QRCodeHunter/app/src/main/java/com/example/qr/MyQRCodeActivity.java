package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * The MyQRCodeActivity class extends the AppCompatActivity class and is responsible for displaying the qrcodes I have
 * added to my account.
 */
public class MyQRCodeActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Retrieves the user's contact information from shared preferences
     * and sets up click listeners for navigation buttons to the home screen, map screen, profile screen,
     * and add code screen.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcodes);

        Button statisticsButton = findViewById(R.id.bottom_button_1);
        Button RankingButton = findViewById(R.id.bottom_button_2);
        Button RemoveButton = findViewById(R.id.bottom_button_3);
        ImageButton backButton = findViewById(R.id.imageButton13);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        RankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQRCodeActivity.this, MyRankingActivity.class);
                startActivity(intent);
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Players").document("Felix").collection("QRCode");

        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> documentNames = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String documentName = documentSnapshot.getId();
                    documentNames.add(documentName);
                }

                // Find the ListView on your app's page
                ListView listView = findViewById(R.id.myqrcode);

                // Create an ArrayAdapter to display the document names in the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyQRCodeActivity.this, android.R.layout.simple_list_item_1, documentNames);

                // Set the ArrayAdapter as the ListView's adapter
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedQRCodeName = (String) parent.getItemAtPosition(position);
                        Intent intent = new Intent(MyQRCodeActivity.this, QRCodeDetailActivity.class);
                        intent.putExtra("QRCodeName", selectedQRCodeName);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}