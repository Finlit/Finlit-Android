package com.app.finlit.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.app.finlit.utils.helpers.DatabaseHelper;

import com.app.finlit.utils.image.ImageLoaderUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class App extends MultiDexApplication {

    private static App instance;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        // Branch logging for debugging
        instance = this;
        initDb();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        printKeyHash();
        initImageLoader();
        initSharedHelper();
    }

    private void initImageLoader(){
        ImageLoader.getInstance().init(ImageLoaderUtils.getImageLoaderConfiguration(this));
    }

    private void initSharedHelper() {
        new SharedPreferenceHelper(this);
    }

    public static App getInstance () {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    private void initDb() {
        DatabaseHelper.init();
    }

    public DatabaseReference getDatabase() {
        return mDatabase;
    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.app.finlit", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("VIVZ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


}
