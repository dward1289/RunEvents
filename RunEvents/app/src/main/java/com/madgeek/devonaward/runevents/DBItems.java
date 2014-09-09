package com.madgeek.devonaward.runevents;

/**
 * Created by devonaward on 9/8/14.
 */
//SQLITE database items


public class DBItems{

    //private variables
    int _id;
    String _title;
    String _address;
    String _cityState;
    String _zipcode;
    String _date;
    String _run;
    String _registerURL;

    // Empty constructor
    public DBItems(){

    }
    // constructor
    public DBItems(int id, String title, String address,String cityState, String zipcode, String date, String run, String registerURL){
        this._id = id;
        this._title = title;
        this._address = address;
        this._cityState = cityState;
        this._zipcode = zipcode;
        this._date = date;
        this._run = run;
        this._registerURL = registerURL;
    }

    // constructor
    public DBItems(String title, String address,String cityState, String zipcode, String date, String run, String registerURL){
        this._title = title;
        this._address = address;
        this._cityState = cityState;
        this._zipcode = zipcode;
        this._date = date;
        this._run = run;
        this._registerURL = registerURL;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting title
    public String getTitle(){
        return this._title;
    }

    // setting title
    public void setTitle(String title){
        this._title = title;
    }

    // getting address
    public String getAddress(){
        return this._address;
    }

    // setting address
    public void setAddress(String address){
        this._address = address;
    }

    // getting city and state
    public String getCityState(){
        return this._cityState;
    }

    // setting city and state
    public void setCityState(String cityState){
        this._cityState = cityState;
    }

    // getting zipcode
    public String getZipcode(){
        return this._zipcode;
    }

    // setting zipcode
    public void setZipcode(String zipcode){
        this._zipcode = zipcode;
    }

    // getting date
    public String getDate(){
        return this._date;
    }

    // setting date
    public void setDate(String date){
        this._date = date;
    }

    // getting run
    public String getRun(){
        return this._run;
    }

    // setting run
    public void setRun(String run){
        this._run = run;
    }

    // getting register url
    public String getRegisterURL(){
        return this._registerURL;
    }

    // setting register url
    public void setRegisterURL(String registerURL) {
        this._registerURL = registerURL;
    }
}
