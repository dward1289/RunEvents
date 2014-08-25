package com.madgeek.devonaward.runevents;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class Main extends Activity {

    EditText search;
    ImageButton searchBtn;
    Button btn5k;
    Button btn10k;
    Button viewFavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the actionbar
        ActionBar actionBar = getActionBar();

        //Link the custom actionbar to the original actionbar
        actionBar.setCustomView(R.layout.action_bar_custom);

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
            }

        });


        btn5k = (Button) findViewById(R.id.btn5);
        btn10k = (Button) findViewById(R.id.btn10);
        viewFavBtn = (Button) findViewById(R.id.viewFavBtn);
        //5K button will be enabled by default
        btn5k.setPressed(true);
        //5K button settings
        btn5k.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        btn5k.setTextColor(Color.DKGRAY);

        //10K button settings
        btn10k.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        btn10k.setTextColor(Color.WHITE);

        //If the 5K button is selected, the 10K button will not be selected at the same time.
        btn5k.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn5k.setPressed(true);
                btn5k.setTextColor(Color.DKGRAY);
                btn10k.setTextColor(Color.WHITE);
                btn10k.setPressed(false);


                //5K races will be retrieved here...
                return true;
            }
        });


        //If the 10K button is selected, the 5K button will not be selected at the same time.
        btn10k.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn10k.setPressed(true);
                btn5k.setPressed(false);
                btn10k.setTextColor(Color.DKGRAY);
                btn5k.setTextColor(Color.WHITE);


                //10K races will be retrieved here...
                return true;
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
}
