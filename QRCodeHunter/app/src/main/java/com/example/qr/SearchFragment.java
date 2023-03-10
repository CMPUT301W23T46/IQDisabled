package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import javax.annotation.Nullable;

public class SearchFragment extends DialogFragment {
    Button searchByNameBtn, searchByCodeBtn, cancelBtn;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle
                             savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        searchByNameBtn = v.findViewById(R.id.search_player_btn);
        searchByCodeBtn = v.findViewById(R.id.search_QRcode_btn);
        cancelBtn = v.findViewById(R.id.cancel_btn);
        searchByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        searchByCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return v;
    }
//
//    @Override
//    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
//
//        SearchFragmentByName searchFragmentByName = new SearchFragmentByName();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.search_fragment, searchFragmentByName);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
////        transaction.replace(R.id.search_fragment,searchFragmentByName);
////        transaction.commit();
//    }
//
//    @Override
//    public void onButtonPressed() {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        SearchFragmentByCode searchFragmentByCode = new SearchFragmentByCode();
//        transaction.replace(R.id.search_fragment, searchFragmentByCode);
//        transaction.addToBackStack("searchByCode");
//        transaction.commit();



    }




