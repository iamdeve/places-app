package com.example.myplaces;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button addPlace, viewAllPlaces, deleteAllPlaces, btn_add, btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPlace = findViewById(R.id.addPlace);
        viewAllPlaces = findViewById(R.id.viewAllPlaces);
        deleteAllPlaces = findViewById(R.id.deleteAllPlaces);

        btn_add = findViewById(R.id.btn_add);
        btn_close = findViewById(R.id.btn_close);



        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPlaceAlert();
            }
        });

        viewAllPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlacesListActivity.class);
                startActivity(intent);
            }
        });

        deleteAllPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
               }
        });
    }

    private void showAddPlaceAlert() {
        AlertDialog.Builder alert;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else{
            alert = new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_data, null);

        EditText title, description;

        title = view.findViewById(R.id.et_title);
        description  = view.findViewById(R.id.et_desc);
        btn_add = view.findViewById(R.id.btn_add);
        btn_close = view.findViewById(R.id.btn_close);


        alert.setView(view);

        alert.setCancelable(true);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PlacesModel placesModel;
                try{
                    if(TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(description.getText().toString())){
                        Toast.makeText(MainActivity.this, "Fields should not be empty!", Toast.LENGTH_SHORT).show();
                    }else {
                        placesModel = new PlacesModel(-1, title.getText().toString(), description.getText().toString());
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        boolean success = databaseHelper.addPlace(placesModel);
                        if (success) {
                            Toast.makeText(MainActivity.this, "Places added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                   }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all places ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper myDb = new DatabaseHelper(MainActivity.this);
                Log.d("Check List", "List ===>"+myDb.getAllPlaces().size());
                if(myDb.getAllPlaces().size() > 0){
                    myDb.deleteAllPlaces();
                    Toast.makeText(MainActivity.this, "All Places Deleted Successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No place exist to deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}