package com.app.finlit.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.app.finlit.ui.nearby.adapter.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by MANISH-PC on 10/16/2018.
 */

public class AutoSearchLocation implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    public interface OnCompleteListner{
        void onSuccess(Location location, String city);

        void onFailure();
    }

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private OnCompleteListner onCompleteListner;
    private Context context;
    private AutoCompleteTextView etLocation;

    private Location location;

    public AutoSearchLocation(Context context, OnCompleteListner onCompleteListner, AutoCompleteTextView etLocation) {
        this.onCompleteListner = onCompleteListner;
        this.context = context;
        this.etLocation = etLocation;
    }

    public void initAutoCmplt() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage((FragmentActivity) context, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        etLocation.setThreshold(1);
        etLocation.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(context, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        etLocation.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            //Log.i(LOG_TAG, "Selected: " + item_demo.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            //Log.i(LOG_TAG, "Fetching details for ID: " + item_demo.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            try {
                if (!places.getStatus().isSuccess()) {
                    //Log.e(LOG_TAG, "Place query did not complete. Error: " +
                    //      places.getStatus().toString());
                    onCompleteListner.onFailure();
                    return;
                }

                // Selecting the first object buffer.
                final Place place = places.get(0);
                LatLng latLng = place.getLatLng();
                location = new Location("");
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                onCompleteListner.onSuccess(location, String.valueOf(place.getAddress()));
                //city = "" + place.getAddress();
                Log.d("+++", "name" + place.getAddress());
            }catch (Exception e){e.printStackTrace();}
        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        onCompleteListner.onFailure();
    }




}
