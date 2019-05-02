package com.app.finlit.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.finlit.data.models.InterestModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class SharedPreferenceHelper {

    public static final String APP_NAME = "Finlit";

    public static final String USER_TOKEN = "userToken";
    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String LOCATION = "location";
    public static final String COORDINATES = "coordinates";
    public static final String EMAIL = "email";
    public static final String USERNAME = "userName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String PROFILE_PIC = "profile_pic";
    public static final String FCM_TOKEN = "fcmToken";
    public static final String NOTIFICATION = "notification";
    public static final String DATES = "dates";
    public static final String FACEBOOK_USER_ID = "facebookUserId";
    public static final String TOUR_COMPLETED = "tourcompleted";
    public static final String HOMECMPLTD = "homecmpltd";
    public static final String FACEBOOK_ACCESS_TOKEN = "facebookAccessToken";
    public static final String ABOUT = "about";
    public static final String QUESTION = "question";
    public static final String RESULT = "result";
    public static final String RESULT_CMLTD = "isresultcompleted";
    public static final String SOUND = "sound";
    public static final String CREATED_AT = "createdat";
    public static final String MATCHGENDER = "matchgender";
    public static final String PAID_QUIZ = "paidquiz";
    public static  final String INTERESTS = "interests";
    public static final String filter = "filter";

    protected final SharedPreferences sharedPreferences;
    protected final SharedPreferences.Editor sharedPreferencesEditor;

    private static SharedPreferenceHelper instance;

    public static SharedPreferenceHelper getInstance() {
        if (instance == null) {
            throw new NullPointerException("SharedHelper was not initialized!");
        }
        return instance;
    }

    public SharedPreferenceHelper(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    protected void delete(String key) {
        if (sharedPreferences.contains(key)) {
            sharedPreferencesEditor.remove(key).commit();
        }
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        sharedPreferencesEditor.putString(key, json);
    }

    protected void savePref(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            sharedPreferencesEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            sharedPreferencesEditor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            sharedPreferencesEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sharedPreferencesEditor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            sharedPreferencesEditor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            sharedPreferencesEditor.putString(key, value.toString());
        }else if (value instanceof Account){
            Gson gson = new Gson();
            String json = gson.toJson(value);
            sharedPreferencesEditor.putString(key, json);
        }
        else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        sharedPreferencesEditor.commit();
    }

    protected <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    protected <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public void clearAll() {
        sharedPreferencesEditor.clear().apply();
    }

    public void clear() {
        saveUserId(null);
        saveUserToken(null);
        saveProfilePic(null);
        saveName(null);
        saveGender(null);
        saveAge(null);
        saveLocation(null);
        saveAbout(null);
        saveEmail(null);
        saveUsername(null);
        saveFacebookAccessToken(null);
        saveFacebookUserId(null);
        saveResult(null);
    }

    public String getUserToken() {
        return getPref(USER_TOKEN);
    }

    public void saveUserToken(String token) {
        savePref(USER_TOKEN, token);
    }

    public String getUserId() {
        return getPref(USER_ID);
    }

    public void saveUserId(String id) {
        savePref(USER_ID, id);
    }

    public String getPhoneNumber() {return getPref(PHONE_NUMBER);}

    public void savePhoneNumber(String num) {
        savePref(PHONE_NUMBER, num);
    }

    public String getCountryCode() {return getPref(COUNTRY_CODE);}

    public void saveCountryCode(String countryCode) {
        savePref(COUNTRY_CODE, countryCode);
    }

    public String getProfilePic() {
        return getPref(PROFILE_PIC);
    }

    public void saveProfilePic(String profile_pic) {
        savePref(PROFILE_PIC, profile_pic);
    }

    public String getName() {return getPref(NAME);}

    public void saveName(String name) {
        savePref(NAME, name);
    }

    public String getGender() {
        return getPref(GENDER);
    }

    public void saveGender(String gender) {
        savePref(GENDER, gender);
    }

    public String getLocation() {
        return getPref(LOCATION);
    }

    public void saveLocation(String location) {
        savePref(LOCATION, location);
    }

    public String getAge() {
        return getPref(AGE);
    }

    public void saveAge(String age) {
        savePref(AGE, age);
    }

    public String getAbout() {
        return getPref(ABOUT);
    }

    public void saveAbout(String about) {
        savePref(ABOUT, about);
    }

    public String getEmail() {
        return getPref(EMAIL);
    }

    public void saveEmail(String email) {
        savePref(EMAIL, email);
    }

    public String getUsername() {
        return getPref(USERNAME);
    }

    public void saveUsername(String username) {
        savePref(USERNAME, username);
    }

    public String getFCMToken() {
        return getPref(FCM_TOKEN);
    }

    public void saveFCMToken(String userId) {
        savePref(FCM_TOKEN, userId);
    }

    public boolean getNotification(){
        return getPref(NOTIFICATION, true);
    }

    public void saveDates(boolean dates){
        savePref(DATES, dates);
    }

    public boolean getDates(){
        return getPref(DATES, true);
    }

    public void saveNotification(boolean notification){
        savePref(NOTIFICATION, notification);
    }

    public boolean getfilter(){
        return getPref(filter, false);
    }

    public void savefilter(boolean b){
        savePref(filter, b);
    }



    public boolean getSound(){
        return getPref(SOUND, true);
    }

    public void saveSound(boolean sound){
        savePref(SOUND, sound);
    }

    public String getFacebookAccessToken() {
        return getPref(FACEBOOK_ACCESS_TOKEN);
    }

    public void saveFacebookAccessToken(String accessToken) {
        savePref(FACEBOOK_ACCESS_TOKEN, accessToken);
    }

    public boolean getProfileCompltd() {
        return getPref(HOMECMPLTD, false);
    }

    public void saveProfileCompltd(boolean b) {
        savePref(HOMECMPLTD, b);
    }

    public String getFacebookUserId() {
        return getPref(FACEBOOK_USER_ID);
    }

    public void saveFacebookUserId(String userId) {
        savePref(FACEBOOK_USER_ID, userId);
    }

    public void saveResult(String result){
        savePref(RESULT, result);
    }

    public String getResult(){
        return getPref(RESULT);
    }

    public void saveIsResultCompleted(boolean b){
        savePref(RESULT_CMLTD, b);
    }

    public boolean getIsResultCompltd(){
        return getPref(RESULT_CMLTD, false);
    }

    public Date getCreatedAt(){
        long millis = getPref(CREATED_AT);
        return new Date(millis);
    }

    public void saveCreatedAt(Date dob){
        savePref(CREATED_AT, dob.getTime());
    }

    public List<String> getQuestion(){
        List<String> savedList = new ArrayList<>();
        String savedString = getPref(QUESTION);
        if(savedString == null){
            return savedList;
        }
        String[] st = savedString.split(",");
        savedList.addAll(Arrays.asList(st));
        return savedList;
    }

    public void saveQuestion(List<String> data){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            str.append(data.get(i)).append(",");
        }
        String retVal = null;
        if(data.size() > 0){
            retVal = str.substring(0, str.length() - 1);
        }
        savePref(QUESTION, retVal);
    }

    public String getMatchgender() {
        return getPref(MATCHGENDER);
    }

    public void saveMatchGender(String matchgender) {
        savePref(MATCHGENDER, matchgender);
    }

    public boolean getPaidQuiz(){
        return getPref(PAID_QUIZ, false);
    }

    public void savePaidQuiz(boolean b){
        savePref(PAID_QUIZ, b);
    }

    public List<InterestModel> getInterests(){
        if (getPref(INTERESTS) == null){
            return null;
        }
        Gson gson = new Gson();
        List<InterestModel> list;

        String string = getPref(INTERESTS);
        Type type = new TypeToken<List<InterestModel>>() {
        }.getType();
        list = gson.fromJson(string, type);
        return list;
    }

    public void saveInterests(List<InterestModel> list){
            setList(INTERESTS, list);
    }

}
