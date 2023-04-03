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
    ArrayAdapter<String> adapter;
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
        Button statisticsButton = findViewById(R.id.bottom_button_1);
        Button RankingButton = findViewById(R.id.bottom_button_2);
        Button RemoveButton = findViewById(R.id.bottom_button_3);
        ImageButton backButton = findViewById(R.id.imageButton13);
        ListView listView = findViewById(R.id.qrcodes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);


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
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> documentNames = new ArrayList<>();
        CollectionReference myqrcode = db.collection("Players").document(username).collection("QRCode");

        DataBaseHelper dbhelper = new DataBaseHelper();
        ListView mycodes = findViewById(R.id.qrcodes);

        dbhelper.getQRCodeByName_hash(username, new OnGetHashByUsernameListener() {
            @Override
            public void onSuccess(String[] hashcodes) throws InterruptedException {
                ArrayList<QRCode> qrCodes = new ArrayList<>();
                for (String hash: hashcodes) {
                    QRCode qr = new QRCode(hash);
                    qrCodes.add(qr);
                }
                QRCodeArrayAdapter adapter = new QRCodeArrayAdapter(MyQRCodeActivity.this,qrCodes);
                mycodes.setAdapter(adapter);
            }
        });




//        myqrcode.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String documentName = document.getId();
//                        documentNames.add(documentName);
//                    }
//
//                    db.collection("QRCode")
//                            .get()
//                            .addOnCompleteListener(task1 -> {
//                                if (task1.isSuccessful()) {
//                                    QuerySnapshot querySnapshot = task1.getResult();
//                                    if (querySnapshot != null) {
//                                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
//                                            String docName = document.getId();
//                                            if (documentNames.contains(docName)) {
//                                                String fieldValue = document.getString("qrcodeName");
//                                                if (fieldValue != null) {
//                                                    data.add(fieldValue);
//                                                }
//                                            }
//                                        }
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                }
//                            });
//                            }
//                        }
//                    });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String select = documentNames.get(position);
//                data.remove(position);
//                RemoveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view){
//                        adapter.notifyDataSetChanged();
//                        db.collection("Players").document(username).collection("QRCode").document(select)
//                                .delete();
//
//                    }
//                });
//                }
//        });
    }
}