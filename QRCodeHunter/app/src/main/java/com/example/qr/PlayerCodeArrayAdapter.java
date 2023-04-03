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
 * An adapter used to display a ListView of QR codes associated with a particular player.
 */
public class PlayerCodeArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> codeList;
    /**
     * Constructs a new PlayerCodeArrayAdapter instance.
     *
     * @param context the context in which the adapter will be used
     * @param codes a List of strings representing the QR codes associated with the player
     */
    public PlayerCodeArrayAdapter(Context context, List<String> codes) {
        super(context, R.layout.contact_code_content, codes);
        this.context = context;
        this.codeList = codes;
    }

    @Override
    /**
     * Gets the view that displays the data at the specified position in the data set.
     *
     * @param position the position of the item within the adapter's data set
     * @param convertView the view to be reused, if possible
     * @param parent the parent ViewGroup that this view will eventually be attached to
     * @return a View object representing the data at the specified position
     */
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_code_content, parent, false);
        TextView codeNameTextView = rowView.findViewById(R.id.contact_code_name);
        codeNameTextView.setText(codeList.get(p));

        ImageButton detailBtn = rowView.findViewById(R.id.detail_btn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Called when the detail button for a QR code is clicked. This method creates a new Intent and starts the
             * QRDetailActivity for the selected QR code.
             *
             * @param v the View that was clicked
             */
            public void onClick(View v) {
                Intent intent = new Intent(context, QRDetailActivity.class);
                intent.putExtra("qrName", codeList.get(p));

                context.startActivity(intent);
            }
        });

        return rowView;

    }
}
