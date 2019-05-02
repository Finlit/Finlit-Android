package com.app.finlit.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.ActivePlanModel;
import com.app.finlit.data.models.SubscriptionModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.home.contract.SubscriptionContract;
import com.app.finlit.ui.home.presentor_impl.SubscriptionPresenterImpl;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.DateHelper;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubscriptionsActivity extends BaseActivity implements SubscriptionContract.View {

    private static final String TAG = SubscriptionsActivity.class.getSimpleName();
    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    @BindView(R.id.tv_price_popular)
    public TextView tvPricepopular;
    @BindView(R.id.tv_per_popular)
    public TextView tvPerPopular;
    @BindView(R.id.tv_price_highdemand)
    public TextView tvPriceHighDemand;
    @BindView(R.id.tv_per_highdemand)
    public TextView tvPerHighDemand;
    @BindView(R.id.tv_price_decent)
    public TextView tvPriceDecent;
    @BindView(R.id.tv_per_decent)
    public TextView tvPerDecent;

    @BindView(R.id.tv_plan_name)
    public TextView tvPlanName;
    @BindView(R.id.tv_expiry_date)
    public TextView tvExpiry;

    private SubscriptionContract.Presenter presenter;
    private SharedPreferenceHelper preferenceHelper;

    private List<SubscriptionModel> list = new ArrayList<>();

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = Constants.CLIENT_ID;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    private String popularPrice, highDemandPrice, decentPrice, subscriptionId;
    private String popularId, highDemandId, decentId;

    public static void start(Context context) {
        Intent intent = new Intent(context, SubscriptionsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_subscriptions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        presenter = new SubscriptionPresenterImpl(this);

        initToolbar();
        initFields();
        initService();
    }

    private void initFields() {
        showProgress();
        presenter.getSubscriptionDetail();
        presenter.getAcivePlan();
    }

    private void initToolbar() {
        tvTitle.setText("SUBSCRIPTIONS");
    }

    private void initService(){
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void setUIData(){
        for (SubscriptionModel subscriptionModel : list){
            switch (subscriptionModel.getName()){
                case "Popular":
                    popularId = subscriptionModel.getId();
                    popularPrice = String.valueOf(subscriptionModel.getPrice());
                    tvPricepopular.setText(popularPrice);
                    tvPerPopular.setText(" "+subscriptionModel.getPer());
                    break;
                case "High Demand":
                    highDemandId = subscriptionModel.getId();
                    highDemandPrice = String.valueOf(subscriptionModel.getPrice());
                    tvPriceHighDemand.setText(highDemandPrice);
                    tvPerHighDemand.setText(" "+subscriptionModel.getPer());
                    break;

                case "Decent":
                    decentId = subscriptionModel.getId();
                    decentPrice = String.valueOf(subscriptionModel.getPrice());
                    tvPriceDecent.setText(decentPrice);
                    tvPerDecent.setText(" "+subscriptionModel.getPer());
                    break;
            }
        }
    }
    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_left)
    public void onClickLeft(){
        onBackPressed();
    }

    @OnClick(R.id.linear_popular)
    public void onClickPopular(){

        ToastUtils.longToast("Development in Progress");
      /*  subscriptionId = popularId;
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, popularPrice);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);*/

        //ToastUtils.shortToast("Under development");
    }

    @OnClick(R.id.linear_high_demand)
    public void onClickHighDemand(){

        ToastUtils.longToast("Development in Progress");
       /* subscriptionId = highDemandId;
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, highDemandPrice);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);*/
        //ToastUtils.shortToast("Under development");
    }

    @OnClick(R.id.linear_decent)
    public void onClickDecent(){
        ToastUtils.longToast("Development in Progress");
      /*  subscriptionId = decentId;
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, decentPrice);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);*/
        //ToastUtils.shortToast("Under development");
    }

    @Override
    public void onSuccess(PageResponse<SubscriptionModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        list.addAll(response.getItems());
        setUIData();
    }

    @Override
    public void onSuccessCreatePlan(DataResponse response) {
        if (isFinishing()){
            return;
        }
        ToastUtils.shortToast("Plan Activated");
        presenter.getAcivePlan();
    }

    @Override
    public void onSuccessActivePlan(DataResponse<ActivePlanModel> response) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        ActivePlanModel model = response.getData();
        if (model!=null){
            tvPlanName.setText(model.getPlan().getName());
            tvExpiry.setText("Event expires on " + DateHelper.getDate(model.getExpiryDate()));
        }

    }

    @Override
    public void onFailure(String message) {
        if (isFinishing()){
            return;
        }
        hideProgress();
        //if (message == null || message.isEmpty())
        //ToastUtils.shortToast(message);
    }

    private PayPalPayment getThingToBuy(String paymentIntent, String price) {
        return new PayPalPayment(new BigDecimal(price), "USD", "FinLit App",
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        if (confirm.getProofOfPayment().getState().equalsIgnoreCase("approved")) {
                            ToastUtils.longToast("PaymentConfirmation info received from FinLit");
                            showProgress();
                            SubscriptionModel model = new SubscriptionModel();
                            model.setPlanId(subscriptionId);
                            presenter.createPlan(model);
                        }


                    } catch (JSONException e) {
                        Log.d(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}
