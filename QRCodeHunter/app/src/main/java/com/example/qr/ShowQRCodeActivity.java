package com.example.qr;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION.SDK_INT;
import android.Manifest;

import androidx.camera.view.PreviewView;
import androidx.camera.core.ImageCapture;

import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class ShowQRCodeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private PreviewView previewView;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    FirebaseFirestore db;
    private String hashCode;

    private int called_times;

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

        visual_rep.setText(visualRep);
        qrcode_name.setText(name);

        Button back_btn = findViewById(R.id.back_btn);
        Button take_pic_btn = findViewById(R.id.take_pic_btn);
        Button take_pic_cancel_btn = findViewById(R.id.btn_cancel);
        CheckBox geo_location = findViewById(R.id.checkbox_geo);
        Button submit_btn = findViewById(R.id.submit_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
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
                    if (mGoogleApiClient.isConnected()) {
                        // Request location updates
                        if (ActivityCompat.checkSelfPermission(ShowQRCodeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(ShowQRCodeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ShowQRCodeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        } else {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, ShowQRCodeActivity.this);
                        }
                        Toast.makeText(ShowQRCodeActivity.this, "Latitude: " + ShowQRCodeActivity.this.latitude + ", Longitude: " + ShowQRCodeActivity.this.longitude, Toast.LENGTH_SHORT).show();
                        db = FirebaseFirestore.getInstance();
                        CollectionReference collectRef = db.collection("QRCode");
                        HashMap<String, String> value = new HashMap<>();
                        QRCode qr = new QRCode(hashContent);
                        value.put("latitude",String.valueOf(ShowQRCodeActivity.this.latitude));
                        value.put("longitude",String.valueOf(ShowQRCodeActivity.this.longitude));
                        value.put("qrcodeName",qr.getQrcodeName());
                        value.put("score",String.valueOf(qr.getScore()));
                        value.put("visual_rep",qr.getVisual_rep());
                        collectRef.document(hashContent).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"Success");
                                Intent intent1 = new Intent(ShowQRCodeActivity.this,HomeActivity.class);
                                startActivity(intent1);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"Failed");
                            }
                        });
                    } else {
                        mGoogleApiClient.connect();
                    }
                }
                else {
                    EditText edx = findViewById(R.id.edit_comment);
                    System.out.println(edx.getText().toString());
                    db = FirebaseFirestore.getInstance();
                    CollectionReference collectRef = db.collection("QRCode");
                    HashMap<String, String> value = new HashMap<>();
                    QRCode qr = new QRCode(hashContent);
                    value.put("latitude",String.valueOf("0"));
                    value.put("longitude",String.valueOf("0"));
                    value.put("qrcodeName",qr.getQrcodeName());
                    value.put("score",String.valueOf(qr.getScore()));
                    value.put("visual_rep",qr.getVisual_rep());
                    collectRef.document(hashContent).set(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"Success");
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
            }
        });

    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(ShowQRCodeActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(ShowQRCodeActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

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

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Request location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Do nothing
    }

    @Override
    public void onLocationChanged(Location location) {

            // Handle location update
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
        System.out.println(edx.getText().toString());
        //TODO: Submit to database

    }


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

//        System.out.println(imageData[1]);
        upload(imageData);
    }


    private void upload(byte[] imageData) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collecRef = db.collection("QRCode");
        HashMap<String,String> value = new HashMap<>();
        value.put("image",imageData.toString());
        collecRef.document(this.hashCode).set(value).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"success");
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"failed");
            }
        });
//        StorageReference imageRef = storageRef.child("images/image.jpeg");
//
//        // Upload the image to Firebase Storage
//        UploadTask uploadTask = imageRef.putBytes(imageData);
//        uploadTask.addOnSuccessListener(taskSnapshot -> {
//            Log.d(TAG,"Success");
//        }).addOnFailureListener(e -> {
//            Log.d(TAG,"Failed");
//        });
    }
}