package com.example.qr;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The MyQRCodeActivity class extends the AppCompatActivity class and is responsible for displaying the qrcodes I have
 * added to my account.
 */
public class MyQRCodeActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    List<String> data = new ArrayList<>();

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
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");
        Button statisticsButton = findViewById(R.id.bottom_button_1);
        Button RankingButton = findViewById(R.id.bottom_button_2);
        Button RemoveButton = findViewById(R.id.bottom_button_3);
        ImageButton backButton = findViewById(R.id.imageButton13);
        ListView listView = findViewById(R.id.qrcodes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        ListView qrCodeListView = findViewById(R.id.qrcodes);
        DataBaseHelper dbhelper = new DataBaseHelper();
        dbhelper.getQRCodeByName_hash(username, new OnGetHashByUsernameListener() {
            @Override
            public void onSuccess(String[] hashcodes) {
                List<QRCode> qrCodeList = new ArrayList<>();
                for (String hash: hashcodes) {
                    dbhelper.getQRCodeByName(hash, new OnGetQRCodeListener() {
                        @Override
                        public void onSuccess(QRCode qrCode) {
                            qrCodeList.add(qrCode);
                            QrCodesArrayAdapter adapter = new QrCodesArrayAdapter(MyQRCodeActivity.this, qrCodeList);
                            listView.setAdapter(adapter);
                            if (qrCodeList.size() == hashcodes.length) {
                                System.out.println(qrCodeList.get(0).getQrcodeName());

                            }
                        }
                    });
                }
            }
        });
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
        List<String> documentNames = new ArrayList<>();
        CollectionReference myqrcode = db.collection("Players").document(username).collection("QRCode");
        myqrcode.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentName = document.getId();
                        documentNames.add(documentName);
                    }
                }
        }
        });
        db.collection("QRCode")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                String docName = document.getId();
                                if (documentNames.contains(docName)) {
                                    String fieldValue = document.getString("qrcodeName");
                                    if (fieldValue != null) {
                                        data.add(fieldValue);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}