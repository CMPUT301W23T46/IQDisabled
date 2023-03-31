package com.example.qr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class QrCodesArrayAdapter extends ArrayAdapter<QRCode> {
    private final Context context;
    private final List<QRCode> qrcodesList;

    public QrCodesArrayAdapter(Context context, List<QRCode> qrCodes) {
        super((Context) context, R.layout.my_qr_codes_list_content, qrCodes);
        this.context = (Context) context;
        this.qrcodesList = qrCodes;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.my_qr_codes_list_content, parent, false);
    }
        else {
            rowView = convertView;
    }

        TextView qrCodeNameTextView = rowView.findViewById(R.id.qr_codes_name);

        QRCode qrCode = qrcodesList.get(p);
        qrCodeNameTextView.setText(qrCode.getQrcodeName());

        return rowView;
    }
}
