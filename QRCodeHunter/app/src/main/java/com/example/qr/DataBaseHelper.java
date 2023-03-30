package com.example.qr;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
/**
 * The DataBaseHelper class aims to contain all the actions do to the database, including adding, deleting, searching
 * And
 */
public class DataBaseHelper {
    FirebaseFirestore db;

    /**
     * Adds a player to the Firestore database with their email, phone number,
     * and associated QR codes.
     *
     * @param player the player to add to the database
     * @param qrCodes an array of QRCode objects associated with the player
     */
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

    /**
     * Checks whether a given username exists in the Firestore database.
     *
     * @param username the username to check for existence
     * @param onCheckQRCodeExistListener an interface for listening to the result of the check
     * @throws ExecutionException if the task executing the check fails
     * @throws InterruptedException if the thread executing the check is interrupted
     */
    public void checkUserNameExist(String username, OnCheckQRCodeExistListener onCheckQRCodeExistListener) throws ExecutionException, InterruptedException {
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
    }

    /**
     * Checks whether a QR code with the given hash code exists in the database.
     *
     * @param hashcode the hash code of the QR code to check for existence
     * @param onCheckQRCodeExistListener the listener to handle the result of the check
     * @return a boolean value indicating whether the QR code exists in the database (may be inaccurate due to asynchronous behavior)
     * @throws ExecutionException if the task fails due to an error
     * @throws InterruptedException if the task is interrupted
     */
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

    /**

     * Adds a new QRCode to the Firestore database and stores its associated information, including score, location and comments.
     * @param qrCode The QRCode object to be stored.
     * @param latitude The latitude coordinate of the QRCode's location.
     * @param longitude The longitude coordinate of the QRCode's location.
     */
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

    /**

     * Adds a QR code to a player's collection of QR codes in the database.

     * @param player The player to add the QR code to.
     * @param hashcode The hash code of the QR code to add.
     */
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

    /**
     * Adds a comment to a QRCode object in the database.
     *
     * @param qrCode the QRCode object to which the comment will be added
     * @param comment the comment to add to the QRCode object
     */
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


    /**

     * Deletes a player's data from the database.
     * @param player the Player object representing the player whose data will be deleted
     */
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

    /**

     * This method deletes the specified QR code from the player's collection of QR codes in the Firestore database.
     * @param player the player object whose collection of QR codes will be updated
     * @param qrCode the QR code object to be deleted from the player's collection
     */
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

    /**
     * Retrieves a player object from the database using their player name.
     *
     * @param playerName the name of the player to retrieve
     * @param iquery an instance of OnGetPlayerListener to handle the callback function when the player is successfully retrieved
     * @throws Exception if there is an error retrieving the player from the database
     */
    public void getPlayer(String playerName, OnGetPlayerListener iquery) throws Exception {
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
//                        player.setPhone_number(result[0].get("phone").toString());
//                        player.setEmail(result[0].get("email").toString());
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
    }

    /**
     * Returns the number of QR codes associated with a given player.
     *
     * @param playerName the name of the player to retrieve the QR code count for
     * @param iquery     an OnQRCodeLengthComplete listener to handle the result
     * @return the number of QR codes associated with the player
     */
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

    /**
     * Retrieves the geolocations of the QR code with the given hashcode.
     *
     * @param hashcode the hashcode of the QR code to retrieve the geolocations of
     * @param iquery   an instance of OnGetQRCodeGeolocations that will be called upon completion of the retrieval
     */
    public void getQRCodeGeolocations(String hashcode, OnGetQRCodeGeolocations iquery) {
        final HashMap<String,Double>[] geo = new HashMap[1];
        geo[0] = new HashMap<String,Double>();
        final Map<String, Object>[] result = new Map[1];
        result[0] = new HashMap<String,Object>();
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("QRCode");
        collectionRef.document(hashcode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result[0] = document.getData();
                        String latitude = result[0].get("latitude").toString();
                        String longitude = result[0].get("longitude").toString();
                        System.out.println(latitude.toString()+longitude.toString());
                        double lat = Double.parseDouble(latitude);
                        double lon = Double.parseDouble(longitude);
                        geo[0].put("longitude",lon);
                        geo[0].put("latitude",lat);
                        iquery.onSuccess(geo[0]);
                    }
                }
            }
        });
    }


    /**

     * Retrieves the hash codes of all QR codes belonging to a specific player.
     * @param username the name of the player to retrieve the QR codes for
     * @param iquery an instance of OnGetHashByUsernameListener to handle the results of the query
     */
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
                        try {
                            iquery.onSuccess(documentNames);
                        } catch (InterruptedException e) {
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

    /**

     * Retrieves the comments associated with a QR code identified by its hash code.

     * @param hashcode The hash code of the QR code to retrieve comments for.

     * @param iquery The callback interface to return the retrieved comments.
     */
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

    /**

     * Retrieves a QR code by its hashcode and its associated comments.
     * @param hashcode the hashcode of the QR code to retrieve.
     * @param iquery the callback to handle the retrieved QR code.
     * @throws NoSuchAlgorithmException if there is an error processing the comments.
     */
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
    /**

     * Retrieves an array of QR codes associated with a given username.

     * @param username the username for which to retrieve the QR codes.

     * @param iquery an instance of OnGetQRCodeNamesListener to be called upon successful completion of the retrieval.
     */
    public void getQRCodesByName(String username, OnGetQRCodeNamesListener iquery) {
        DataBaseHelper helper = new DataBaseHelper();
        helper.getQRCodeByName_hash(username, new OnGetHashByUsernameListener() {
            @Override
            public void onSuccess(String[] hashcodes) {
                QRCode[] qrCodes = new QRCode[hashcodes.length];
                int index = 0;
                for (String hashcode: hashcodes) {
                    helper.getQRCodeByName(hashcode, new OnGetQRCodeListener() {
                        @Override
                        public void onSuccess(QRCode qrCode) {
                            qrCodes[index] = qrCode;
                        }
                    });
                }
                iquery.onSuccess(qrCodes);
            }
        });
    }

    /**

     * This method retrieves all QR codes stored in the Firestore database and passes them to the listener
     * @param iquery An instance of OnGetAllQRCodeListener interface, which will receive the list of QR codes.
     */
    public void getAllQRCode(OnGetAllQRCodeListener iquery){
        db = FirebaseFirestore.getInstance();
        CollectionReference collecRef = db.collection("QRCode");

        collecRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String[] result = new String[queryDocumentSnapshots.size()];
                int i = 0;
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    result[i] = documentSnapshot.getId();
                    i++;
                }
                iquery.onSuccess(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Falied");
            }
        });
    }

    public void getQRcodeScore(String hashcode, OnGetQRCodeScoreListener iquery) {
        db = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = db.collection("QRCode");
        final Map<String, Object>[] result = new Map[1];
        result[0] = new HashMap<String,Object>();
        collectionReference.document(hashcode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result[0] = document.getData();
                        String score_str = result[0].get("score").toString();
                        Integer score_int = Integer.parseInt(score_str);
                        iquery.onSuccess(score_int);
                    }
            }
        }});
    }

    /**
     * this method retrieve all player objects passes to listener
     * @param iquery An instance of OnGetAllPlayerListener interface, which store the list of all player objects.
     */

    public void getAllPlayer(OnGetAllPlayerListener iquery) {

        db = FirebaseFirestore.getInstance();
        CollectionReference collecRef = db.collection("Players");
        collecRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<Player> playerList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // Get the data of each document and convert to Player object
                    Player player = new Player(
                            documentSnapshot.getString("playName"),
                            documentSnapshot.getString("email"),
                            documentSnapshot.getString("phone_number")
                    );
                    playerList.add(player);
                }

                // Convert the List to an array of Players and pass to listener
                Player[] result = playerList.toArray(new Player[playerList.size()]);
                iquery.success(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to retrieve Players", e);
                iquery.failure(e);
            }
        });
    }

//    public void getHighestScore() {
//        db = FirebaseFirestore.getInstance();
////        CollectionReference collectionReference = db.collection();
//    }

}
