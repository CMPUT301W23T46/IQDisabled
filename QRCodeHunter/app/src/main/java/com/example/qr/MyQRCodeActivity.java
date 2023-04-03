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
 * The MyQRCodeActivity class is responsible for displaying a list of QR codes for a particular player.
 * It allows the player to navigate to different activities, such as viewing statistics and rankings,
 * and to remove a selected QR code from their collection.
 */
public class MyQRCodeActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> data = new ArrayList<>();

    /**
     * Called when the activity is starting. It initializes the layout and sets up the UI components.
     * It also retrieves the user's QR code collection from Firestore and displays it in the list.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state
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
        myqrcode.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            /**
             * This method is called when a Firestore database operation is completed. It checks if the task was successful
             * and, if so, retrieves the query snapshot from the task result. The query snapshot is used to perform
             * further operations on the Firestore database, such as displaying the user's QR codes in a list or deleting
             * a selected QR code from the database. If the task was not successful, an exception is thrown.
             *
             * @param task a Task object containing the result of the Firestore database operation
             */
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentName = document.getId();
                        documentNames.add(documentName);
                    }

                    db.collection("QRCode")
                            .get()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task1.getResult();
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
                    });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /**
             * This method is called when an item in the ListView is clicked. It retrieves the selected item's position and ID,
             * and then performs an action, such as removing the selected QR code from the database and updating the UI to remove
             * the item from the list.
             *
             * @param parent   the AdapterView where the click happened
             * @param view     the view within the AdapterView that was clicked
             * @param position the position of the view in the adapter
             * @param id       the row ID of the item that was clicked
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = documentNames.get(position);
                data.remove(position);
                RemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        adapter.notifyDataSetChanged();
                        db.collection("Players").document(username).collection("QRCode").document(select)
                                .delete();

                    }
                });
                }
        });
    }
}