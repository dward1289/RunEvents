package com.madgeek.devonaward.runevents;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by devonaward on 8/25/14.
 * Displays custom list view on main screen.
 */
public class CustomList extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> titleList;
    ArrayList<String>dateList;
    ArrayList<String>areaList;
    ArrayList<String>runList;

    public CustomList(Activity context,
                      ArrayList<String> titleList,ArrayList<String>dateList, ArrayList<String>areaList, ArrayList<String>runList) {
        super(context, R.layout.custom_list_layout, titleList);
        this.context = context;
        this.titleList = titleList;
        this.dateList = dateList;
        this.areaList = areaList;
        this.runList = runList;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_list_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        TextView txtArea = (TextView) rowView.findViewById(R.id.txtArea);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtDate);
        TextView txtRun = (TextView) rowView.findViewById(R.id.txtRun);

        txtTitle.setText(titleList.get(position));
        txtArea.setText(areaList.get(position));
        txtDate.setText(dateList.get(position));
        txtRun.setText(runList.get(position) + " RUN");
        return rowView;
    }


}

