package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AddEvent extends Activity {

    TextView theTitle;
    TextView theDate;
    TextView theArea;
    TextView theRun;
    String fetchedTitle;
    String fetchedDate;
    String fetchedArea;
    String fetchedRun;

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
        fetchedRun = intent.getStringExtra("run");

        //Display fetched data
        theTitle.setText(fetchedTitle);
        theArea.setText(fetchedArea);
        theDate.setText(fetchedDate);
        theRun.setText(fetchedRun);
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
            //Display alert for save function
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddEvent.this);
            alertDialogBuilder.setTitle(this.getTitle());
            alertDialogBuilder.setMessage("User will be able to save the event and view them later.");
            alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    dialog.cancel();
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
