package com.example.myplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String PLACES_TABLE = "PLACESTABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "myplaces.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PLACES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addPlace(PlacesModel placesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, placesModel.getTitle());
        cv.put(COLUMN_DESCRIPTION, placesModel.getDescription());

        long insert = db.insert(PLACES_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<PlacesModel> getAllPlaces(){
        List<PlacesModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+PLACES_TABLE + " ORDER BY "+COLUMN_TITLE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int placeId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                PlacesModel placeModel = new PlacesModel(placeId, title, description);
                returnList.add(placeModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteAllPlaces(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+PLACES_TABLE);
    }
}
