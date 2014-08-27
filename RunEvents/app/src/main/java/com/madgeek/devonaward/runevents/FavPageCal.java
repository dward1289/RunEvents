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
import android.widget.CalendarView;
import android.widget.ImageButton;


public class FavPageCal extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page_cal);

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
        //CList button settings
        calBtn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        //List button settings
        listBtn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        //List onClick will navigate to list view
        listBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent listIntent = new Intent(FavPageCal.this, FavPage.class);
                FavPageCal.this.startActivity(listIntent);

                return true;
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
