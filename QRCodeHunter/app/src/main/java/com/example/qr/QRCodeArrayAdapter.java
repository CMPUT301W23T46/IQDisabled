package com.example.qr;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class QRCodeArrayAdapter extends ArrayAdapter<QRCode>{
    private final Context context;
    private final ArrayList<QRCode> qrList;

    private int position;

    public QRCodeArrayAdapter(Context context, ArrayList<QRCode> qrCodes) {
        super(context, R.layout.contact_list_content, qrCodes);
        this.context = context;
        this.qrList = qrCodes;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.qrcode_array_adapter, parent, false);
        this.position = p;
        TextView qrCodeTextView = rowView.findViewById(R.id.qr_name_tw);

        SharedPreferences sharedPref = context.getSharedPreferences("myPref", MODE_PRIVATE);
        String username = sharedPref.getString("username","N/A");
        String email = sharedPref.getString("email","N/A");
        String phone = sharedPref.getString("phone","N/A");

        qrCodeTextView.setText(qrList.get(p).getQrcodeName());

        ImageButton delete = rowView.findViewById(R.id.delete_qr_btn);
        ImageButton detail = rowView.findViewById(R.id.detail_qr_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String document_name = qrList.get(p).getHashCode();
                qrList.remove(p);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Players").document(username).collection("QRCode").document(document_name).delete();

                notifyDataSetChanged();
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QRDetailActivity.class);
                intent.putExtra("qrName", qrList.get(p).getQrcodeName());

                context.startActivity(intent);
            }
        });

        return rowView;
    }
}