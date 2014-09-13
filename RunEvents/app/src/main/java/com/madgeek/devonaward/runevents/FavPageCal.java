package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FavPageCal extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;
    CalendarView calendarView;
    ListView favcalList;
    CustomLisFav adapter;
    Date actualDate;
    List<DBItems> savedItemsSQL;
    //Title of events
    ArrayList<String> titleList = new ArrayList<String>(100);
    //Dates
    ArrayList<String> dateList = new ArrayList<String>(100);
    //City and State
    ArrayList<String> areaList = new ArrayList<String>(100);
    //5K or 10K
    ArrayList<String> runList = new ArrayList<String>(100);
    //Countdown days
    ArrayList<String> daysList = new ArrayList<String>(100);
    //Sign Up
    ArrayList<String> signList = new ArrayList<String>(100);
    ArrayList<String> queryDate = new ArrayList<String>(100);

    int CurrentWeek;
    int CurrentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page_cal);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listBtn = (ImageButton) findViewById(R.id.btnListOnly);
        calBtn = (ImageButton) findViewById(R.id.btnCList);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        //Get database
        final DBHandler dbHandler = new DBHandler(this);
        //Get saved events
        savedItemsSQL = dbHandler.getAllEvents();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {

                //Clear the list first
                adapter.clear();
                adapter.notifyDataSetChanged();
                runList.clear();
                titleList.clear();
                areaList.clear();
                dateList.clear();
                runList.clear();
                daysList.clear();
                signList.clear();

                //Get today's date
                Calendar now = Calendar.getInstance();
                now.set(Calendar.YEAR,year);
                now.set(Calendar.MONTH,month);
                now.set(Calendar.DATE, dayOfMonth);

                //Get today's month and week number
                CurrentWeek = now.get(Calendar.WEEK_OF_MONTH);
                CurrentMonth = now.get(Calendar.MONTH);

                //Check for saved events and Load saved events
                if(savedItemsSQL.size() == 0){
                    Log.i("DATABASE", "EMPTY DATABASE");
                    //Display alert for no saved events
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FavPageCal.this);
                    alertDialogBuilder.setTitle("Saved Events");
                    alertDialogBuilder.setMessage("You haven't saved any events yet.");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show alert
                    alertDialog.show();

                }else {

                    //Check for events happening for the selected week.
                    for (DBItems theItem : savedItemsSQL) {

                        //Get the dates from the database
                        String thatDate = (theItem.getDate().toString());

                        //Convert string to date
                        DateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy");
                        DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

                        try {
                            actualDate = inputFormat.parse(thatDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String dateReformated = outputFormat.format(actualDate);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        Date convertedDate = new Date();
                        try {
                            convertedDate = dateFormat.parse(dateReformated);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        //Get the month and date number of the events
                        Calendar calender = Calendar.getInstance();
                        calender.setTime(convertedDate);
                        int futureWeek = calender.get(Calendar.WEEK_OF_MONTH);
                        int futureMonth = calender.get(Calendar.MONTH);

                        //If the events are in the selected week, they will display.
                        if(CurrentMonth == futureMonth && CurrentWeek == futureWeek){
                            titleList.add(theItem.getTitle().toString());
                            dateList.add(theItem.getDate().toString());
                            areaList.add(theItem.getCityState().toString());
                            runList.add(theItem.gettheRun().toString());
                            daysList.add(theItem.getCountdown().toString());
                            signList.add(theItem.getsignUp().toString());
                        }
                    }
                }
            }
        });

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
        adapter = new
                CustomLisFav(FavPageCal.this, titleList, dateList, areaList, runList, daysList, signList);
        //Get list view
        favcalList=(ListView)findViewById(R.id.listViewCal);
        //Add custom adapter to list view and setup onClick listener
        favcalList.setAdapter(adapter);
        favcalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(FavPageCal.this, "Event: " + titleList.get(+position), Toast.LENGTH_SHORT).show();
                //Alert: View Details or Delete
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
