package com.example.qr;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION.SDK_INT;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import androidx.camera.camera2.interop.Camera2Interop;
import androidx.camera.view.PreviewView;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowQRCodeActivity extends AppCompatActivity {
    private PreviewView previewView;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scan);
        LinearLayout capture = findViewById(R.id.capture_preview);
        capture.setVisibility(View.GONE);

        Intent intent = getIntent();
        String hashContent = intent.getStringExtra("hashContent");
        String firstSix = intent.getStringExtra("firstSix");
        String name = intent.getStringExtra("name");
        Integer score = intent.getIntExtra("score",0);
        String visualRep = intent.getStringExtra("visualRep");

        TextView visual_rep = findViewById(R.id.visual_rep);
        TextView qrcode_name = findViewById(R.id.qrcode_name);
        EditText edt_comment = findViewById(R.id.edit_comment);
        System.out.println(visualRep);

        visual_rep.setText(visualRep);
        qrcode_name.setText(name);

        Button back_btn = findViewById(R.id.back_btn);
        Button submit_btn = findViewById(R.id.submit_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowQRCodeActivity.this.finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture.setVisibility(View.VISIBLE);
                take_Pic();
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
                                    }

                                    @Override
                                    public void onError(@NonNull ImageCaptureException exception) {
                                        // There was an error capturing or saving the image
                                        Log.e(TAG, "Error capturing image: " + exception.getMessage());
                                    }
                                });
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
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        // Compress the bitmap to JPEG format with 80% quality
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        // Convert the compressed image to a byte array
        byte[] imageData = baos.toByteArray();

        // Upload the compressed image to Firebase Storage
//        uploadToFirebaseStorage(imageData);
    }

    private void uploadToFirebaseStorage(byte[] imageData) {
        // Create a Firebase Storage reference to the image
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/image.jpg");

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Image uploaded successfully
        }).addOnFailureListener(e -> {
            // Error occurred while uploading image
        });
    }



}