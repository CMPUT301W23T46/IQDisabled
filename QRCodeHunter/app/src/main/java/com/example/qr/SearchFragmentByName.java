package com.example.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;

public class SearchFragmentByName extends DialogFragment {

    Button searchByNameAddBtn, searchByNameCancelBtn;

    @Override
    public void onStart() {
        super.onStart();
        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels*0.30);
        getDialog().getWindow().setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setLayout(width,height);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.search_fragment_by_name,container,false);
        searchByNameAddBtn = v.findViewById(R.id.search_by_name_add_btn);
        searchByNameCancelBtn = v.findViewById(R.id.search_by_name_cancel_btn);
        searchByNameAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });

        searchByNameCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return v;
    }



}
