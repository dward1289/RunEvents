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
    String _signUp;
    String _countdown;

    // Empty constructor
    public DBItems(){

    }
    // constructor
    public DBItems(int id, String title, String address,String cityState, String zipcode, String date, String run, String registerURL, String signUp, String countdown){
        this._id = id;
        this._title = title;
        this._address = address;
        this._cityState = cityState;
        this._zipcode = zipcode;
        this._date = date;
        this._run = run;
        this._registerURL = registerURL;
        this._signUp = signUp;
        this._countdown = countdown;
    }

    // constructor
    public DBItems(String title, String address,String cityState, String zipcode, String date, String run, String registerURL, String signUp, String countdown){
        this._title = title;
        this._address = address;
        this._cityState = cityState;
        this._zipcode = zipcode;
        this._date = date;
        this._run = run;
        this._registerURL = registerURL;
        this._signUp = signUp;
        this._countdown = countdown;
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
    public String gettheRun(){
        return this._run;
    }

    // setting run
    public void settheRun(String run){
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

    // getting signup
    public String getsignUp(){
        return this._signUp;
    }

    // setting signup
    public void setsignUp(String signUp){
        this._signUp = signUp;
    }

    // getting countdown
    public String getCountdown(){
        return this._countdown;
    }

    // setting countdown
    public void setCountdown(String countdown){
        this._countdown = countdown;
    }

}
