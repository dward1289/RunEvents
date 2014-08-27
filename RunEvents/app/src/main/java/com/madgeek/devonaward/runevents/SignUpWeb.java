package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class SignUpWeb extends Activity {

    String fetchedTitle;
    String fetchedDate;
    String fetchedArea;
    String fetchedRun;
    Context context;
    WebView theWeb;
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
        fetchedRun = intent.getStringExtra("run");

        //Display site
        theWeb = (WebView)findViewById(R.id.web);
        theWeb.getSettings().setJavaScriptEnabled(true);
        theWeb.getSettings().setLoadWithOverviewMode(true);
        theWeb.getSettings().setUseWideViewPort(true);
        theWeb.loadUrl("http://developer.active.com/docs/");
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
        //Navigates back to the event details
        if (id == R.id.action_registered) {

            //Sends the data back to EventDetails and loads EventDetails
            Intent signUpIntent = new Intent(SignUpWeb.this, EventDetails.class);
            signUpIntent.putExtra("title", fetchedTitle);
            signUpIntent.putExtra("date", fetchedDate);
            signUpIntent.putExtra("run", fetchedRun);
            signUpIntent.putExtra("area", fetchedArea);
            SignUpWeb.this.startActivity(signUpIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
