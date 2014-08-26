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
import android.widget.Button;
import android.widget.ImageButton;


public class FavPage extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;

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
        //List button settings
        listBtn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        //CList button settings
        calBtn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        //CList onClick will navigate to calendar list view
        calBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent calIntent = new Intent(FavPage.this, FavPageCal.class);
                FavPage.this.startActivity(calIntent);

                return true;
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
