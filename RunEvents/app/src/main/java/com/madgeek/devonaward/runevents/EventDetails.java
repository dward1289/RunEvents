package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventDetails extends Activity {

    TextView theTitle;
    TextView theDate;
    TextView theArea;
    TextView theRun;
    String fetchedTitle;
    String fetchedDate;
    String fetchedArea;
    String fetchedcityState;
    String fetchedAddress;
    String fetchedZip;
    String fetchedRun;
    String fetchedURL;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get text views
        theTitle = (TextView)findViewById(R.id.eventTitle);
        theArea = (TextView)findViewById(R.id.addressTxt);
        theDate = (TextView)findViewById(R.id.dateTxt);
        theRun = (TextView)findViewById(R.id.distanceTxt);

        //Fetch data that was sent from Main
        Intent intent = getIntent();
        fetchedTitle = intent.getStringExtra("title");
        fetchedDate = intent.getStringExtra("date");
        fetchedArea = intent.getStringExtra("area");
        fetchedAddress = intent.getStringExtra("address");
        fetchedcityState = intent.getStringExtra("cityState");
        fetchedZip = intent.getStringExtra("zipcode");
        fetchedRun = intent.getStringExtra("run");
        fetchedURL = intent.getStringExtra("url");

        //Display fetched data
        theTitle.setText(fetchedTitle);
        theArea.setText(fetchedArea);
        theDate.setText(fetchedDate);
        theRun.setText(fetchedRun);

        //Register and view more info button with onClick
        regBtn = (Button)findViewById(R.id.infoRegBtn);
        regBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Navigate to web view
                Intent webIntent = new Intent(EventDetails.this, SignUpWeb.class);
                //Get website to sign up
                //infoIntent.putExtra("site", site);
                webIntent.putExtra("title", fetchedTitle);
                webIntent.putExtra("date", fetchedDate);
                webIntent.putExtra("run", fetchedRun);
                webIntent.putExtra("area", fetchedArea);
                webIntent.putExtra("address", fetchedAddress);
                webIntent.putExtra("cityState", fetchedcityState);
                webIntent.putExtra("zipcode", fetchedZip);
                webIntent.putExtra("url", fetchedURL);
                EventDetails.this.startActivity(webIntent);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Navigates to add event page with event info
        if (id == R.id.action_saveEvent) {
            Intent addEventIntent = new Intent(EventDetails.this, AddEvent.class);
            addEventIntent.putExtra("title", fetchedTitle);
            addEventIntent.putExtra("date", fetchedDate);
            addEventIntent.putExtra("run", fetchedRun);
            addEventIntent.putExtra("area", fetchedArea);
            addEventIntent.putExtra("address", fetchedAddress);
            addEventIntent.putExtra("cityState", fetchedcityState);
            addEventIntent.putExtra("zipcode", fetchedZip);
            addEventIntent.putExtra("url", fetchedURL);
            EventDetails.this.startActivity(addEventIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
