package com.madgeek.devonaward.runevents;

/**
 * Created by devonaward on 9/8/14.
 *
 * SQLite database for saved events. This database will
 * be displayed in list view and calendar list view.
 */
        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventManager";

    //Event table name
    private static final String TABLE_EVENTS = "events";

    //Event Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_CITYSTATE = "cityState";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_DATE = "date";
    private static final String KEY_RUN = "theRun";
    private static final String KEY_REGISTERURL = "registerURL";
    private static final String KEY_SIGNUP = "signup";
    private static final String KEY_COUNTDOWN = "countdown";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_CITYSTATE + " TEXT," + KEY_ZIPCODE + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_RUN + " TEXT," + KEY_REGISTERURL + " TEXT,"
                + KEY_SIGNUP + " TEXT," + KEY_COUNTDOWN + " TEXT"+ ")";
        db.execSQL(CREATE_EVENT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new event
    void addEvent(DBItems DBItems) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, DBItems.getTitle());
        values.put(KEY_ADDRESS, DBItems.getAddress());
        values.put(KEY_CITYSTATE, DBItems.getCityState());
        values.put(KEY_ZIPCODE, DBItems.getZipcode());
        values.put(KEY_DATE, DBItems.getDate());
        values.put(KEY_RUN, DBItems.gettheRun());
        values.put(KEY_REGISTERURL, DBItems.getRegisterURL());
        values.put(KEY_SIGNUP, DBItems.getsignUp());
        values.put(KEY_COUNTDOWN, DBItems.getCountdown());

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single event
    DBItems getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_ADDRESS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DBItems dbItems = new DBItems(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5) ,cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
        // return event
        return dbItems;
    }

    // Getting All Events
    public List<DBItems> getAllEvents() {
        List<DBItems> eventList = new ArrayList<DBItems>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBItems dbItems = new DBItems();
                dbItems.setID(Integer.parseInt(cursor.getString(0)));
                dbItems.setTitle(cursor.getString(1));
                dbItems.setAddress(cursor.getString(2));
                dbItems.setCityState(cursor.getString(3));
                dbItems.setZipcode(cursor.getString(4));
                dbItems.setDate(cursor.getString(5));
                dbItems.settheRun(cursor.getString(6));
                dbItems.setRegisterURL(cursor.getString(7));
                dbItems.setsignUp(cursor.getString(8));
                dbItems.setCountdown(cursor.getString(9));
                // Adding event to list
                eventList.add(dbItems);
            } while (cursor.moveToNext());
        }

        // return Event list
        return eventList;
    }

    // Updating single event
    public int updateEvent(DBItems dbItems) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, dbItems.getTitle());
        values.put(KEY_ADDRESS, dbItems.getAddress());
        values.put(KEY_CITYSTATE, dbItems.getCityState());
        values.put(KEY_ZIPCODE, dbItems.getZipcode());
        values.put(KEY_DATE, dbItems.getDate());
        values.put(KEY_RUN, dbItems.gettheRun());
        values.put(KEY_REGISTERURL, dbItems.getRegisterURL());
        values.put(KEY_SIGNUP, dbItems.getsignUp());
        values.put(KEY_COUNTDOWN, dbItems.getCountdown());

        // updating row
        return db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dbItems.getID()) });
    }

    // Deleting single event
    public void deleteEvent(int ev) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID+ "=" + ev, null);
        Log.i("ROWS", "TABLE_EVENTS KEY_ID " + ev);
        db.close();
    }

    // Getting Event Count
    public int getEventCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}

