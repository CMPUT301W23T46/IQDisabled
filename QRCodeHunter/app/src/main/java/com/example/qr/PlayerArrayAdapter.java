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

public class PlayerArrayAdapter extends ArrayAdapter<Player> {
    private final Context context;
    private final Player[] playerList;

    public PlayerArrayAdapter(Context context, Player[] players) {
        super(context, R.layout.contact_list_content, players);
        this.context = context;
        this.playerList = players;
    }

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
                Intent intent = new Intent(context, PlayerCodeActivity.class);
                intent.putExtra("name", playerList[p].getPlayName());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
