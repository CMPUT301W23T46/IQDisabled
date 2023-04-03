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
/**
 * This class extends the ArrayAdapter class and represents an adapter for the list view of QR codes.
 * It provides a customized view for each QR code item in the list.
 */
public class QRCodeArrayAdapter extends ArrayAdapter<QRCode>{
    private final Context context;
    private final ArrayList<QRCode> qrList;

    private int position;
    /**
     * Constructs a new instance of the QRCodeArrayAdapter.
     *
     * @param context the context of the adapter
     * @param qrCodes an ArrayList of QRCode objects representing the QR codes of the player
     */
    public QRCodeArrayAdapter(Context context, ArrayList<QRCode> qrCodes) {
        super(context, R.layout.contact_list_content, qrCodes);
        this.context = context;
        this.qrList = qrCodes;
    }
    /**
     * Overrides the getView method of the ArrayAdapter class to provide a customized view for each QR code item in the list.
     *
     * @param position the position of the item in the list
     * @param convertView the recycled view to populate
     * @param parent the parent view group of the item
     * @return the view for the specified position in the list
     */
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
            /**
             * Overrides the onClick method of the OnClickListener interface to delete the QR code from the database and notify the adapter of the change.
             *
             * @param v the View that was clicked
             */
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
            /**
             * Overrides the onClick method of the OnClickListener interface to launch a new activity to display the details of the QR code.
             *
             * @param v the View that was clicked
             */
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