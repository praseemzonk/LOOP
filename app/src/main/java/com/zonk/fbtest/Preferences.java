package com.zonk.fbtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.zonk.fbtest.Model.User;

import org.codehaus.jackson.map.ObjectMapper;


public class Preferences {

    private static final String SERVER_URL = "SERVER_URL";
    private static final String ADD_AUTH_IN_HEADER = "ADD_AUTH_IN_HEADER";
    private final String USER_DTO = "USER_DTO";
    private final String PLAN = "PLAN";

    private final String B2B = "B2B";
    private final String CHAT = "CHAT";

    private final String PAGES = "PAGES";
    private final String NOTIFICATION="NOTIFICATION";
    private final String ABSOLUTE_PATH = "ABSOLUTE_PATH";

    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private static final String LATITUDE= "LATITUDE";

    private static final String TEXT= "TEXT";

    private static final String SKILL= "SKILL";
    private static final String LONGITUDE= "LONGITUDE";
    private static final String LOGGEDIN= "LOGGEDIN";
    private static final String OTPVERIFIED= "OTPVERIFIED";
    private static final String SKILLSSELECTED= "SKILLSSELECTED";
    private static final String SKILLSLEVELSSELECTED= "SKILLSLEVELSSELECTED";
    private static final String PROFILEVERIFIED= "PROFILEVERIFIED";

    private static final String SEARCH_LOG_ID="SEARCH_LOG_ID";

    private Context context;

    public Preferences(Context context) {
        super();
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean OTPVerified() {
        return getBoolean(OTPVERIFIED,false);
    }


    public  void setOtpverified(boolean verified){
        setBoolean(OTPVERIFIED,verified);
    }


    public boolean SignedIn() {
        return getBoolean(LOGGEDIN,false);
    }


    public  void setLoggedin(boolean verified){
        setBoolean(LOGGEDIN,verified);
    }


    public boolean skillSelected() {
        return getBoolean(SKILLSSELECTED,false);
    }


    public  void setSkillsselected(boolean verified){
        setBoolean(SKILLSSELECTED,verified);
    }


    public boolean skillLevelSelected() {
        return getBoolean(SKILLSLEVELSSELECTED,false);
    }


    public  void setskillLevelSelected(boolean verified){
        setBoolean(SKILLSLEVELSSELECTED,verified);
    }


    public boolean ProfileVerified() {
        return getBoolean(PROFILEVERIFIED,false);
    }


    public  void setProfileverified(boolean verified){
        setBoolean(PROFILEVERIFIED,verified);
    }



    public String B2B() {
        return getString(B2B, "false");
    }


    public  void setB2B(String verified){
        setString(B2B,verified);
    }


    private String getString(String key, String def) {
        SharedPreferences prefs = getSharedPreferences(key);
        String s = prefs.getString(key, def);
        return s;
    }

    private void setString(String key, String val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    private boolean getBoolean(String key, boolean def) {
        SharedPreferences prefs = getSharedPreferences(key);
        boolean b = prefs.getBoolean(key, def);
        return b;
    }

    private void setBoolean(String key, boolean val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.commit();
    }

    public String getServerUrl() {
        return getString(SERVER_URL, context.getString(R.string.server_url));
    }

    public boolean addAuthInHeader() {
        return getBoolean(ADD_AUTH_IN_HEADER, false);
    }
    public void setAddAuthInHeader(boolean addAuthInHeader) {
        setBoolean(ADD_AUTH_IN_HEADER, addAuthInHeader);
    }

    public String getAuthToken() {
        return getString(AUTH_TOKEN, null);
    }
    public void setAuthToken(String authToken) {
        setString(AUTH_TOKEN, authToken);
    }



    public  void setLatitude(String latitude){
        setString(LATITUDE,latitude);
    }

    public String getText() {
        return getString(TEXT, null);
    }

    public  void setText(String text){
        setString(TEXT,text);
    }

    public String getLatitude() {
        return getString(LATITUDE, null);
    }



    public  void setLongitude(String longitude){
        setString(LONGITUDE,longitude);
    }


    public String getSkill() {
        return getString(SKILL, null);
    }



    public  void setSkill(String longitude){
        setString(SKILL,longitude);
    }


    public String getABSOLUTE_PATH() {
        return getString(ABSOLUTE_PATH, null);
    }


    public  void setABSOLUTE_PATH(String ABSOLUTE_PATH){
        setString(ABSOLUTE_PATH,ABSOLUTE_PATH);
    }

    public String getLongitude() {
        return getString(LONGITUDE, null);
    }


    public String getSearchLogId() {
        return getString(SEARCH_LOG_ID, null);
    }
    public void setSearchLogId(String searchLogId)
    {
        setString(SEARCH_LOG_ID, searchLogId);
    }

    public  void setNOTIFICATION(boolean notification){
        setBoolean(NOTIFICATION,notification);
    }

    public boolean getNOTIFICATIONBoolean() {
        return getBoolean(NOTIFICATION,false);
    }

    public User getUserDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = getString(USER_DTO, null);
        User userDTO = null;
        try {
            userDTO = objectMapper.readValue(value, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    public void setUserDTO(User userDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = null;
        try {
            value = objectMapper.writeValueAsString(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setString(USER_DTO, value);
    }


    public void logout() {
        setAddAuthInHeader(false);
        setAuthToken(null);
        setUserDTO(null);
    }

    public boolean isLoggedIn(){

        return getAuthToken()!=null || getUserDTO()!= null;

    }
}