package com.app.finlit.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isAgeValid(String age) {
        String exp = "^(18?[1-9]|[1-9][0-9]|[1][1-9][1-9]|100)$";
        Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(age);
        return matcher.matches();
    }

    public static int getSpinnerIndex(Spinner spinner, String string) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String getImageUrlFromRef(String ref) {

        String url = "https://maps.googleapis.com/maps/api/place/photo?";
        url = url.concat("maxwidth=400&");
        url = url.concat("key=AIzaSyCMw89JDLpCBdiRGkbjE6_zVK1MQzmfpa4&");
        url = url.concat("photoreference="+ref);
        return url;
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

//    public static LatLng getLatLng(Double[] latLng){
//        String ltlng = Arrays.toString(latLng);
//        String string = ltlng.substring(1, ltlng.length() - 1);
//        String[] latlong = string.split(",");
//        double longitude = Double.parseDouble(latlong[0]);
//        double latitude = Double.parseDouble(latlong[1]);
//        return new LatLng(latitude, longitude);
//    }

//    protected Boolean isActivityRunning(Class activityClass)
//    {
//        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
//
//        for (ActivityManager.RunningTaskInfo task : tasks) {
//            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
//                return true;
//        }
//
//        return false;
//    }

//    public boolean isServiceRunning() {
//
//        ActivityManager activityManager = (ActivityManager)Monitor.this.getSystemService (Context.ACTIVITY_SERVICE);
//        List<RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
//        isServiceFound = false;
//        for (int i = 0; i < services.size(); i++) {
//            if (services.get(i).topActivity.toString().equalsIgnoreCase("ComponentInfo{com.lyo.AutoMessage/com.lyo.AutoMessage.TextLogList}")) {
//                isServiceFound = true;
//            }
//        }
//        return isServiceFound;
//    }

    public static int getTotalPages(int total, int perPage){
        if (total == 0 && perPage == 0){
            return  0;
        }
        return (total + perPage - 1) / perPage;
    }
}