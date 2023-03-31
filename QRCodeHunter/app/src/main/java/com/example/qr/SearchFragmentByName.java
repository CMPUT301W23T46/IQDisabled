package com.example.qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;

/**
 A dialog fragment for searching players by code.
 */

public class SearchFragmentByName extends DialogFragment {

    Button searchByNameAddBtn, searchByNameCancelBtn;
    /**
     * This method is called when the fragment is started. It sets the width and height
     * of the dialog window to 95% of the device's screen size.
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
     * This method is called when the view is being created. It inflates the fragment's
     * layout and initializes the UI components.
     *
     * @param inflater the LayoutInflater object used to inflate the layout
     * @param container the ViewGroup object that contains the fragment
     * @param savedInstanceState a Bundle object containing the saved state of the fragment
     * @return the inflated View object representing the fragment's layout
     */

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
                EditText edx = getView().findViewById(R.id.search_fragment_name_input);
                String username = edx.getText().toString();
                Intent intent = new Intent(getActivity(),ShowOtherProfile.class);
                DataBaseHelper dbhelper = new DataBaseHelper();
                try {
                    dbhelper.getPlayer(username, new OnGetPlayerListener() {
                        @Override
                        public void onSuccess(Player player) {
                            intent.putExtra("name",username);
                            intent.putExtra("phone",player.getPhone_number());
                            intent.putExtra("email",player.getEmail());
                            startActivity(intent);

                        }
                    });
                } catch (Exception e) {
                    System.out.println("catecheddddddd");
                    throw new RuntimeException(e);
                }


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
