package com.example.myplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends ArrayAdapter<PlacesModel> {
    public PlaceAdapter(Context context, List<PlacesModel> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlacesModel places = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.list_data_title);
        TextView description = (TextView) convertView.findViewById(R.id.list_data_desc);
        // Populate the data into the template view using the data object
        title.setText(places.getTitle());
        description.setText(places.getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}