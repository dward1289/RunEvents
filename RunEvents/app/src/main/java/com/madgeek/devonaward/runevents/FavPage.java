package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class FavPage extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;
    ListView favList;

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
        setContentView(R.layout.activity_fav_page);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listBtn = (ImageButton) findViewById(R.id.btnListOnly);
        calBtn = (ImageButton) findViewById(R.id.btnCList);

        //List button will be enabled by default
        listBtn.setPressed(true);
        listBtn.setImageResource(R.drawable.ic_listit);

        //CList onClick will navigate to calendar list view
        calBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent calIntent = new Intent(FavPage.this, FavPageCal.class);
                FavPage.this.startActivity(calIntent);

                return true;
            }
        });

        //Custom adapter displays and add functionality to custom list view
        CustomLisFav adapter = new
                CustomLisFav(FavPage.this, titleList, dateList, areaList, runList, daysList, signList);
        //Get list view
        favList=(ListView)findViewById(R.id.listViewList);
        //Add custom adapter to list view and setup onClick listener
        favList.setAdapter(adapter);
        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(FavPage.this, "Event: " + titleList[+position], Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fav_page, menu);
        return true;
    }


}
