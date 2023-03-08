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

import java.security.NoSuchAlgorithmException;

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

        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCodeActivity.this.finish();
            }
        });
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
            QRCodeStats stats = new QRCodeStats(result.getContents());
            Intent intent = new Intent(AddCodeActivity.this,ShowQRCodeActivity.class);
            try {
                String hashContent = stats.hashString();
//                System.out.println(hashContent);
                String firstSix = stats.extractFirstSix(hashContent);
//                System.out.println(firstSix);
                String name = stats.naming(firstSix);
//                System.out.println(name);
                Integer score = stats.scoring(hashContent);
//                System.out.println(score);
                String visualRep = stats.visualRep(firstSix);
//                System.out.println(visualRep);
                intent.putExtra("hashContent",hashContent);
                intent.putExtra("firstSix",firstSix);
                intent.putExtra("name",name);
                intent.putExtra("score",score);
                intent.putExtra("visualRep",visualRep);
                startActivity(intent);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

    });


}
