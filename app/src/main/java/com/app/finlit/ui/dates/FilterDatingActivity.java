package com.app.finlit.ui.dates;

import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.utils.Filters;
import com.app.finlit.utils.SharedPreferenceHelper;

import butterknife.BindView;
import butterknife.OnClick;
import io.apptik.widget.MultiSlider;

public class FilterDatingActivity extends BaseActivity   {

    @BindView(R.id.tv_heading)
    public TextView tv_heading;

    @BindView(R.id.seekbar_range)
    public MultiSlider seekBarRange;

    @BindView(R.id.tv_range)
    public TextView tvRange;

    int range = 0;

    @BindView(R.id.seekbar_age)
    public MultiSlider seekBarAge;
    @BindView(R.id.minValue)
    public TextView tvAgeMinRange;
    @BindView(R.id.maxValue)
    public TextView tvMaxRange;
    private Location location;
    private SharedPreferenceHelper preferenceHelper;


    @Override
    protected int getContentId() {
        return R.layout.activity_filter_dating;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniListener();
        iniFields();
        setTv_heading();
        Filters.init();
        preferenceHelper=SharedPreferenceHelper.getInstance();
    }

    private void iniFields() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarRange.setMin(0);
        }
        seekBarRange.setMax(5000);
    }

    private void iniListener() {
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

    }



    public void setTv_heading(){
        tv_heading.setText("FILTER");
    }

    @OnClick(R.id.iv_left)
    public void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.iv_right_tick)
    public void onClickFilter(){
        Filters.getInstance().reset();
//
//            Filters.getInstance().addFilter("latitude", String.valueOf(location.getLongitude()));
//            Filters.getInstance().addFilter("longitude", String.valueOf(location.getLatitude()));
//            if (tvRange.getText().toString().equals("0 ") || Integer.valueOf(tvRange.getText().toString())<10000)
//                Filters.getInstance().addFilter("range", "10000");
//            else
//                Filters.getInstance().addFilter("range", tvRange.getText().toString());

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
        setResult(RESULT_OK);
        preferenceHelper.savefilter(true);
        finish();
    }
}
