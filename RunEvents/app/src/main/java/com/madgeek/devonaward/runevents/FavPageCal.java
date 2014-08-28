package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class FavPageCal extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;
    CalendarView calendarView;
    ListView favcalList;
    //Dummy data currently. JSON data will be populated in the arrays from API.
    //Title of events
    String[] titleList = {
            "Run in the Name of Love",
            "Color Me Rad",
            "Zombie Run"
    };
    //Dates
    String[] dateList = {
            "August 30, 2014",
            "September 12, 2014",
            "September 20, 2014"
    };
    //City and State
    String[] areaList = {
            "Durham, NC",
            "Durham, NC",
            "Raleigh, NC"
    };
    //5K or 10K
    String[] runList = {
            "5K",
            "5K",
            "5K"
    };

    String[] daysList = {
            "3",
            "15",
            "23"
    };

    String[] signList = {
            "Sign Up Soon",
            "Already Signed Up",
            "Already Signed Up",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page_cal);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listBtn = (ImageButton)findViewById(R.id.btnListOnly);
        calBtn = (ImageButton)findViewById(R.id.btnCList);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        //Calendar view currently disabled
        calendarView.setEnabled(false);

        //Display alert for calendar functionality
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FavPageCal.this);
        alertDialogBuilder.setTitle(this.getTitle());
        alertDialogBuilder.setMessage("User will be able to select a date and events will display that are in the selected week.");
        alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

        //CList button will be enabled by default
        calBtn.setPressed(true);
        calBtn.setImageResource(R.drawable.ic_calendarlist);

        //List onClick will navigate to list view
        listBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent listIntent = new Intent(FavPageCal.this, FavPage.class);
                FavPageCal.this.startActivity(listIntent);

                return true;
            }
        });

        //Custom adapter displays and add functionality to custom list view
        CustomLisFav adapter = new
                CustomLisFav(FavPageCal.this, titleList, dateList, areaList, runList, daysList, signList);
        //Get list view
        favcalList=(ListView)findViewById(R.id.listViewCal);
        //Add custom adapter to list view and setup onClick listener
        favcalList.setAdapter(adapter);
        favcalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(FavPageCal.this, "Event: " + titleList[+position], Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fav_page_cal, menu);
        return true;
    }

}
