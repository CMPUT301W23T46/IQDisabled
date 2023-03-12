package com.example.qr;

import static android.content.ContentValues.TAG;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
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

    public boolean checkUserNameExist(String username, OnCheckQRCodeExistListener onCheckQRCodeExistListener) throws ExecutionException, InterruptedException {
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
                        onCheckQRCodeExistListener.onSuccess(result[0]);
                    } else {
                        result[0] = false;
                        onCheckQRCodeExistListener.onSuccess(result[0]);
                    }
                }
                else {
                    Log.d("DatabaseManager", "Error getting documents: ", task.getException());
                }
            }
        });
        return result[0];
    }

    public boolean checkQRCodeExist(String hashcode, OnCheckQRCodeExistListener onCheckQRCodeExistListener) throws ExecutionException, InterruptedException {
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
                        onCheckQRCodeExistListener.onSuccess(result[0]);
                    } else {
                        result[0] = false;
                        onCheckQRCodeExistListener.onSuccess(result[0]);
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

    public Player getPlayer(String playerName, OnGetPlayerListener iquery) throws Exception {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Players").document(playerName);
        Player player = new Player("","","");

//        ExecutorService threadpool = Executors.newCachedThreadPool();
        final Map<String, Object>[] result = new Map[1];
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result[0] = document.getData();
                        player.setPlayName(playerName);
                        player.setPhone_number(result[0].get("phone").toString());
                        player.setEmail(result[0].get("email").toString());
                        Log.d(TAG, "Completed");
                        iquery.onSuccess(player);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return player;
    }

    public int getQRCodesNum(String playerName, OnQRCodeLengthComplete iquery) {
        final int[] length = {0};
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Players").document(playerName).collection("QRCode");
        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                length[0] = queryDocumentSnapshots.size();
                iquery.onSuccess(length[0]);
            }
        });
        return length[0];
    }

    public void getQRCodeByName_hash(String username, OnGetHashByUsernameListener iquery) {
        db = FirebaseFirestore.getInstance();
        CollectionReference playersRef = db.collection("Players");
        playersRef.document(username).collection("QRCode").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        String[] documentNames = new String[querySnapshot.size()];
                        int i = 0;
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            documentNames[i] = documentSnapshot.getId();
                            i++;
                        }
                        iquery.onSuccess(documentNames);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error getting documents.", e);
                    }
                });
    }

    public void getComments(String hashcode, OnGetCommentByHashListener iquery) {
        db = FirebaseFirestore.getInstance();
        CollectionReference playersRef = db.collection("QRCode");
        playersRef.document(hashcode).collection("Comments").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        String[] documentNames = new String[querySnapshot.size()];
                        int i = 0;
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            documentNames[i] = documentSnapshot.getId();
                            i++;
                        }
                        try {
                            iquery.onSuccess(documentNames);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error getting documents.", e);
                    }
                });
    }

    public void getQRCodeByName(String hashcode, OnGetQRCodeListener iquery) {
        db = FirebaseFirestore.getInstance();
        CollectionReference playersRef = db.collection("QRCode");
        DataBaseHelper helper = new DataBaseHelper();
        helper.getComments(hashcode, new OnGetCommentByHashListener() {
            @Override
            public void onSuccess(String[] comments) throws NoSuchAlgorithmException {
                QRCode result = new QRCode(hashcode,comments);
                iquery.onSuccess(result);
            }
        });

    }


    public void getQRCodeByNames(String username, OnGetQRCodeNamesListener iquery) {
        DataBaseHelper helper = new DataBaseHelper();
        helper.getQRCodeByName_hash(username, new OnGetHashByUsernameListener() {
            @Override
            public void onSuccess(String[] hashcodes) {

            }
        });
    }
}
