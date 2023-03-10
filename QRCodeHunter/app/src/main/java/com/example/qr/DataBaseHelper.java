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

    public void pushPlayer(Player player, QRCode[] qrCodes) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Players");
        HashMap<String,String> value = new HashMap<>();
        value.put("email", player.getEmail());
        value.put("phone", player.getPhone_number());
        collectionReference.document(player.getPlayName())
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
        for (QRCode v: qrCodes) {
            HashMap<String,String> qrvalue = new HashMap<>();
            qrvalue.put("hash",v.getHashCode());
            CollectionReference collection_player = db.collection("Players").document(player.getPlayName()).collection("QRCode");
            collection_player.document(v.getHashCode())
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

    public boolean checkQRCodeExist(String hashcode, IQuery iQuery) throws ExecutionException, InterruptedException {
        db = FirebaseFirestore.getInstance();
        final boolean[] result = new boolean[1];
        CollectionReference userNameRefCollection = db.collection("QRCode");
        DocumentReference userNameRefDocument =userNameRefCollection.document(hashcode);
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

    public void pushQRCode(QRCode qrCode, double latitude, double longitude) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("QRCode");
        String score_str = String.valueOf(qrCode.getScore());
        HashMap<String,String> value = new HashMap<>();
        value.put("qrcodeName", qrCode.getQrcodeName());
        value.put("visual_rep", qrCode.getVisual_rep());
        value.put("score",score_str);
        value.put("latitude",String.valueOf(latitude));
        value.put("longitude",String.valueOf(longitude));
        collectionReference.document(qrCode.getHashCode())
                .set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        for (String v: qrCode.getComments()) {
            HashMap<String,String> comment = new HashMap<>();
            comment.put("comment",v);
            CollectionReference collection_qr = db.collection("QRCode").document(qrCode.getHashCode()).collection("Comments");
            collection_qr.document(v).set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.w(TAG, "Working");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    public void player_add_qrcode(Player player, String hashcode) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Players").document(player.getPlayName()).collection("QRCode");
        HashMap<String,String> value = new HashMap<>();
        value.put("hash",hashcode);
        collectionReference.document(hashcode).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void qrcode_add_comment(QRCode qrCode, String comment) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("QRCode").document(qrCode.getHashCode()).collection("Comments");
        HashMap<String,String> commentval = new HashMap<>();
        commentval.put("comment",comment);
        collectionReference.document(comment).set(commentval).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void delete_Players(Player player) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Players");
        collectionReference.document(player.getPlayName()).delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Working", "Data not deleted!");
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Working","Data deleted successfully!");
            }
        });
    }

    public void delete_Player_QRCode(Player player, QRCode qrCode) {
        db  = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Players").document(player.getPlayName())
                .collection("QRCode");
        collectionReference.document(qrCode.getHashCode()).delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Working", "Data not deleted!");
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Working","Data deleted successfully!");
                    }
                });
    }
}
