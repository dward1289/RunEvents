package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main extends Activity {

    EditText search;
    ImageButton searchBtn;
    Button btn5k;
    Button btn10k;
    Button viewFavBtn;

    ListView mainList;

    //Dummy data currently. JSON data will be populated in the arrays from API.
    //Title of events
    String[] titleList = {
            "Run in the Name of Love",
            "Color Me Rad",
            "Zombie Run",
            "Pumpkin Patch 5K",
            "8th Annual Patrol Stroll"
    };
    //Dates
    String[] dateList = {
            "August 30, 2014",
            "September 12, 2014",
            "September 20, 2014",
            "October 5, 2014",
            "October 15, 2014"
    };
    //City and State
    String[] areaList = {
            "Durham, NC",
            "Durham, NC",
            "Raleigh, NC",
            "Morrisville, NC",
            "Chapel Hill, NC"
    };
    //5K or 10K
    String[] runList = {
            "5K",
            "5K",
            "5K",
            "5K",
            "5K"
    };
    //Addresses
    String[] addressList = {
            "103 Hobkin Rd",
            "4501 Railway Dr",
            "2309 Fareway Ln",
            "303 Lockingham Rd",
            "5167 Orangeville Dr"
    };
    //Zip Codes
    String[] zipList = {
            "27707",
            "27713",
            "28115",
            "27587",
            "27510"
    };
    //URLs from JSON data will go here
    //String[] registerURLList;

    //This URL will contain the current location of the user.
    private static String url = "http://api.amp.active.com/v2/search/?city=durham&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";

    //MOST IMPORTANT BECAUSE IT HOLDS ALL OF THE DATA
    private static final String TAG_RESULTED = "results";
    //JSON Array that holds the data retrieved.
    JSONArray theData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the actionbar
        ActionBar actionBar = getActionBar();

        //Link the custom actionbar to the original actionbar
        actionBar.setCustomView(R.layout.action_bar_custom);

        //Get the data
        //Edit for 5k,10k, and City search
        new GetData().execute();


        //Search field and search button defined
        search = (EditText) actionBar.getCustomView().findViewById(
                R.id.searchfield);

        searchBtn = (ImageButton) actionBar.getCustomView().findViewById(
                R.id.searchBtn);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        //Displays text view when user clicks on search button
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Display search
                search.setVisibility(View.VISIBLE);
                search.setEnabled(true);
                //Display keyboard
                InputMethodManager inputMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMM.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);

            //new GetDataCity().execute();



            }

        });

        //Display alert message when enter key is pressed
        //Search: User will be able to find races in other cities.

        btn5k = (Button) findViewById(R.id.btn5);
        btn10k = (Button) findViewById(R.id.btn10);
        viewFavBtn = (Button) findViewById(R.id.viewFavBtn);
        //5K button will be enabled by default
        btn5k.setPressed(true);
        //5K button settings
        btn5k.setTextColor(Color.rgb(53,232,89));

        //10K button settings
        btn10k.setTextColor(Color.WHITE);
        btn10k.setEnabled(false);

        //If the 5K button is selected, the 10K button will not be selected at the same time.
        btn5k.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn5k.setPressed(true);
                btn5k.setTextColor(Color.rgb(0,102,0));
                btn10k.setTextColor(Color.WHITE);
                btn10k.setPressed(false);

                //5K races will be retrieved here...
                //new GetData5K().execute();
                return true;
            }
        });


        //If the 10K button is selected, the 5K button will not be selected at the same time.
        btn10k.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn10k.setPressed(true);
                btn5k.setPressed(false);
                btn10k.setTextColor(Color.rgb(53,232,89));
                btn5k.setTextColor(Color.WHITE);


                //10K races will be retrieved here...
                //new GetData5K().execute();
                return true;
            }
        });

        //Custom adapter displays and add functionality to custom list view
        CustomList adapter = new
                CustomList(Main.this, titleList, dateList, areaList, runList);
        //Get list view
        mainList=(ListView)findViewById(R.id.listViewData);
        //Add custom adapter to list view and setup onClick listener
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Main.this, "Event: " + titleList[+position], Toast.LENGTH_SHORT).show();

                //Send data to info view
                Intent infoIntent = new Intent(Main.this, EventDetails.class);
                infoIntent.putExtra("title", titleList[+position]);
                infoIntent.putExtra("date", dateList[+position]);
                infoIntent.putExtra("run", runList[+position]);
                infoIntent.putExtra("area", addressList[+position]+"\n" + areaList[+position]+ " "+zipList[+position]);
                Main.this.startActivity(infoIntent);

            }
        });
        //View the saved events page
        viewFavBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent myIntent = new Intent(Main.this, FavPage.class);
                Main.this.startActivity(myIntent);
                return true;
            }
        });



    }

    //Get the data and display from API
    //This will be used for 5k, 10k, and City search
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            Log.i("WORKING", "WORKING ON IT...");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Setup the service
            APIjson AJ = new APIjson();

            //Make the request for data
            String jsonStr = AJ.makeServiceCall(url, APIjson.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //Getting JSON Array
                    theData = jsonObj.getJSONArray(TAG_RESULTED);



                    //Loop through data
                    for (int i = 0; i < theData.length(); i++) {
                        JSONObject c = theData.getJSONObject(i);

                        String theEventName = c.getString("assetName");
                        String theEventDate = c.getString("activityStartDate");
                        String theEventRegistration = c.getString("registrationUrlAdr");
                        String theEventCity = c.getJSONObject("place").getString("cityName");
                        String theEventState = c.getJSONObject("place").getString("stateProvinceCode");
                        String theEventZip = c.getJSONObject("place").getString("postalCode");
                        String theEventAddress = c.getJSONObject("place").getString("addressLine1Txt");
                        Log.i("API WORKING DATA", theEventName+", "+theEventDate+" "+theEventRegistration+" "+theEventCity+", "+theEventState+" "+theEventZip+" "+theEventAddress);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i("API WORKING 2", "NOW WHAT?");
        }
    }
}