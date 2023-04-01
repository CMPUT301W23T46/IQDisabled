package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;
/**
 A dialog fragment for searching players by code.
 */
public class SearchFragmentByCode extends DialogFragment {

    private Button searchByCodeAddBtn, searchByCodeCancelBtn;
    private EditText latitudeEditText, longitudeEditText;

    /**
     Sets the size of the dialog window.
     */
    @Override
    public void onStart() {
        super.onStart();
        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels*0.30);
        getDialog().getWindow().setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setLayout(width,height);
    }

    /**
     Inflates the view for the dialog fragment and sets up the add and cancel buttons.
     @param inflater The LayoutInflater object that can be used to inflate views.
     @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     @return The View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.search_fragment_by_code,container,false);
        latitudeEditText = v.findViewById(R.id.search_fragment_latitude_input);
        longitudeEditText = v.findViewById(R.id.search_fragment_longitude_input);
        searchByCodeAddBtn = v.findViewById(R.id.search_by_code_add_btn);
        searchByCodeCancelBtn = v.findViewById(R.id.search_by_code_cancel_btn);
        searchByCodeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                double longitude = Double.parseDouble(longitudeEditText.getText().toString());

                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                dismiss();
            }
        });

        searchByCodeCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return v;
    }



}
