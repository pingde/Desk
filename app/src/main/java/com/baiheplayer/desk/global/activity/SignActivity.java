package com.baiheplayer.desk.global.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.Share;
import com.baiheplayer.desk.global.logic.CheckChangeImpl;
import com.baiheplayer.desk.global.logic.TextChangeImpl;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/8.
 */
@ContentView(R.layout.activity_sign)
public class SignActivity extends BaseActivity {

    private final String cLogin = "isLogin";
    private final String cName = "nickname";
    private final String cPwd = "password";

    @ViewInject(R.id.et_sign_account)
    private EditText mAccount;
    @ViewInject(R.id.et_sign_verify)
    private EditText mVerify;
    @ViewInject(R.id.et_sign_password)
    private EditText mPassword;
    @ViewInject(R.id.img_sign_del)
    private ImageButton mDel;
    @ViewInject(R.id.tv_get_verify)
    private TextView mGetVerify;
    @ViewInject(R.id.cb_login_del)
    private CheckBox mLook;
    @Override
    public void onView(Bundle savedInstanceState) {
        mAccount.addTextChangedListener(new TextChangeImpl(mDel));
        mLook.setOnCheckedChangeListener(new CheckChangeImpl(mPassword));
    }

    /**点击删除清空输入账号*/
    @Event(R.id.img_sign_del)
    private void clearAccount(View v){
        String account = null ;
        mAccount.setText(account);
    }

    @Event(value = {R.id.rl_back,R.id.ib_back})
    private void goBack(View v){
        onBackPressed();
    }

    @Event(R.id.btn_sign_ensure)
    private void signEnsure(View v){
        Share.putBoolean(cLogin,false);
        Constant.isLogin = false;
        Snackbar.make(mAccount,"清空当前账号",Snackbar.LENGTH_SHORT).show();
    }
}
