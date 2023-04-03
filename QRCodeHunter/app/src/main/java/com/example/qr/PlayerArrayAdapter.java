package com.example.qr;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This class extends the ArrayAdapter class and represents an adapter for the player list view.
 * It provides a customized view for each player item in the list.
 */
public class PlayerArrayAdapter extends ArrayAdapter<Player> {
    private final Context context;
    private final Player[] playerList;
    /**
     * Constructs a new instance of the PlayerArrayAdapter.
     *
     * @param context the context of the adapter
     * @param players an array of Player objects to be displayed in the adapter
     */
    public PlayerArrayAdapter(Context context, Player[] players) {
        super(context, R.layout.contact_list_content, players);
        this.context = context;
        this.playerList = players;
    }
    /**
     * Overrides the getView method of the ArrayAdapter class to provide a customized view for each player item in the list.
     *
     * @param position the position of the item in the list
     * @param convertView the recycled view to populate
     * @param parent the parent view group of the item
     * @return the view for the specified position in the list
     */
    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_list_content, parent, false);
        TextView playerNameTextView = rowView.findViewById(R.id.contact_player_name);

        // Set the text of the player name TextView

        playerNameTextView.setText(playerList[p].getPlayName());

        Button viewBtn = rowView.findViewById(R.id.contact_view_btn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dbhelper = new DataBaseHelper();
                dbhelper.getQRCodesNum(playerList[p].getPlayName(), new OnQRCodeLengthComplete() {
                    @Override
                    public void onSuccess(int length) {
                        if (length == 0) {
                            Toast.makeText(context, "No QR Code Found", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(context, PlayerCodeActivity.class);
                            intent.putExtra("name", playerList[p].getPlayName());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        });

        return rowView;
    }
}
