package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AddEvent extends Activity {

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
    RadioGroup signupYN;
    RadioButton nosign;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get text views
        theTitle = (TextView)findViewById(R.id.eventTitle);
        theArea = (TextView)findViewById(R.id.addressTxt);
        theDate = (TextView)findViewById(R.id.dateTxt);
        theRun = (TextView)findViewById(R.id.distanceTxt);

        //Fetch data that was sent from EventDetails
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

        //If the user hasn't signed up the option to sign up will be presented.
        signUpBtn = (Button)findViewById(R.id.infoRegBtn);
        signupYN = (RadioGroup) findViewById(R.id.radioGroupYN2);
        signupYN.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selected =
                        signupYN.getCheckedRadioButtonId();
                nosign = (RadioButton)findViewById(selected);
                if(nosign.getText().equals("No")){
                    signUpBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        //Register and view more info button with onClick
        signUpBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Navigate to web view
                Intent webIntent = new Intent(AddEvent.this, SignUpWeb.class);
                //Get website to sign up
                //infoIntent.putExtra("site", site);
                webIntent.putExtra("title", fetchedTitle);
                webIntent.putExtra("date", fetchedDate);
                webIntent.putExtra("run", fetchedRun);
                webIntent.putExtra("area", fetchedArea);
                AddEvent.this.startActivity(webIntent);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_saving) {
            //Saves event

            //Add event to saved list
            DBHandler dbHandler = new DBHandler(this);
            dbHandler.addEvent(new DBItems(fetchedTitle, fetchedAddress, fetchedcityState, fetchedZip, fetchedDate, fetchedRun, fetchedURL));
            List<DBItems> events = dbHandler.getAllEvents();
            for (DBItems en : events) {
                String log = "Id: " + en.getID() + " ,Event: " + en.getTitle() + " ,Address: " + en.getAddress()
                        + "City and State: " + en.getCityState() + " ,Zip: " + en.getZipcode() + " ,Date: "
                        + en.getDate() + " ,Run: " + en.getRun() + " ,URL: " + en.getRegisterURL();
                // Writing event to log
                Log.i("SQLite Working", log);
            }

                //Display alert for save success
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddEvent.this);
                alertDialogBuilder.setTitle(this.getTitle());
                alertDialogBuilder.setMessage("Event saved!");
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent infoIntent = new Intent(AddEvent.this, Main.class);
                        AddEvent.this.startActivity(infoIntent);
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
