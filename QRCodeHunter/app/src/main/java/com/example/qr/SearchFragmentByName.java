package com.example.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;

public class SearchFragmentByName extends DialogFragment {
//    interface SearchFragmentByNameListener {
//        void onButtonPressed();
//    }
//
//    void onButtonPressed() {
//        SearchFragmentByNameListener listener = (SearchFragmentByNameListener) getParentFragment();
//        listener.onButtonPressed();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.search_fragment_by_name,container,false);
    }



}
