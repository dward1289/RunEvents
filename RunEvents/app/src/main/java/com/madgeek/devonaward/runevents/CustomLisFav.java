package com.madgeek.devonaward.runevents;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by devonaward on 8/27/14.
 */
public class CustomLisFav extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> titleList;
    ArrayList<String> dateList;
    ArrayList<String> areaList;
    ArrayList<String> runList;
    ArrayList<String> daysList;
    ArrayList<String> signList;

    public CustomLisFav(Activity context,
                        ArrayList<String> titleList,ArrayList<String> dateList, ArrayList<String> areaList, ArrayList<String> runList, ArrayList<String> daysList, ArrayList<String> signList ) {
        super(context, R.layout.custom_listfav_layout, titleList);
        this.context = context;
        this.titleList = titleList;
        this.dateList = dateList;
        this.areaList = areaList;
        this.runList = runList;
        this.daysList = daysList;
        this.signList = signList;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_listfav_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        TextView txtArea = (TextView) rowView.findViewById(R.id.txtArea);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtDate);
        TextView txtRun = (TextView) rowView.findViewById(R.id.txtRun);
        TextView txtDays = (TextView) rowView.findViewById(R.id.txtCountit);
        TextView txtSign = (TextView) rowView.findViewById(R.id.txtSign);

        txtTitle.setText(titleList.get(position));
        txtArea.setText(areaList.get(position));
        txtDate.setText(dateList.get(position));
        txtRun.setText(runList.get(position) + " RUN");
        txtDays.setText(daysList.get(position));
        txtSign.setText(signList.get(position));
        return rowView;
    }
}
