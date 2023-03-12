package com.example.qr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button searchByNameBtn = findViewById(R.id.search_player_btn);
        Button searchByCodeBtn = findViewById(R.id.search_QRcode_btn);
        Button cancelBtn = findViewById(R.id.cancel_btn);
        searchByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragmentByName searchFragmentByName = new SearchFragmentByName();
                searchFragmentByName.show(getSupportFragmentManager(),"search fragment by name");
            }
        });
        searchByCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragmentByCode searchFragmentByCode = new SearchFragmentByCode();
                searchFragmentByCode.show(getSupportFragmentManager(),"search fragment by code");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }
}



//    @Override
//    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle
//                             savedInstanceState) {
//        super.onCreateView(inflater,container,savedInstanceState);
//        View v = inflater.inflate(R.layout.search_fragment, container, false);
//        searchByNameBtn = v.findViewById(R.id.search_player_btn);
//        searchByCodeBtn = v.findViewById(R.id.search_QRcode_btn);
//        cancelBtn = v.findViewById(R.id.cancel_btn);
//        searchByNameBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SearchFragment.this, SearchFragmentByName.class);
//                startActivity(intent);
//            }
//        });
//
//        searchByCodeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
//        return v;
//    }
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








