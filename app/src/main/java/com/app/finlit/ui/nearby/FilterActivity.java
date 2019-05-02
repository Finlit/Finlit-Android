package com.app.finlit.ui.nearby;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finlit.R;
import com.app.finlit.data.models.LocationModel;
import com.app.finlit.ui.authenticate.AddProfileActivity;
import com.app.finlit.ui.authenticate.SignInActivity;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.dates.CreateDateActivity;
import com.app.finlit.ui.nearby.adapter.PlaceArrayAdapter;
import com.app.finlit.utils.AutoSearchLocation;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.LocationUtils;
import com.app.finlit.utils.ToastUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.apptik.widget.MultiSlider;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.app.finlit.ui.nearby.FieldSelector.getPlaceFields;
import static com.app.finlit.utils.Utility.hideSoftKeyboard;

@RuntimePermissions
public class FilterActivity extends BaseActivity implements AutoSearchLocation.OnCompleteListner {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 23487;

    @BindView(R.id.iv_left)
    public ImageView ivLeft;
    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.seekbar_range)
    public MultiSlider seekBarRange;
    @BindView(R.id.tv_range)
    public TextView tvRange;

    @BindView(R.id.minValue)
    public TextView tvAgeMinRange;
    @BindView(R.id.maxValue)
    public TextView tvMaxRange;
    @BindView(R.id.seekbar_age)
    public MultiSlider seekBarAge;

    @BindView(R.id.et_search)
    public EditText etSearch;
    @BindView(R.id.et_location)
    public EditText etLocation;

    Double lat, lng;
    @BindView(R.id.tv_all)
    public TextView tvAll;
    @BindView(R.id.tv_real_estate)
    public TextView tvRealEstate;
    @BindView(R.id.tv_retirement)
    public TextView tvRetirement;
    @BindView(R.id.tv_crunccing)
    public TextView tvCrunccing;
    @BindView(R.id.tv_budget)
    public TextView tvBudget;
    @BindView(R.id.tv_personal)
    public TextView tvPersonalInvestment;
    @BindView(R.id.tv_cryptocurrency)
    public TextView tvCryptocurrency;

    @BindView(R.id.container)
    public LinearLayout linearContainer;

    private String question;


    private Geocoder geocoder;
    private List<Address> addresses;
    private Integer mile;
    private Location location;
    int range = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, FilterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_filter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String apiKey = "AIzaSyCYO5grDXQW98BM5pa_35o8_tqskspFwOo";

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
            return;
        }

        // Setup Places Client
        if (!com.google.android.libraries.places.api.Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        etLocation.setEnabled(false);

        initToolbar();
        initFields();
        initListners();
        Filters.init();
    }

    private void initToolbar() {
        tvTitle.setText("FILTERS");
    }

    private void initFields() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarRange.setMin(0);
        }
        seekBarRange.setMax(5000);

//        Typeface typeface= Typeface.createFromAsset(this.getAssets(), "euphemia_regular.ttf");
//        etLocation.setTypeface(typeface);
//
//        new AutoSearchLocation(this, this, etLocation).initAutoCmplt();
    }

    private void initListners() {

        seekBarRange.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                tvRange.setText(String.valueOf(value));
            }
        });

        seekBarAge.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                    thumbIndex, int value) {
                if (thumbIndex == 0) {
                    tvAgeMinRange.setText(String.valueOf(value));
                } else {
                    tvMaxRange.setText(String.valueOf(value));
                }
            }
        });

        linearContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideSoftKeyboard(FilterActivity.this);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @OnClick(R.id.tv_all)
    public void onClickAll() {
        resetSearch();
        tvAll.setTextColor(getResources().getColor(R.color.white));
        tvAll.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
    }

    @OnClick(R.id.tv_real_estate)
    public void onClickRealEstate() {
        resetSearch();
        tvRealEstate.setTextColor(getResources().getColor(R.color.white));
        tvRealEstate.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Real estate";
    }

    @OnClick(R.id.tv_retirement)
    public void onClickRetirement() {
        resetSearch();
        tvRetirement.setTextColor(getResources().getColor(R.color.white));
        tvRetirement.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Retirement planning";
    }

    @OnClick(R.id.tv_crunccing)
    public void onClickCrunncing() {
        resetSearch();
        tvCrunccing.setTextColor(getResources().getColor(R.color.white));
        tvCrunccing.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Credit Card Churning";
    }

    @OnClick(R.id.tv_budget)
    public void onClickBudget() {
        resetSearch();
        tvBudget.setTextColor(getResources().getColor(R.color.white));
        tvBudget.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Budget planning";
    }

    @OnClick(R.id.tv_personal)
    public void onClickPersonalInvestment() {
        resetSearch();
        tvPersonalInvestment.setTextColor(getResources().getColor(R.color.white));
        tvPersonalInvestment.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Personal investment";
    }

    @OnClick(R.id.tv_cryptocurrency)
    public void onClickCryptocurrency() {
        resetSearch();
        tvCryptocurrency.setTextColor(getResources().getColor(R.color.white));
        tvCryptocurrency.setBackground(ContextCompat.getDrawable(this, R.drawable.background_selected_filter));
        question = "Cryptocurrency Trading";
    }

    @OnClick(R.id.tv_apply)
    public void onClickApply() {
        Filters.getInstance().reset();
        if (!etSearch.getText().toString().trim().isEmpty()) {
            Filters.getInstance().addFilter("name", etSearch.getText().toString());
        }
//        if (location != null) {
        // Filters.getInstance().addFilter("location", String.valueOf(location.getLongitude()));

        if (lat != null && lng != null) {
            Filters.getInstance().addFilter("latitude", String.valueOf(lat));
            Filters.getInstance().addFilter("longitude", String.valueOf(lng));
        }

//            if (tvRange.getText().toString().equals("0 ") || Integer.valueOf(tvRange.getText().toString())<10000)
//                Filters.getInstance().addFilter("range", "10000");
//            else
//                Filters.getInstance().addFilter("range", tvRange.getText().toString());


        Log.e("getRange", tvRange.getText().toString());
        if (tvRange.getText().toString().equalsIgnoreCase("0 ")) {
            range = 0;
        } else {
            range = Integer.valueOf(tvRange.getText().toString()) * 1600;
        }
        if (range != 0) {
            Filters.getInstance().addFilter("range", String.valueOf(range));
        }

        Filters.getInstance().addFilter("ageMin", tvAgeMinRange.getText().toString());
        Filters.getInstance().addFilter("ageMax", tvMaxRange.getText().toString());
        if (question != null)
            Filters.getInstance().addFilter("filterBy", question);

        setResult(RESULT_OK);
        finish();
    }


    private void resetSearch() {
        tvAll.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvRealEstate.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvRetirement.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvCrunccing.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvBudget.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvPersonalInvestment.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));
        tvCryptocurrency.setBackground(ContextCompat.getDrawable(this, R.drawable.background_unselected_filter));

        tvAll.setTextColor(getResources().getColor(R.color.text_blue));
        tvRealEstate.setTextColor(getResources().getColor(R.color.text_blue));
        tvRetirement.setTextColor(getResources().getColor(R.color.text_blue));
        tvCrunccing.setTextColor(getResources().getColor(R.color.text_blue));
        tvBudget.setTextColor(getResources().getColor(R.color.text_blue));
        tvPersonalInvestment.setTextColor(getResources().getColor(R.color.text_blue));
        tvCryptocurrency.setTextColor(getResources().getColor(R.color.text_blue));
    }

    @OnClick(R.id.location)
    public void onClickLocation() {
        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, getPlaceFields())
                .setInitialQuery("")
                .setCountry("")
                .build(FilterActivity.this);
        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(intent);
                etLocation.setSelected(true);
                etLocation.setText(place.getAddress());
                lat = (place.getLatLng().latitude);
                lng = (place.getLatLng().longitude);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(intent);
                //responseView.setText(status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        super.onActivityResult(requestCode, resultCode, intent);
    }

    //
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//    public void getLocation() {
//        LocationUtils.askToTurnOnLocation(this);
//        SmartLocation.with(this).location()
//                .oneFix()
//                .start(new OnLocationUpdatedListener() {
//                    @Override
//                    public void onLocationUpdated(Location location) {
//                        if (location != null) {
//                            FilterActivity.this.location = location;
//                            geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
//                        }
//                        try {
//                            assert location != null;
//                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (addresses != null) {
//                            String address = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
//                            etLocation.setText(address);
//                        }
//                    }
//                });


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        FilterActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }

//    @OnEditorAction(R.id.et_search)
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//        switch (actionId) {
//
//            case EditorInfo.IME_ACTION_DONE:
//                etSearch.clearFocus();
//                hideKeyboard(getCurrentFocus());
//                return true;
//        }

    // return false;
//    }

    @Override
    public void onSuccess(Location location, String city) {
        hideKeyboard(getCurrentFocus());
        etLocation.clearFocus();
        this.location = location;
    }

    @Override
    public void onFailure() {

    }

}
