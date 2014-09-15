package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SignUpWeb extends Activity {

    String fetchedTitle;
    String fetchedDate;
    String fetchedArea;
    String fetchedcityState;
    String fetchedAddress;
    String fetchedZip;
    String fetchedRun;
    String fetchedURL;
    String countdownChecked;
    WebView theWeb;
    Date actualDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_web);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fetch data that was sent from EventDetails and holds it.
        Intent intent = getIntent();
        fetchedTitle = intent.getStringExtra("title");
        fetchedDate = intent.getStringExtra("date");
        fetchedArea = intent.getStringExtra("area");
        fetchedAddress = intent.getStringExtra("address");
        fetchedcityState = intent.getStringExtra("cityState");
        fetchedZip = intent.getStringExtra("zipcode");
        fetchedRun = intent.getStringExtra("run");
        fetchedURL = intent.getStringExtra("url");

        //Display site
        theWeb = (WebView)findViewById(R.id.web);
        theWeb.getSettings().setJavaScriptEnabled(true);
        theWeb.setWebViewClient(new WebViewClient());
        theWeb.getSettings().setLoadWithOverviewMode(true);
        theWeb.setInitialScale(600);


        theWeb.loadUrl(fetchedURL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Saves event and navigate back to main screen.
        if (id == R.id.action_registered) {

            //Get current date
            Calendar c1 = Calendar.getInstance();
            c1.getTime();

            //Get second calendar
            Calendar c2 = Calendar.getInstance();

            //Convert second calendar string to date
            DateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy");
            DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

            try {
                actualDate = inputFormat.parse(fetchedDate);
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
            c2.setTime(convertedDate);

            //Get the countdown of days
            String elapsedDaysText = null;
            try
            {
                long milliSeconds1 = c1.getTimeInMillis();
                long milliSeconds2 = c2.getTimeInMillis();
                long periodSeconds = (milliSeconds2 - milliSeconds1) / 1000;
                long elapsedDays = periodSeconds / 60 / 60 / 24 + 1;
                elapsedDaysText = String.format("%d", elapsedDays);
            }
            catch (Exception e)
            {

            }
            countdownChecked = elapsedDaysText+" days away!";

            //Save event to database
            DBHandler dbHandler = new DBHandler(this);
            dbHandler.addEvent(new DBItems(fetchedTitle, fetchedAddress, fetchedcityState, fetchedZip, fetchedDate, fetchedRun, fetchedURL, "Already Signed Up", countdownChecked));
            List<DBItems> events = dbHandler.getAllEvents();
            for (DBItems en : events) {
                String log = "Id: " + en.getID() + " ,Event: " + en.getTitle() + " ,Address: " + en.getAddress()
                        + "City and State: " + en.getCityState() + " ,Zip: " + en.getZipcode() + " ,Date: "
                        + en.getDate() + " ,Run: " + en.gettheRun() + " ,URL: " + en.getRegisterURL()
                        + " ,SignUp: " + en.getsignUp() + " ,Countdown: " + en.getCountdown();
                // Writing event to log
                Log.i("SQLite Working", log);
            }
            //Display alert for save success
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpWeb.this);
            alertDialogBuilder.setTitle(this.getTitle());
            alertDialogBuilder.setMessage("You have successfully registered for the race.\n The event has been saved.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent infoIntent = new Intent(SignUpWeb.this, Main.class);
                    SignUpWeb.this.startActivity(infoIntent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show alert
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
