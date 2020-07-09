package com.store.anime.ostshow.helpers;


public class Config {
    public static final String APP_URL = "http://192.168.1.71:8888/ostshowapi/";


    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static String USER_SHARED_PREF = "user_name";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}