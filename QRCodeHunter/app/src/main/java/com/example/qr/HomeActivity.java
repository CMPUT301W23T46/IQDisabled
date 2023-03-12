package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {

    ImageButton searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");
//        System.out.println(username);
//        System.out.println(email);
//        System.out.println(phone);
        ImageButton contactBtn = findViewById(R.id.contact_btn);
        ImageButton addBtn = findViewById(R.id.add_btn);
        ImageButton mapBtn = findViewById(R.id.map_btn);
        ImageButton profileBtn = findViewById(R.id.profile_btn);
        ImageButton highestScoreBtn = findViewById(R.id.imageButton14);
        Button viewQRCodesBtn = findViewById(R.id.view_qr_codes_main_btn);
        ImageButton searchBtn = findViewById(R.id.home_search_btn);


        DataBaseHelper dbhelper = new DataBaseHelper();
        //pushPlayer调用
//        dbhelper.pushPlayer("Felix","wtkuan@163.com","8375739912",new String[]{"qrd","siejdf"});

        //checkUserNameExist调用
//        try {
//            if (dbhelper.checkUserNameExist("Felix", new IQuery() {
//                @Override
//                public void onSuccess(boolean result) {
//                    Toast.makeText(HomeActivity.this, "true", Toast.LENGTH_SHORT).show();
//                }
//            })) {
//                // Do Nothing
//            }
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //pushQRCode use case
//        dbhelper.pushQRCode("w2soievwn",30,"low code","--11234",59.2,-133,new String[]{"hello"});
//        dbhelper.player_add_qrcode(new Player("Felix","wtkuan@163.com","8375739912"),"wehjfwrg");
//        QRCode qrCode = null;
//        try {
//            qrCode = new QRCode("w2oievn",new String[] {"hello","nice work"});
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
////        dbhelper.pushQRCode(qrCode,-34.34,-133.2);
//        dbhelper.qrcode_add_comment(qrCode,"well done");


        dbhelper.getComments("61fa93bca0736dd2e135c80ed083a77eedf61c0b2b557e418686bf5d246dc4dd", new OnGetCommentByHashListener() {
            @Override
            public void onSuccess(String[] comments) {
                for (String comment : comments) {
                    System.out.println(comment);
                }
            }
        });
        Player ply = new Player("Alice","Alice@ualberta.ca","987657890");
        QRCode qrCode = null;
        try {
            qrCode = new QRCode("sdfgn",new String[] {"3o","work"});
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
//        dbhelper.pushPlayer(ply,new QRCode[]{qrCode});

//        dbhelper.delete_Player_QRCode(ply,qrCode);
        Player player1;
        try {
            dbhelper.getPlayer("Felix", new OnGetPlayerListener() {
                @Override
                public void onSuccess(Player player) {
                    Toast.makeText(HomeActivity.this, player.getPhone_number(), Toast.LENGTH_SHORT).show();
                }
            });
//            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



//        Toast.makeText(this, player1.getEmail()+player1.getPhone_number(), Toast.LENGTH_SHORT).show();
                contactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                        startActivity(intent);
                    }
                });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddCodeActivity.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        viewQRCodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyQRCodeActivity.class);
                startActivity(intent);
            }
        });
        highestScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HighestScoreOfAllPlayerActivity.class);
                startActivity(intent);
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
