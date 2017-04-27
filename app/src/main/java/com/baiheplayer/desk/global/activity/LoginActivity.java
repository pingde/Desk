package com.baiheplayer.desk.global.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.Share;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A login screen that offers login via email/password.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private final String cLogin = "isLogin";
    private final String cPhone = "phone";
    private final String cPwd = "password";

    // UI references.
    @ViewInject(R.id.email)
    private AutoCompleteTextView mEmailView;
    @ViewInject(R.id.password)
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mGoSign;
    private final int GO_TO_SIGN = 1;
    private final int GO_TO_MAIN = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GO_TO_SIGN:
                    startActivity(new Intent(LoginActivity.this, SignActivity.class));
                    break;
                case GO_TO_MAIN:
                    startActivity(new Intent(LoginActivity.this, DeviceActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onView(Bundle savedInstanceState) {
        mEmailView.setText(Constant.USER_PHONE);
    }

    @Event(R.id.tv_go_sign)
    private void goSignActivity(View v) {
        handler.sendEmptyMessage(GO_TO_SIGN);
    }

    @Event(R.id.email_sign_in_button)
    private void showSnackBar(View v) {
        Share.putBoolean(cLogin, true);
        Constant.isLogin = true;  //实时跟进刷新
        Share.putString(cPhone, mEmailView.getText().toString().trim());
        Share.putString(cPwd, mPasswordView.getText().toString().trim());
        Snackbar.make(mEmailView, "假设已成功登录", Snackbar.LENGTH_SHORT).show();
        handler.sendEmptyMessageDelayed(GO_TO_MAIN, 3000);
    }


}

