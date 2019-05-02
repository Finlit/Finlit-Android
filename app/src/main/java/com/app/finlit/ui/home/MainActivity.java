package com.app.finlit.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.ui.authenticate.MyProfileActivity;
import com.app.finlit.ui.authenticate.QuestionsActivity;
import com.app.finlit.ui.authenticate.contract.UserApiContract;
import com.app.finlit.ui.authenticate.presentor_impl.UserApiPresenterImpl;
import com.app.finlit.ui.base.BaseActivity;
import com.app.finlit.ui.blog.MyBlogActivity;
import com.app.finlit.ui.chat.ChatActivity;
import com.app.finlit.ui.dates.DatesActivity;
import com.app.finlit.ui.dates.PendingDatesActivity;
import com.app.finlit.ui.nearby.NearByActivity;
import com.app.finlit.ui.notification.NotificationActivity;
import com.app.finlit.ui.settings.SettingsActivity;
import com.app.finlit.utils.Constants;
import com.app.finlit.utils.SharedPreferenceHelper;
import com.app.finlit.utils.ToastUtils;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements UserApiContract.View{

    public static final String FINISH_ALERT = "finish_alert";
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.iv_left)
    public ImageView ivLeft;

    @BindView(R.id.tv_heading)
    public TextView tvTitle;

    private Dialog dialog= null;
    private TextView txtVersionCode;
    private String versionName;
    private TextView txtDialogPay, txtDialogCancel;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = Constants.CLIENT_ID;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    private UserApiContract.Presentor presentor;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentor = new UserApiPresenterImpl(this);
        initToolbar();
        try {
            versionName = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
       registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }

    private void initToolbar() {
//        tvTitle.setText("Home");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }

    @OnClick(R.id.iv_right_2)
    public void onClickNotification(){
        NotificationActivity.start(this);
    }

    @OnClick(R.id.linear_find_match)
    public void onClickFindMatch(){
        DatesActivity.start(this);
        //FindMatchActivity.start(this);
    }

    @OnClick(R.id.linear_near_by)
    public void onClickNearBy(){
        NearByActivity.start(this);
    }

    @OnClick(R.id.linear_my_circle)
    public void onClickMyCircle(){
        startActivity(new Intent(MainActivity.this,ChatActivity.class));
    }

    @OnClick(R.id.my_blog)
    public void onClickMyBlog(){
        startActivity(new Intent(MainActivity.this,MyBlogActivity.class));
    }

    @OnClick(R.id.linear_setting)
    public void onClickSetting(){
        SettingsActivity.start(this);
    }

    @OnClick(R.id.linear_my_profile)
    public void onClickMyProfile(){
        MyProfileActivity.start(this);
    }

    @OnClick(R.id.linear_message)
    public void onClickMessage(){
        startActivity(new Intent(MainActivity.this, PendingDatesActivity.class));
    }

   @OnClick(R.id.linear_chat_room)
    public void onClickChatRoom(){

        startActivity(new Intent(MainActivity.this,NotificationActivity.class));
//
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_quiz_payment);
        dialog.getWindow().setLayout(width1,height1);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        txtDialogCancel = dialog.findViewById(R.id.txt_dialog_payment_cancel);
        txtDialogPay = dialog.findViewById(R.id.txt_dialog_payment_now);

        txtDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        txtDialogPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (SharedPreferenceHelper.getInstance().getPaidQuiz()){
//                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
//                intent.putExtra("UniqId", "from_setting");
//                startActivity(intent);
//                return;
//                }
//
//
//                PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, "1.0");
//
//                Intent intent = new Intent(MainActivity.this, com.paypal.android.sdk.payments.PaymentActivity.class);
//                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//                intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingToBuy);
//                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//                dialog.dismiss();
//            }
//        });
////
    }

//    private PayPalPayment getThingToBuy(String paymentIntent, String price) {
//        return new PayPalPayment(new BigDecimal(price), "USD", "FinLit App",
//                paymentIntent);
//    }

    @OnClick(R.id.linear_subscription)
    public void onClickSubscription(){
        SubscriptionsActivity.start(this);
    }

    @OnClick(R.id.iv_left)
    public void onClickInformation(){
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.70);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_version);
        dialog.getWindow().setLayout(width1,height1);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        txtVersionCode = dialog.findViewById(R.id.txt_version_code);
        txtVersionCode.setText(versionName);
    }

    @OnClick(R.id.tv_share)
    public void onClickShare(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.app.finlit");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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
                            UserModel userModel = new UserModel();
                            userModel.isPaidQuiz = true;
                            presentor.updatePassword(userModel);
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

    @Override
    public void onSuccessAddProfile(DataResponse<UserModel> response) {
    }

    @Override
    public void onSuccessUpdatePassword(DataResponse<UserModel> response) {
        if (isFinishing()){
            return;
        }
        SharedPreferenceHelper.getInstance().savePaidQuiz(true);
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                intent.putExtra("UniqId", "from_setting");
                startActivity(intent);
    }

    @Override
    public void onFailure(String error) {
        if (isFinishing()){
            return;
        }
        ToastUtils.shortToast(error);
    }
}
