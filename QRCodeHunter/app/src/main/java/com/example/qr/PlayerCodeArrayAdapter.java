package com.example.qr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
/**
 * This class extends the ArrayAdapter class and represents an adapter for the QR code list view of a player.
 * It provides a customized view for each QR code item in the list.
 */
public class PlayerCodeArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> codeList;

    /**
     * Constructs a new instance of the PlayerCodeArrayAdapter.
     *
     * @param context the context of the adapter
     * @param codes a list of String objects representing the QR codes of the player
     */
    public PlayerCodeArrayAdapter(Context context, List<String> codes) {
        super(context, R.layout.contact_code_content, codes);
        this.context = context;
        this.codeList = codes;
    }
    /**
     * Overrides the getView method of the ArrayAdapter class to provide a customized view for each QR code item in the list.
     * @param convertView the recycled view to populate
     * @param parent the parent view group of the item
     * @return the view for the specified position in the list
     */
    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_code_content, parent, false);
        TextView codeNameTextView = rowView.findViewById(R.id.contact_code_name);
        codeNameTextView.setText(codeList.get(p));

        ImageButton detailBtn = rowView.findViewById(R.id.detail_btn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QRDetailActivity.class);
                intent.putExtra("qrName", codeList.get(p));

                context.startActivity(intent);
            }
        });
        return rowView;

    }
}
