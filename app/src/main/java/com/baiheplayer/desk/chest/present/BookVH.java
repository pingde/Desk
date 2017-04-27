package com.baiheplayer.desk.chest.present;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiheplayer.desk.chest.Book;

/**
 * Created by Administrator on 2017/3/21.
 */

public class BookVH extends RecyclerView.ViewHolder {

    ImageView cover;
    TextView textView;

    public BookVH(View itemView) {
        super(itemView);
    }

    public void onBindViewHolder(Book book) {

    }
}
