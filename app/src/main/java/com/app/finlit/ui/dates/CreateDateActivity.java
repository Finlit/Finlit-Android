package com.app.finlit.ui.dates;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finlit.R;
import com.app.finlit.data.models.CancelDatesModel;
import com.app.finlit.data.models.DateStatusModel;
import com.app.finlit.data.models.UserLocationdateModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.dates.contract.CancelDatesContract;
import com.app.finlit.ui.dates.contract.DatesContract;
import com.app.finlit.ui.dates.presenter_impl.DatesPresenterImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.app.finlit.ui.dates.FieldSelector.getPlaceFields;

public class CreateDateActivity extends BaseActivity implements DatesContract.View, CancelDatesContract.View {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 23487;

    CancelDatesModel model;
    @BindView(R.id.tv_rightsend)
    public TextView tv_send;

    @BindView(R.id.iv_my_side)
    public ImageView iv_MySide;

    @BindView(R.id.iv_your_side)
    public ImageView iv_YourSide;

    @BindView(R.id.dateselect)
    public TextView tv_datetTime;

    @BindView(R.id.name1)
    public TextView tv_name;

    @BindView(R.id.choose_location)
    public TextView tv_Location;

    @BindView(R.id.tv_heading)
    public TextView tv_Heading;


    private SimpleDateFormat simpleDateFormat;
    private DatesContract.Presenter presenter;


    private DateStatusModel dateStatusModel;
    private SharedPreferenceHelper preferenceHelper;
    private int requestCode;
    private int resultCode;
    private Intent intent;

    String locationValue = "";
    String timeValue = "";
    String date = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_create_date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Filters.getInstance() != null)
            Filters.getInstance().reset();

        String apiKey = "AIzaSyCYO5grDXQW98BM5pa_35o8_tqskspFwOo";

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        presenter = new DatesPresenterImpl(this);
        simpleDateFormat = new SimpleDateFormat();
        preferenceHelper = SharedPreferenceHelper.getInstance();
        dateStatusModel = (DateStatusModel) getIntent().getSerializableExtra(Constants.DATE_STATUS_MODEL);
        iniFields();


//        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "Segoe_Regular.ttf");
        //     tv_Location.setTypeface(typeface);


    }


    @OnClick(R.id.ll_select_date)
    public void onClickDateTime() {
        new SingleDateAndTimePickerDialog.Builder(this)
                .backgroundColor(Color.WHITE)
                .mainColor(Color.GRAY)
                .title("Select Date & Time")
                .titleTextColor(Color.WHITE)
                .mustBeOnFuture()


                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDateSelected(Date date) {
                        //tv_datetTime.setText(simpleDateFormat.format(date));
                        tv_datetTime.setText(simpleDateFormat.format(date));
                       /* if(tv_datetTime.getText().toString()!=null && tv_Location.getText().toString()!=null){
                            tv_send.setBackgroundColor(R.color.pink);
                        }*/


                        timeValue = "abcd";

                        checkDataInserted();


                    }
                })
                .display();

    }

    private void checkDataInserted() {

        if ((!locationValue.equalsIgnoreCase("")) && (!timeValue.equalsIgnoreCase(""))) {
            tv_send.setBackgroundColor(getResources().getColor(R.color.pink));
            onClickSend();
        }

    }

    @OnClick(R.id.ll_location)
    public void onClickLocation() {

        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, getPlaceFields())
                .setInitialQuery("")
                .setCountry("")
                .build(CreateDateActivity.this);
        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(intent);
                tv_Location.setSelected(true);
                tv_Location.setText(place.getAddress());
                locationValue = place.getAddress();
                checkDataInserted();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(intent);
                //responseView.setText(status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        super.onActivityResult(requestCode, resultCode, intent);
    }



    private void onClickSend() {

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateStatusModel statusModel=new DateStatusModel();
//                try {
//                    Date d=new SimpleDateFormat("dd/MM/yy hh:mm aa").parse(tv_datetTime.getText().toString());
//                    String abc=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS zzz").format(d);
//                    Log.e("abc",abc);
//
//                    StatusModel.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS zzz").parse(abc));
//                    Log.e("abc",StatusModel.getUpdatedAt().toString());
//                } catch (ParseException e) {
//                    Log.e("test",e.toString());
//                }
                statusModel.setDate(tv_datetTime.getText().toString());
                UserLocationdateModel locationdateModel=new UserLocationdateModel();
                locationdateModel.setAddress(tv_Location.getText().toString());
                statusModel.setUserlocation(locationdateModel);
                presenter.SendDatesRequest(dateStatusModel.getId(), statusModel);
                preferenceHelper.savefilter(true);
                finish();
            }
        });


    }


    @OnClick(R.id.iv_left)
    public void onClickBack() {
        onBackPressed();
    }


    private void iniFields() {
        hideProgress();
        if (dateStatusModel.getImgUrl() != null) {
            displayImageIcon(dateStatusModel.getImgUrl());
        }

        tv_Heading.setText("MEET " + dateStatusModel.getName());
        if (preferenceHelper.getProfilePic() != null) {
            displayImageIcons(preferenceHelper.getProfilePic());

        }
        tv_name.setText("Suggest a time and place to meet, " +
                "We will notify you if "+dateStatusModel.getName()
                +" confirms");
    }


    private void displayImageIcon(String attachUrl) {
        ImageLoader.getInstance().displayImage(attachUrl, iv_YourSide,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }

    private void displayImageIcons(String attachUrl) {
        ImageLoader.getInstance().displayImage(attachUrl, iv_MySide,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
    }


    @Override
    public void onSuccessDates(PageResponse<DateStatusModel> response) {

    }

    @Override
    public void onSuccessIntrest(DataResponse response) {

    }

    @Override
    public void onSuccessSendDatesRequest(DataResponse response) {

        if(isFinishing()){
            return;
        }
    }

    @Override
    public void onSuccessConfirm(DataResponse response) {

    }


    @Override
    public void onSuccessCancelDates(DataResponse response) {


    }

    @Override
    public void onSuccessEditDates(DataResponse response) {


    }

    @Override
    public void onFailure(String error) {

    }
}
