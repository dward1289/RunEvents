package com.madgeek.devonaward.runevents;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by devonaward on 8/27/14.
 */
public class CustomLisFav extends ArrayAdapter<String> {

    Activity context;
    String[] titleList;
    String[] dateList;
    String[] areaList;
    String[] runList;
    String[] daysList;
    String[] signList;

    public CustomLisFav(Activity context,
                      String[] titleList,String[] dateList, String[] areaList, String[] runList, String[] daysList, String[] signList ) {
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

        txtTitle.setText(titleList[position]);
        txtArea.setText(areaList[position]);
        txtDate.setText(dateList[position]);
        txtRun.setText(runList[position] + " RUN");
        txtDays.setText(daysList[position] + " days away!");
        txtSign.setText(signList[position]);
        return rowView;
    }
}
