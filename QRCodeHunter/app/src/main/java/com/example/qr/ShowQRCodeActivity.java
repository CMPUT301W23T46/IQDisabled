package com.example.qr;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION.SDK_INT;
import android.Manifest;

import androidx.camera.view.PreviewView;
import androidx.camera.core.ImageCapture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
/**
 * This class represents an activity that displays information about a scanned QR code.
 * It allows the user to take a picture, submit their location and comment, and store information about the QR code in a Firebase Cloud Firestore database.
 */
public class ShowQRCodeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private PreviewView previewView;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    FirebaseFirestore db;
    private String hashCode;

    private String image;

    private int called_times;
    /**
     * This method is called when the activity is created.
     * It sets up the Google API client for location services, creates a location request object,
     * retrieves information about the scanned QR code from the previous activity,
     * initializes UI elements, and sets up listeners for button clicks.
     *
     * @param savedInstanceState the saved instance state of the activity, or null if there is none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scan);
        LinearLayout capture = findViewById(R.id.capture_preview);
        capture.setVisibility(View.GONE);
        called_times = 0;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Create LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)  // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);

        Intent intent = getIntent();
        String hashContent = intent.getStringExtra("hashContent");
        this.hashCode = hashContent;
        String firstSix = intent.getStringExtra("firstSix");
        String name = intent.getStringExtra("name");
        Integer score = intent.getIntExtra("score",0);
        String visualRep = intent.getStringExtra("visualRep");

        TextView visual_rep = findViewById(R.id.visual_rep);
        TextView tw_score = findViewById(R.id.qrcode_score);
        TextView qrcode_name = findViewById(R.id.qrcode_name);
        EditText edt_comment = findViewById(R.id.edit_comment);
        tw_score.setText(score.toString());
        System.out.println(visualRep);

        DataBaseHelper dbhelper = new DataBaseHelper();
        try {
            dbhelper.checkQRCodeExist(hashCode, new OnCheckQRCodeExistListener() {
                @Override
                public void onSuccess(boolean result) {
                    TextView scanned_before = findViewById(R.id.scan_before);
                    String yes_string = "Yes";
                    String no_string = "No";
                    scanned_before.setText(result?yes_string:no_string);
                    if (!result) {
                        Toast.makeText(ShowQRCodeActivity.this, "Great job! This one hasn't been scanned!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        dbhelper.getAllQRCode(new OnGetAllQRCodeListener() {
            @Override
            public void onSuccess(String[] hashcodes) {
                List<Integer> scoresList = new ArrayList<>();
                for (String hashcode : hashcodes) {
                    dbhelper.getQRcodeScore(hashcode, new OnGetQRCodeScoreListener() {
                        @Override
                        public void onSuccess(Integer score_a) {
                            scoresList.add(score_a);
                            if (scoresList.size() == hashcodes.length) {
                                int[] scoresArray = scoresList.stream().mapToInt(Integer::intValue).toArray();
                                Arrays.sort(scoresArray);
                                Integer[] temp = Arrays.stream(scoresArray).boxed().toArray(Integer[]::new);
                                Arrays.sort(temp, Collections.reverseOrder());
                                scoresArray = Arrays.stream(temp).mapToInt(Integer::valueOf).toArray();
                                for (int c: scoresArray) {
                                    System.out.print(c+",,");
                                }
                                int j;
                                for (j = scoresArray.length-1; j >0; j--) {
                                    if (score < scoresArray[scoresArray.length-1]) {
                                        j = scoresArray.length;
                                        break;
                                    }
                                    if (scoresArray[j-1]>score && scoresArray[j]<=score) {
                                        break;
                                    }
                                }
                                j+=1;
                                TextView score_rank = findViewById(R.id.est_rank);
                                score_rank.setText(String.valueOf(j));
                            }
                        }
                    });
                }
            }
        });


        visual_rep.setText(visualRep);
        qrcode_name.setText(name);

        Button back_btn = findViewById(R.id.back_btn);
        Button take_pic_btn = findViewById(R.id.take_pic_btn);
        Button take_pic_cancel_btn = findViewById(R.id.btn_cancel);
        CheckBox geo_location = findViewById(R.id.checkbox_geo);
        Button submit_btn = findViewById(R.id.submit_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the back button is clicked.
             * It finishes the current activity.
             *
             * @param v the view that was clicked
             */
            @Override
            public void onClick(View v) {
                ShowQRCodeActivity.this.finish();
            }
        });


        take_pic_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture.setVisibility(View.GONE);
            }
        });

        take_pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture.setVisibility(View.VISIBLE);
                take_Pic();
//                capture.setVisibility(View.GONE);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (geo_location.isChecked()) {
                    mGoogleApiClient.connect();
                    submit_btn.setText("Please Wait");
                    // Request location updates

                }
                else {
                    EditText edx = findViewById(R.id.edit_comment);
                    System.out.println(edx.getText().toString());
                    db = FirebaseFirestore.getInstance();
                    CollectionReference collectRef = db.collection("QRCode");
                    HashMap<String, String> value = new HashMap<>();
                    QRCode qr = new QRCode(hashContent);
                    value.put("latitude",String.valueOf("250"));
                    value.put("longitude",String.valueOf("250"));
                    value.put("qrcodeName",qr.getQrcodeName());
                    value.put("score",String.valueOf(qr.getScore()));
                    value.put("visual_rep",qr.getVisual_rep());
                    String temp = ShowQRCodeActivity.this.image;
                    value.put("image",temp);
                    collectRef.document(hashContent).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"Success");
                            DataBaseHelper dbhelper = new DataBaseHelper();
                            dbhelper.qrcode_add_comment(qr,edt_comment.getText().toString());
                            Intent intent1 = new Intent(ShowQRCodeActivity.this,HomeActivity.class);
                            startActivity(intent1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"Failed");
                        }
                    });
                }
                DataBaseHelper dbhelper = new DataBaseHelper();
                SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
                String username = sharedPref.getString("username","N/A");
                String hash_add = ShowQRCodeActivity.this.hashCode;
                db = FirebaseFirestore.getInstance();
                CollectionReference collectRef = db.collection("Players").document(username).collection("QRCode");

                Map<String,Object> value = new HashMap<>();
                value.put(hash_add,hash_add);
                collectRef.document(hash_add).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Failed");
                    }
                });
            }
        });

    }
    /**

     Checks if the app has necessary permission to read and write to external storage.
     If the SDK version is above R, checks if the app has necessary permission to manage all files.
     @return true if the app has the necessary permissions, false otherwise
     */
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(ShowQRCodeActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(ShowQRCodeActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }
    /**

     Requests necessary permissions to read and write to external storage.
     If the SDK version is above R, opens the app's manage all files access settings page.
     */
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(ShowQRCodeActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    /**

     Called when GoogleApiClient is connected.
     Checks if the app has necessary permissions to access the device location.
     If not, requests the permissions. If yes, requests location updates.
     @param bundle The bundle passed to onCreate
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Request location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        if (ActivityCompat.checkSelfPermission(ShowQRCodeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ShowQRCodeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ShowQRCodeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, ShowQRCodeActivity.this);
        }
//                        Toast.makeText(ShowQRCodeActivity.this, "Latitude: " + ShowQRCodeActivity.this.latitude + ", Longitude: " + ShowQRCodeActivity.this.longitude, Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();
        CollectionReference collectRef = db.collection("QRCode");
        HashMap<String, Object> value = new HashMap<>();

        QRCode qr = new QRCode(hashCode);
        value.put("latitude",String.valueOf(ShowQRCodeActivity.this.latitude));
        value.put("longitude",String.valueOf(ShowQRCodeActivity.this.longitude));
        value.put("qrcodeName",qr.getQrcodeName());
        value.put("score",String.valueOf(qr.getScore()));
        value.put("visual_rep",qr.getVisual_rep());
        String temp = this.image;
        value.put("image",temp);
        collectRef.document(hashCode).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"Success");
                Button submit_btn = findViewById(R.id.submit_btn);
                submit_btn.setText("Success!");
                DataBaseHelper dbhelper = new DataBaseHelper();
                EditText edt_comment = findViewById(R.id.edit_comment);
                dbhelper.qrcode_add_comment(qr,edt_comment.getText().toString());
                Intent intent1 = new Intent(ShowQRCodeActivity.this,HomeActivity.class);
                startActivity(intent1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Failed");
                Toast.makeText(ShowQRCodeActivity.this, "Unable to upload. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**

     Called when GoogleApiClient connection is suspended. Does nothing.
     @param i The reason for the disconnection. Defined in GoogleApiClient.
     */

    @Override
    public void onConnectionSuspended(int i) {
        // Do nothing
    }
    /**
     Called when GoogleApiClient connection fails. Does nothing.
     @param connectionResult The result of the failed connection attempt. Defined in GoogleApiClient.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Do nothing
    }
    /**

     This method is called when the device's location changes. It updates the latitude and longitude
     of the user's location and increments the number of times the method has been called.
     @param location The new location of the device.
     */
    @Override
    public void onLocationChanged(Location location) {

            // Handle location update
        if (called_times <= 2) {
            called_times+=1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ShowQRCodeActivity.this.latitude = location.getLatitude();
            ShowQRCodeActivity.this.longitude = location.getLongitude();
            System.out.println(this.longitude+this.latitude);
            EditText edx = findViewById(R.id.edit_comment);

            Map<String,Object> values = new HashMap<>();
            values.put("longitude",String.valueOf(longitude));
            values.put("latitude",String.valueOf(latitude));
            db=FirebaseFirestore.getInstance();
            DocumentReference docuRef = db.collection("QRCode").document(this.hashCode);

            docuRef.update(values).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,"Failed");
                }
            });
        }


    }

    /**
     This method is called to take a photo using the device's camera. It first checks if the necessary
     permissions have been granted and requests them if they have not. It then sets up the camera preview
     and image capture use case. When the user clicks the capture button, it saves the captured image
     to the device and uploads it to the Firebase database.
     */

    public void take_Pic() {
        if (!checkPermission()) {
            requestPermission();
        }
        Executor cameraExecutor = ContextCompat.getMainExecutor(this);
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Preview preview = new Preview.Builder()
                        .build();

                previewView = findViewById(R.id.preview_view);

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Set up the image capture use case
                ImageCapture imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .build();
                // Bind the camera provider to the lifecycle owner and start preview
                cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);

                Button button = findViewById(R.id.btn_capture);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File outputDirectory = Environment.getExternalStorageDirectory();
                        File file = new File(outputDirectory, "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg");

                        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

                        imageCapture.takePicture(outputOptions,
                                cameraExecutor,
                                new ImageCapture.OnImageSavedCallback() {
                                    @Override
                                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                        // The image has been captured and saved successfully
                                        Log.d(TAG, "Image saved successfully: " + file.getAbsolutePath());
                                        compressAndUpload(new File(file.getAbsolutePath()));
                                    }

                                    @Override
                                    public void onError(@NonNull ImageCaptureException exception) {
                                        // There was an error capturing or saving the image
                                        Log.e(TAG, "Error capturing image: " + exception.getMessage());
                                    }
                                });
                        LinearLayout capture = findViewById(R.id.capture_preview);
                        capture.setVisibility(View.GONE);
                    }
                });
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                // Handle any exceptions here
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void compressAndUpload(File file) {
        // Read the image file as a Bitmap
//        System.out.println(file == null);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
//        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),0,baos.size());
        byte[] imageData = baos.toByteArray();

        this.image = imageData.toString();
//        System.out.println(imageData[1]);
    }
}