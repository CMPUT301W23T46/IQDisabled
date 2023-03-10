package com.example.qr;

import static android.content.ContentValues.TAG;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DataBaseHelper {
    FirebaseFirestore db;

    public void pushPlayer(String playName, String email, String phone_number, String[] QRCodes) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Players");
        HashMap<String,String> value = new HashMap<>();
        value.put("email",email);
        value.put("phone",phone_number);
        collectionReference.document(playName)
                .set(value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.w(TAG, "Working");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        for (String v: QRCodes) {
            HashMap<String,String> qrvalue = new HashMap<>();
            qrvalue.put("hash",v);
            CollectionReference collection_player = db.collection("Players").document(playName).collection("QRCode");
            collection_player.document(v)
                    .set(qrvalue)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.w(TAG, "Working");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    public boolean checkUserNameExist(String username, IQuery iQuery) throws ExecutionException, InterruptedException {
        db = FirebaseFirestore.getInstance();
        final boolean[] result = new boolean[1];
        CollectionReference userNameRefCollection = db.collection("Players");
        DocumentReference userNameRefDocument =userNameRefCollection.document(username);
        userNameRefDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result[0] = true;
                        iQuery.onSuccess(result[0]);
                    } else {
                        result[0] = false;
                        iQuery.onSuccess(result[0]);
                    }
                }
                else {
                    Log.d("DatabaseManager", "Error getting documents: ", task.getException());
                }
            }
        });
        return result[0];
    }
}
