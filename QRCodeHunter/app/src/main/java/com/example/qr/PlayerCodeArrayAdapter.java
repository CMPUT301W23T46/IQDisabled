package com.example.qr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlayerCodeArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> codeList;


    public PlayerCodeArrayAdapter(Context context, List<String> codes) {
        super(context, R.layout.contact_code_content, codes);
        this.context = context;
        this.codeList = codes;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_code_content, parent, false);
        TextView codeNameTextView = rowView.findViewById(R.id.contact_code_name);
        codeNameTextView.setText(codeList.get(p));



        return rowView;

    }
}
