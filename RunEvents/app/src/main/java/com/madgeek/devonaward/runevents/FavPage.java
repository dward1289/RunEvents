package com.madgeek.devonaward.runevents;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FavPage extends Activity {

    ImageButton listBtn;
    ImageButton calBtn;
    ListView favList;
    List<DBItems> savedItemsSQL;

    //Title of events
    ArrayList<String> titleList = new ArrayList<String>(100);
    //Dates
    ArrayList<String> dateList = new ArrayList<String>(100);
    //City and State
    ArrayList<String> areaList = new ArrayList<String>(100);
    //5K or 10K
    ArrayList<String> runList = new ArrayList<String>(100);
    //Countdown days
    ArrayList<String> daysList = new ArrayList<String>(100);
    //Sign Up
    ArrayList<String> signList = new ArrayList<String>(100);

    ArrayList<String> warningList = new ArrayList<String>(100);
    ArrayList<String> addressList = new ArrayList<String>(100);
    ArrayList<String> zipList = new ArrayList<String>(100);
    ArrayList<String> urlList = new ArrayList<String>(100);



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);

        //ActionBar icon is back button.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get database
        final DBHandler dbHandler = new DBHandler(this);
        //Get saved events
        savedItemsSQL = dbHandler.getAllEvents();

        //Check for saved events and Load saved events
        if(savedItemsSQL.size() == 0){
            Log.i("DATABASE","EMPTY DATABASE");
            //Display alert for no saved events
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FavPage.this);
            alertDialogBuilder.setTitle(this.getTitle());
            alertDialogBuilder.setMessage("You haven't saved any events yet.");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show alert
            alertDialog.show();

        }else {
            //Populate fav list
            for (DBItems theItem : savedItemsSQL) {
                titleList.add(theItem.getTitle().toString());
                dateList.add(theItem.getDate().toString());
                areaList.add(theItem.getCityState().toString());
                runList.add(theItem.gettheRun().toString());
                daysList.add(theItem.getCountdown().toString());
                signList.add(theItem.getsignUp().toString());
                addressList.add(theItem.getAddress().toString());
                zipList.add(theItem.getZipcode().toString());
                urlList.add(theItem.getRegisterURL().toString());
            }
            //Find the events that are coming and display notification
            for (DBItems theItem : savedItemsSQL) {
                if (theItem.getCountdown().equals("1 days away!") || theItem.getCountdown().equals("2 days away!")) {
                    warningList.add(theItem.getTitle().toString());
                }
            }
            if(warningList.size()>1){
                    //prepare intent which is triggered if the notification is selected
                    Intent intent = new Intent(this, FavPage.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    //Notification sound
                    Uri theSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    // build notification
                Notification notification  = new Notification.Builder(this)
                        .setContentTitle("Run Events")
                        .setContentText("There are " + warningList.size() + " events coming up soon. Have you signed up?")
                        .setSmallIcon(R.drawable.ic_launcher_notify)
                        .setContentIntent(pendingIntent)
                        .setSound(theSound)
                        .setAutoCancel(true)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //Display notification
                notificationManager.notify(0, notification);
            }else if(warningList.size() == 1){
                StringBuilder sb = new StringBuilder();
                for(String str : warningList){
                    sb.append(str).append("");
                }
                //Convert the array to a string
                String strfromArrayList = sb.toString();
                //prepare intent which is triggered if the notification is selected
                Intent intent = new Intent(this, FavPage.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                //Notification sound
                Uri theSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // build notification
                Notification notification  = new Notification.Builder(this)
                        .setContentTitle("Run Events")
                        .setContentText(strfromArrayList + " is coming up. Have you signed up?")
                        .setSmallIcon(R.drawable.ic_launcher_notify)
                        .setContentIntent(pendingIntent)
                        .setSound(theSound)
                        .setAutoCancel(true)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //Display notification
                notificationManager.notify(0, notification);
            }
        }

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
                                    final int position, long id) {
                Toast.makeText(FavPage.this, "Event: " + titleList.get(+position), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FavPage.this);

                alertDialogBuilder.setTitle("Saved Events");
                alertDialogBuilder.setMessage("What would you like to do with the selected event?");

                alertDialogBuilder.setPositiveButton("View Details", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Navigate to event details
                        Intent eventIntent = new Intent(FavPage.this, EventDetails.class);

                        eventIntent.putExtra("title", titleList.get(+position));
                        eventIntent.putExtra("date", dateList.get(+position));
                        eventIntent.putExtra("run", runList.get(+position));
                        eventIntent.putExtra("area", addressList.get(+position)+"\n" + areaList.get(+position)+ " "+zipList.get(+position));
                        eventIntent.putExtra("url", urlList.get(+position));
                        eventIntent.putExtra("address", addressList.get(+position));
                        eventIntent.putExtra("cityState", areaList.get(+position));
                        eventIntent.putExtra("zipcode", zipList.get(+position));
                        FavPage.this.startActivity(eventIntent);
                    }
                });
                alertDialogBuilder.setNegativeButton("Delete Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dbHandler.deleteEvent(savedItemsSQL.get(position).getID());
                        dbHandler.close();
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();

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
