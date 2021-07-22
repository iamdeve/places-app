package com.example.myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlacesListActivity extends AppCompatActivity {

    ListView places_list;
    ArrayAdapter customerArrayAdapter;
    DatabaseHelper databaseHelper;
    PlaceAdapter adapter ;
    Button btn_redirect;
    TextView tv_warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        btn_redirect = findViewById(R.id.btn_redirect);
        tv_warning = findViewById(R.id.tv_warning);

        btn_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacesListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        places_list = findViewById(R.id.places_list);

        databaseHelper = new DatabaseHelper(PlacesListActivity.this);
        showPlacesOnListView(databaseHelper);
    }

    private void showPlacesOnListView(DatabaseHelper databaseHelper) {
//        customerArrayAdapter = new ArrayAdapter<PlacesModel>(PlacesListActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAllPlaces());
//        places_list.setAdapter(customerArrayAdapter)
        List allPlaces = databaseHelper.getAllPlaces();
        if(allPlaces.size() == 0){
            tv_warning.setVisibility(View.VISIBLE);
            btn_redirect.setVisibility(View.VISIBLE);
        }else{
            tv_warning.setVisibility(View.GONE);
            btn_redirect.setVisibility(View.GONE);
        }
        adapter  = new PlaceAdapter(this,databaseHelper.getAllPlaces());
        places_list.setAdapter(adapter);
    }
}