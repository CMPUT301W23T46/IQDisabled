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
 * A custom ArrayAdapter used to display a list of players in a ListView.
 */
public class PlayerArrayAdapter extends ArrayAdapter<Player> {
    private final Context context;
    private final Player[] playerList;
    /**
     * Constructs a new PlayerArrayAdapter.
     *
     * @param context the context in which the adapter will be used
     * @param players an array of Player objects representing the players to be displayed in the ListView
     */
    public PlayerArrayAdapter(Context context, Player[] players) {
        super(context, R.layout.contact_list_content, players);
        this.context = context;
        this.playerList = players;
    }
    /**
     * Returns the View to be displayed for a specific player in the ListView.
     *
     * @param p the position of the player in the ListView
     * @param convertView the old view to reuse, if possible
     * @param parent the parent view group
     * @return a View representing the specified player in the ListView
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
