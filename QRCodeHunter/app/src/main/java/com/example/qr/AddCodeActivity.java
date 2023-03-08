package com.example.qr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import javax.xml.transform.Result;

public class AddCodeActivity extends AppCompatActivity {
    String result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnCam = (Button) findViewById(R.id.btn_scan);
        btnCam.setOnClickListener(v -> {
            scan();
        });
        TextView qrCodeInfo= (TextView)findViewById(R.id.QRCode_info_text);
        qrCodeInfo.setText(result);

    }

    private void scan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan your QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barlauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barlauncher = registerForActivityResult(new ScanContract(), result-> {
        if (result.getContents() != null){
            this.result = result.getContents();
            AlertDialog.Builder builder = new AlertDialog.Builder(AddCodeActivity.this);
            builder.setTitle("Scan");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }

    });


}
