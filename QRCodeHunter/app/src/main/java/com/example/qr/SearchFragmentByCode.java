package com.example.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;

public class SearchFragmentByCode extends DialogFragment {
//    interface SearchFragmentByCodeListener {
//        void onButtonPressed();
//    }
//
//    void onButtonPressed() {
//        SearchFragmentByCodeListener listener = (SearchFragmentByCodeListener) getParentFragment();
//        listener.onButtonPressed();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.search_fragment_by_code, container, false);


    }
}
