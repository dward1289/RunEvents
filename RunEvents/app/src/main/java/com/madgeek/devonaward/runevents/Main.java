package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main extends Activity {

    EditText search;
    ImageButton searchBtn;
    Button btn5k;
    Button btn10k;
    Button viewFavBtn;
    EditText searchF;
    ListView mainList;
    ProgressDialog dialog;

    //Locate user settings
    LocateUser locateUser;
    double latitude;
    double longitude;
    //Custom adapter
    CustomList adapter;
    //Event information
    String theEventDate;
    String theEventName;
    String theEventRegistration;
    String theEventCity;
    String theEventState;
    String theEventZip;
    String theEventAddress;

    //Title of events
    ArrayList<String> theTitleList = new ArrayList<String>(11);
    //City and State
    ArrayList<String> theAreaList = new ArrayList<String>(11);
    //5K or 10K
    ArrayList<String> theRunList = new ArrayList<String>(11);
    //Dates
    ArrayList<String> theDateList = new ArrayList<String>(11);
    //Addresses
    ArrayList<String> theAddressList = new ArrayList<String>(11);
    //Zip Codes
    ArrayList<String> theZipList = new ArrayList<String>(11);
    //Register Links
    ArrayList<String> theRegisterList = new ArrayList<String>(11);

    //This URL will contain the current location of the user.
    private static String url;

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

        //Get list view
        mainList=(ListView)findViewById(R.id.listViewData);

        //Get current location
        locateUser = new LocateUser(Main.this);
        // check if GPS enabled
        if(locateUser.GetLocation()){

            latitude = locateUser.getLatitude();
            longitude = locateUser.getLongitude();

            Log.i("LOCATION FOUND","Your Location is - Lat: " + latitude + " Long: " + longitude);
        }else{
            //Unable to get location
            //GPS or Network is not enabled
            //Display location settings
            locateUser.SettingsAlert();
        }
        //Complete search with current location of user by default.
        url = "http://api.amp.active.com/v2/search/?lat_lon="+latitude+"%2C"+longitude+"&radius=50&query=5k&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";

        //Search field and search button defined
        search = (EditText) actionBar.getCustomView().findViewById(
                R.id.searchfield);

        searchBtn = (ImageButton) actionBar.getCustomView().findViewById(
                R.id.searchBtn);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);


        //10 items will display by default and 5K will be the runs displayed
        //by default.
        for (int i = 0; i < 10; i++){
            theRunList.add("5K");

        }
        //Get the data
        new GetData().execute();

        //User enters city name and activates search.
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Get city search text
                searchF = (EditText)findViewById(R.id.searchfield);
                String searchFtxt = searchF.getText().toString();
                String btn5KTxt = btn5k.getText().toString();
                String btn10KTxt = btn10k.getText().toString();

                //If the edit text is empty, an alert will display
                if (searchFtxt.matches("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main.this);
                    alertDialogBuilder.setTitle("Run Events");
                    alertDialogBuilder.setMessage("Please enter a city name.");
                    alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show alert
                    alertDialog.show();
                }else{
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    theRunList.clear();
                    theTitleList.clear();
                    theAreaList.clear();
                    theDateList.clear();
                    theAddressList.clear();
                    theZipList.clear();
                    theRegisterList.clear();


                    //Complete search for city
                    if(btn5k.getCurrentTextColor() == Color.rgb(53,232,89)) {
                        for (int i = 0; i < 10; i++){
                            theRunList.add("5K");
                        }
                        url = "http://api.amp.active.com/v2/search/?city=" + searchFtxt + "&query=" + btn5KTxt + "&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";
                        Log.i("THE RUN: ", btn5KTxt);
                        new GetData().execute();
                    }
                    if(btn10k.getCurrentTextColor() == Color.rgb(53,232,89)) {
                        for (int i = 0; i < 10; i++){
                            theRunList.add("10K");
                        }
                        url = "http://api.amp.active.com/v2/search/?city=" + searchFtxt + "&query=" + btn10KTxt + "&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";
                        new GetData().execute();
                    }
                }

            }

        });

        btn5k = (Button) findViewById(R.id.btn5);
        btn10k = (Button) findViewById(R.id.btn10);
        viewFavBtn = (Button) findViewById(R.id.viewFavBtn);

        //5K button settings
        btn5k.setTextColor(Color.rgb(53,232,89));

        //10K button settings
        btn10k.setTextColor(Color.WHITE);

        //If the 5K button is selected, the 10K button will not be selected at the same time.
        btn5k.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                btn5k.setTextColor(Color.rgb(53, 232, 89));
                btn10k.setTextColor(Color.WHITE);

                adapter.clear();
                adapter.notifyDataSetChanged();
                theRunList.clear();
                theTitleList.clear();
                theAreaList.clear();
                theDateList.clear();
                theAddressList.clear();
                theZipList.clear();
                theRegisterList.clear();

                for (int i = 0; i < 10; i++) {
                    theRunList.add("5K");
                }
                //5K races will be retrieved here...
                url = "http://api.amp.active.com/v2/search/?lat_lon=" + latitude + "%2C" + longitude + "&radius=50&query=5k&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";
                new GetData().execute();
            }
        });


        //If the 10K button is selected, the 5K button will not be selected at the same time.
        btn10k.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                btn10k.setTextColor(Color.rgb(53, 232, 89));
                btn5k.setTextColor(Color.WHITE);

                adapter.clear();
                adapter.notifyDataSetChanged();
                theRunList.clear();
                theTitleList.clear();
                theAreaList.clear();
                theDateList.clear();
                theAddressList.clear();
                theZipList.clear();
                theRegisterList.clear();

                for (int i = 0; i < 10; i++) {
                    theRunList.add("10K");
                }
                //10K races will be retrieved here...
                url = "http://api.amp.active.com/v2/search/?lat_lon=" + latitude + "%2C" + longitude + "&radius=50&query=10k&current_page=1&per_page=10&sort=distance&start_date=2014-09-01..&exclude_children=true&api_key=sqq35zvx6a8rgmxhy9csm8qj";
                new GetData().execute();
            }
        });


        //Custom adapter displays and add functionality to custom list view
        adapter = new CustomList(Main.this, theTitleList, theDateList, theAreaList, theRunList);
        //Add custom adapter to list view and setup onClick listener
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(Main.this, "Event: " + theTitleList.get(+position), Toast.LENGTH_SHORT).show();
                //Send data to info view
                Intent infoIntent = new Intent(Main.this, EventDetails.class);
                infoIntent.putExtra("title", theTitleList.get(+position));
                infoIntent.putExtra("date", theDateList.get(+position));
                infoIntent.putExtra("run", theRunList.get(+position));
                infoIntent.putExtra("area", theAddressList.get(+position)+"\n" + theAreaList.get(+position)+ " "+theZipList.get(+position));
                infoIntent.putExtra("address", theAddressList.get(+position));
                infoIntent.putExtra("cityState", theAreaList.get(+position));
                infoIntent.putExtra("zipcode", theZipList.get(+position));
                infoIntent.putExtra("url", theRegisterList.get(+position));

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
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            Log.i("WORKING", "WORKING ON IT...");

            dialog = ProgressDialog.show(Main.this, "",
                    "Loading events. Please wait...", true);
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

                        theEventName = c.getString("assetName");
                        theEventDate = c.getString("activityStartDate");
                        theEventRegistration = c.getString("registrationUrlAdr");
                        theEventCity = c.getJSONObject("place").getString("cityName");
                        theEventState = c.getJSONObject("place").getString("stateProvinceCode");
                        theEventZip = c.getJSONObject("place").getString("postalCode");
                        theEventAddress = c.getJSONObject("place").getString("addressLine1Txt");

                        Log.i("API WORKING DATA", theEventName+", "+theEventDate+" "+theEventRegistration+" "+theEventCity+", "+theEventState+" "+theEventZip+" "+theEventAddress);

                        //Format the date to display properly
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
                        Date actualDate = inputFormat.parse(theEventDate);
                        String dateReformated = outputFormat.format(actualDate);
                        //State and city string
                        String theArea = theEventCity+", "+theEventState;
                        //Populate the custom list view with data
                        theTitleList.add(theEventName);
                        theAreaList.add(theArea);
                        theDateList.add(dateReformated);
                        theAddressList.add(theEventAddress);
                        theZipList.add(theEventZip);
                        theRegisterList.add(theEventRegistration);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
            Log.i("API WORKING 2", "GREAT");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

        }
    }
}