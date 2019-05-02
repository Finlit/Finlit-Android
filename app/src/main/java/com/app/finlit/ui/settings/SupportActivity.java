package com.app.finlit.ui.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;

import butterknife.BindView;

public class SupportActivity extends BaseActivity {

    @BindView(R.id.tv_support)
    public TextView textView;

    @BindView(R.id.iv_left)
    public ImageView iv_back;

    @BindView(R.id.tv_heading)
    public TextView heading;

    @Override
    protected int getContentId() {
        return R.layout.activity_support;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        heading.setText("Support");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText("Support\n" +
                "If you have any questions or concerns whatsoever, please reach out to us at letsmeet@finlitdating.com\n" +
                "\n" +
                "Thank you!");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }
}
