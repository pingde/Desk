package com.baiheplayer.desk.global.logic;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/2/22.
 */

public class TextChangeImpl implements TextWatcher {

    private ImageView mImage;
    public TextChangeImpl(){}

    public TextChangeImpl(ImageView imageView){
        this.mImage = imageView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(count>0){
            mImage.setVisibility(View.VISIBLE);
        } else {
            mImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
