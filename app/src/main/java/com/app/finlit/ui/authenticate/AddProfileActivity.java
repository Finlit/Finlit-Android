package com.app.finlit.ui.authenticate;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.finlit.R;
import com.app.finlit.data.models.InterestModel;
import com.app.finlit.data.models.LocationModel;
import com.app.finlit.data.models.LocationdateModel;
import com.app.finlit.data.models.OptionsModel;
import com.app.finlit.data.models.UserLocationdateModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.params.ImageModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.adapter.InterestAdapter;
import com.app.finlit.ui.authenticate.adapter.OptionsAdapter;
import com.app.finlit.ui.authenticate.adapter.SpinnerAdapter;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.contract.ImageContract;
import com.app.finlit.ui.authenticate.presentor_impl.UserApiPresenterImpl;
import com.app.finlit.ui.authenticate.presentor_impl.ImagePresenterImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.chat.ChatActivity;
import com.app.finlit.ui.dates.CreateDateActivity;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.AutoSearchLocation;
import com.app.finlit.utils.LocationUtils;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.app.finlit.utils.Utility;
import com.app.finlit.utils.image.ImageFilePath;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.app.finlit.utils.listeners.OnActionListener;
import com.cloudinary.utils.StringUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


import static com.app.finlit.ui.authenticate.FieldSelector.getPlaceFields;
import static com.app.finlit.utils.Utility.hideSoftKeyboard;

@RuntimePermissions
public class AddProfileActivity extends BaseActivity implements UserApiContract.View,AdapterView.OnItemSelectedListener,
        ImageContract.View, AutoSearchLocation.OnCompleteListner,NumberPicker.OnValueChangeListener{
    private static final int AUTOCOMPLETE_REQUEST_CODE = 23487;
    @BindView(R.id.iv_left)
    public ImageView ivLeft;

    @BindView(R.id.tv_heading)
    public TextView txtHeading;

    @BindView(R.id.et_name)
    public EditText etName;

    @BindView(R.id.et_add_profile_password)
    public EditText etPassword;

    @BindView(R.id.et_about_you)
    public EditText etAbout;
//    @BindView(R.id.et_add_profile_username)
//    public EditText etUsername;
    @BindView(R.id.et_age)
    public EditText etAge;

    @BindView(R.id.spinner_add_profile_gender)
    public Spinner spinnerGender;

    @BindView(R.id.et_interest)
    public EditText etInterest;


    @BindView(R.id.et_add_profile_location)
    public EditText etLocation;

    @BindView(R.id.txt_add_profile_proceed)
    public TextView txtProceed;

    @BindView(R.id.txt_add_profile_options)
    public TextView tvOptions;

    @BindView(R.id.lay_add_profile_main)
    public RelativeLayout layMain;

    @BindView(R.id.lay_add_profile_top)
    public RelativeLayout relProfileTop;

    @BindView(R.id.lay_interest)
    public RelativeLayout relativeLayoutInterest;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @BindView(R.id.iv_add_profile_background)
    public ImageView ivBackground;
    @BindView(R.id.iv_mask)
    public ImageView ivMask;
    @BindView(R.id.iv_add_profile_dp)
    public ImageView ivProfile;

    @BindView(R.id.lay_add_profile_password)
    public LinearLayout linearLayoutPassword;
//    @BindView(R.id.lay_add_profile_username)
//    public LinearLayout linearLayoutUsername;

    private SharedPreferenceHelper preferenceHelper;
    private UserApiContract.Presentor presentor;
    private ImageContract.Presenter imagePresenter;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int RequestPermissionCode = 1;
    private Uri imageUri;
    private Uri capturedImageURI;
    private Uri imgUri=null;
    private String imagePath;

    private Geocoder geocoder;
    private Location location;
    private List<Address> addresses;
    private LatLng latLngSource;

    private LocationModel locationModel;

    private String gender, option,fromData;
    private UserModel userModel;

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private int mYear, mMonth, mDay, mHour, mMinute;

    private Dialog dialog= null;
    private RecyclerView recyclerViewOptions, recyclerViewInterest;
    private TextView txtDone, txtCancel, tvDone, tvCancle;
    private OptionsAdapter optionsAdapter;
    private List<OptionsModel> listOptions;
    private OnActionListener<OptionsModel> onActionListenerSelected;
    private OnActionListener<OptionsModel> onActionListenerUnSelected;
    private List<OptionsModel> selectedList;
    private List<String> stringsList;

    private List<InterestModel> interestList, selectedInterest;

    private LinearLayout linearLayout;


    public static void start(Context context) {
        Intent intent = new Intent(context, AddProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_add_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String apiKey = "AIzaSyCYO5grDXQW98BM5pa_35o8_tqskspFwOo";

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        super.onCreate(savedInstanceState);
        etLocation.setEnabled(false);
        preferenceHelper = SharedPreferenceHelper.getInstance();
        presentor = new UserApiPresenterImpl(this);
        imagePresenter = new ImagePresenterImpl(this);
        selectedList = new ArrayList<>();
        stringsList = new ArrayList<>();
        selectedInterest = new ArrayList<>();
        locationModel= new LocationModel();
        fromData = getIntent().getStringExtra("UniqId");
        layMain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard(AddProfileActivity.this);
                }
            }
        });
        initToolbar();
        initSpinnerGender();
        initFields();
        showProfilePic();

      //  initAutoComplete();

    }

//    private void initAutoComplete() {
//        Typeface typeface= Typeface.createFromAsset(this.getAssets(), "euphemia_regular.ttf");
//        etLocation.setTypeface(typeface);
//        new AutoSearchLocation(this, this, etLocation).initAutoCmplt();
//    }

    private void initFields() {
        if (fromData!=null){
            relativeLayoutInterest.setVisibility(View.VISIBLE);
            txtHeading.setText("EDIT PROFILE");
            showProfilePic();
            etName.setText(preferenceHelper.getName());
            etAbout.setText(preferenceHelper.getAbout());
            etLocation.setText(preferenceHelper.getLocation());
            etAge.setText(preferenceHelper.getAge());
            linearLayoutPassword.setVisibility(View.GONE);
//            linearLayoutUsername.setVisibility(View.GONE);
            if (preferenceHelper.getQuestion()!=null){
                stringsList = preferenceHelper.getQuestion();
                tvOptions.setText(StringUtils.join(stringsList, ",\n"));
            }
            if (preferenceHelper.getInterests()!=null && !preferenceHelper.getInterests().isEmpty()) {
                selectedInterest.addAll(preferenceHelper.getInterests());
                etInterest.setText("Interests Selected");
            }
        }else {
            relativeLayoutInterest.setVisibility(View.GONE);
        }

    }

    private void showProfilePic() {
        if (preferenceHelper.getProfilePic()!=null){
            ivMask.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(preferenceHelper.getProfilePic(), ivProfile,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
            ImageLoader.getInstance().displayImage(preferenceHelper.getProfilePic(), ivBackground,
                    ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
            imagePath = preferenceHelper.getProfilePic();
        }
        if (preferenceHelper.getName()!=null){
            etName.setText(preferenceHelper.getName());
        }
        if(preferenceHelper.getAge()!=null){
            etAge.setText(preferenceHelper.getAge());
        }
    }

    @OnClick({R.id.et_age, R.id.iv_add_profile_age})
    public void onClickAge(){
        show();
    }

    public void show() {
        dialog = new Dialog(this);
        dialog.setTitle("Age Picker");
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_age);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Button b1 = dialog.findViewById(R.id.button1);
        Button b2 = dialog.findViewById(R.id.button2);
        final NumberPicker np = dialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(18);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAge.setText(String.valueOf(np.getValue()));
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void initToolbar() {
        txtHeading.setText("CREATE PROFILE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }

    private void initSpinnerGender() {
        String[] providers = getResources().getStringArray(R.array.gender);
        SpinnerAdapter adapter = new SpinnerAdapter(this, Arrays.asList(providers));
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(this);

        if (fromData!=null){
            int pos = 0;
            for (int i = 0; i < providers.length; i++) {
                if (providers[i].toLowerCase().equals(preferenceHelper.getGender())){
                    pos = i;
                    break;
                }
            }
            spinnerGender.setSelection(pos);
        }
    }

    @OnClick(R.id.lay_add_profile_location)
    public void onClickLocation(){

        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, getPlaceFields())
                .setInitialQuery("")
                .setCountry("")
                .build(AddProfileActivity.this);
        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);

         //  AddProfileActivityPermissionsDispatcher.getLocationWithPermissionCheck(this);
    }



//    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//
//
//    public void getLocation() {
//        LocationUtils.askToTurnOnLocation(this);
//        SmartLocation.with(this).location()
//                .oneFix()
//                .start(new OnLocationUpdatedListener() {
//                    @Override
//                    public void onLocationUpdated(Location location) {
//                        AddProfileActivity.this.location = location;
//                        if (location != null) {
//                            geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
//                        }
//                        try {
//                            assert location != null;
//                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            double[] latlng = new double[2];
//                            latlng[0] = location.getLongitude();
//                            latlng[1] = location.getLatitude();
//                            locationModel.setCoordinates(latlng);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (addresses != null) {
//                            //String address = addresses.get(0).getAddressLine(0);
//                            String address = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
//                            // Log.e("testlocation",address);
//                            etLocation.setText(address);
//
//                            locationModel.setAddress(address);
//                        }
//                    }
//                });


    @OnClick(R.id.iv_add_profile_dp)
    public void onClickDp(){
        AddProfileActivityPermissionsDispatcher.selectImageWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddProfileActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "temp");
        capturedImageURI = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageURI);
        startActivityForResult(intentPicture, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
            {
                onCaptureImageResult(data);
            }
        }
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ivMask.setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.iv_add_profile_dp)).setImageURI(result.getUri());
                ((ImageView) findViewById(R.id.iv_add_profile_background)).setImageURI(result.getUri());
                imageUri = result.getUri();
                imagePath= ImageFilePath.getPath(this, imageUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                etLocation.setSelected(true);
                etLocation.setText(place.getAddress());
                latLngSource = place.getLatLng();

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //responseView.setText(status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onCaptureImageResult(Intent data) {

        startCropImageActivity(capturedImageURI);
    }

    private void onSelectFromGalleryResult(Intent data) {

        imgUri = data.getData();
        assert imgUri != null;
        imagePath = ImageFilePath.getPath(this, imgUri);
        startCropImageActivity(imgUri);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setFixAspectRatio(true)
                .setAspectRatio(1,1)
                .start(this);
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @OnClick(R.id.txt_add_profile_proceed)
    public void onClickProceed(){

        if (!validate()){
            return;
        }

        UserModel userModel = new UserModel();

        userModel.setName(etName.getText().toString());
        userModel.setAboutUs(etAbout.getText().toString());
        userModel.setGender(gender.toLowerCase());
        userModel.setAgeGroup(Integer.valueOf(etAge.getText().toString()));
        userModel.setQuestion(stringsList);
        if (!selectedInterest.isEmpty()){
            userModel.setInterest(selectedInterest);
        }

        if (imagePath!=null && imagePath.contains("http"))
            userModel.setImgUrl(imagePath);
        if (fromData == null) {
            userModel.setPassword(etPassword.getText().toString());
            preferenceHelper.saveProfileCompltd(true);
        }
//        if (locationModel!=null){
//            if(locationModel.getAddress()!=null){
//                userModel.setLocation(locationModel);
//            }
//        } else if (!etLocation.getText().toString().trim().isEmpty()){
//            locationModel = new LocationModel();
//            locationModel.setAddress(etLocation.getText().toString());
//            userModel.setLocation(locationModel);
//        }

        LocationModel model=new LocationModel();
        double[] latlng = new double[2];
        if (latLngSource != null) {
            latlng[0] = latLngSource.longitude;
            latlng[1] = latLngSource.latitude;
            model.setCoordinates(latlng);
        }else{
            ToastUtils.longToast("Destination Co-ordinates Missing");
            return;
        }
        model.setAddress(etLocation.getText().toString());
        userModel.setLocation(model);
        showProgress();
        presentor.addProfile(userModel);
    }


    @OnEditorAction({R.id.et_add_profile_location, R.id.et_age, R.id.et_add_profile_password})
    public boolean onEditorActionLocation(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_DONE:
                if (etLocation.hasFocus())
                    etLocation.clearFocus();
                if (etAge.hasFocus())
                    etAge.clearFocus();
                if (etPassword.hasFocus()){
                    etPassword.clearFocus();
                    onClickProceed();
                }

                hideKeyboard(getCurrentFocus());
                return true;

        }

        return false;
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty()){
            etName.requestFocus();
            etName.setError("Enter name");
            return false;
        }

        if (etName.getText().toString().length()<2){
            etName.requestFocus();
            etName.setError("Enter valid name");
            return false;
        }
        if (etAbout.getText().toString().isEmpty()){
            etAbout.requestFocus();
            etAbout.setError("Add something about you");
            return false;
        }
        if (etAge.getText().toString().isEmpty()){
            ToastUtils.shortToast("Select age");
            return false;
        }

        if (gender == null){
            ToastUtils.shortToast("Select gender");
            return false;
        }

        if (stringsList.isEmpty()){
            ToastUtils.shortToast("Select category");
            return false;
        }
        if (fromData == null && etPassword.getText().toString().isEmpty()){
            etPassword.requestFocus();
            etPassword.setError("Enter password");
            return false;
        }

        if (fromData == null && etPassword.getText().toString().length()<6){
            etPassword.setError("Password should be of atleast 6 characters");
            etPassword.requestFocus();
            return false;
        }

//        if (fromData == null && etUsername.getText().toString().isEmpty()){
//            etUsername.requestFocus();
//            etUsername.setError("Enter username");
//            return false;
//        }

        if (imagePath == null){
            ToastUtils.shortToast("Select Profile Image");
            return false;
        }
        if (Integer.valueOf(etAge.getText().toString())<10){
            ToastUtils.shortToast("Minimum Age should be 10");
            return false;
        }
        return true;
    }

    @Override
    public void onSuccessAddProfile(DataResponse<UserModel> response) {
        if(isFinishing()){
            return;
        }
        if (imagePath!=null && !imagePath.contains("http")){
            imagePresenter.uploadImage(preferenceHelper.getUserId(), imagePath);
            return;
        }
        nextScreen();
    }

    @Override
    public void onSuccessUpdatePassword(DataResponse<UserModel> response) {

    }

    @Override
    public void onSuccess(DataResponse<ImageModel> response) {
        if (isFinishing()){
            return;
        }
        nextScreen();

    }

    @Override
    public void onFailure(String error) {
        if(isFinishing()){
            return;
        }
        hideProgress();
        ToastUtils.longToast(error);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_add_profile_gender:
                String gender = (String) parent.getItemAtPosition(position);
                if (gender.equalsIgnoreCase("Gender")){
                    return;
                }
                this.gender = gender;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void nextScreen(){
        hideProgress();
        preferenceHelper.saveProfileCompltd(true);
       // if (fromData!=null)
            MainActivity.start(this);
       // else
          //  WelcomeActivity.start(this);
    }

    @Override
    public void onSuccess(Location location, String city) {
        if (isFinishing()){
            return;
        }
        hideKeyboard(getCurrentFocus());
        etLocation.clearFocus();
        double[] latlng = new double[2];
        latlng[0] = location.getLongitude();
        latlng[1] = location.getLatitude();
        locationModel.setAddress(city);
        locationModel.setCoordinates(latlng);

    }

    @Override
    public void onFailure() {

    }

    @OnClick({R.id.txt_add_profile_options, R.id.iv_add_profile_email})
    public void onClickOptions(){
        selectedList.clear();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_options);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        recyclerViewOptions = dialog.findViewById(R.id.recycler_options);
        txtDone= dialog.findViewById(R.id.txt_dialog_done);
        txtCancel = dialog.findViewById(R.id.txt_dialog_cancel);
        initListener();
        initDialogOptions();
    }

    private void initListener() {
        onActionListenerSelected = new OnActionListener<OptionsModel>() {
            @Override
            public void notify(OptionsModel model, int position) {
                selectedList.add(model);
            }
        };

        onActionListenerUnSelected = new OnActionListener<OptionsModel>() {
            @Override
            public void notify(OptionsModel model, int position) {
                selectedList.remove(model);
            }
        };

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedList.isEmpty()){
                    ToastUtils.shortToast("Select Options");
                    return;
                }
                stringsList .clear();
                for (OptionsModel optionsModel : selectedList){
                    stringsList.add(optionsModel.getName());
                }
                tvOptions.setText("");
                tvOptions.setText(StringUtils.join(stringsList, ",\n"));
                dialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedList.clear();
                dialog.dismiss();
            }
        });
    }

    private void initDialogOptions() {
        listOptions= new ArrayList<>();
        OptionsModel op1= new OptionsModel();
        op1.setId("1");
        op1.setName("Credit Card Churning");
        op1.setSelected(false);

        OptionsModel op2= new OptionsModel();
        op2.setId("2");
        op2.setName("Stock Trading");
        op2.setSelected(false);

        OptionsModel op3= new OptionsModel();
        op3.setId("3");
        op3.setName("Real Estate");
        op3.setSelected(false);

        OptionsModel op4= new OptionsModel();
        op4.setId("4");
        op4.setName("Retirement Planning");
        op4.setSelected(false);

        OptionsModel op5= new OptionsModel();
        op5.setId("5");
        op5.setName("Budget Planning");
        op5.setSelected(false);

        OptionsModel op6= new OptionsModel();
        op6.setId("6");
        op6.setName("Personal Investment");
        op6.setSelected(false);

        OptionsModel op7= new OptionsModel();
        op7.setId("7");
        op7.setName("Futures/Forex Trading");
        op7.setSelected(false);

        OptionsModel op8= new OptionsModel();
        op8.setId("8");
        op8.setName("CryptoCurrency Trading");
        op8.setSelected(false);

        OptionsModel op9 = new OptionsModel();
        op9.setId("9");
        op9.setName("Vacation planning");
        op9.setSelected(false);

        listOptions.add(op1);
        listOptions.add(op2);
        listOptions.add(op3);
        listOptions.add(op4);
        listOptions.add(op5);
        listOptions.add(op6);
        listOptions.add(op7);
        listOptions.add(op8);
        listOptions.add(op9);

        for (OptionsModel optionsModel : listOptions){
            for (String string : stringsList){
                if (string.equalsIgnoreCase(optionsModel.getName())){
                    optionsModel.setSelected(true);
                    break;
                }
            }
        }

        optionsAdapter= new OptionsAdapter(this, listOptions,onActionListenerSelected,onActionListenerUnSelected);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOptions.setLayoutManager(gridLayoutManager);
        recyclerViewOptions.setAdapter(optionsAdapter);
        recyclerViewOptions.setNestedScrollingEnabled(false);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//        etAge.setText(String.valueOf(newVal));
        etAge.clearFocus();
    }

    @OnClick(R.id.lay_interest)
    public void onClickInterest(){
        initDialogeInterest();
    }

    private void initDialogeInterest() {
        interestList = new ArrayList<>();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width,height);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialoge_interest);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        recyclerViewInterest = dialog.findViewById(R.id.recycler_interest);
        tvDone= dialog.findViewById(R.id.txt_dialog_done);
        tvCancle = dialog.findViewById(R.id.txt_dialog_cancel);
        linearLayout = dialog.findViewById(R.id.linear);
        initListenerInterest();
        initDialogInterest();
    }

    private void initListenerInterest() {
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (InterestModel model : interestList){
                    if (model.isSelected){
                        selectedInterest.add(model);
                    }
                }
                etInterest.setText("Interests Selected");
                dialog.dismiss();
            }
        });

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void initDialogInterest(){
        interestList.clear();
        String[] providers = getResources().getStringArray(R.array.interest);

        List<String> stringListInterest = new ArrayList<>();
        stringListInterest.addAll(Arrays.asList(providers));


        for (int i = 0; i<14; i++){
            InterestModel model = new InterestModel();
            model.setQuestion(stringListInterest.get(i));
            interestList.add(model);
        }

        if (selectedInterest.size()>0){
            for (InterestModel model : selectedInterest){

                for (InterestModel innerModel : interestList){
                    if (model.getQuestion().equals(innerModel.getQuestion())){
                        innerModel.setAnswer(model.getAnswer());
                        innerModel.setSelected(true);
                        break;
                    }
                }
            }
        }
        InterestAdapter interestAdapter= new InterestAdapter(this, interestList, getCurrentFocus(), this);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclerViewInterest.setLayoutManager(gridLayoutManager);
        recyclerViewInterest.setAdapter(interestAdapter);
        recyclerViewInterest.setNestedScrollingEnabled(false);
    }
}
