package com.baiheplayer.desk.chest.present;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Book;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ShelfLinearAdapter extends RecyclerView.Adapter implements IEditMode {

    private Context context;
    private List<Book> list;
    private IClickEvent callback;
    private boolean isShow = false;

    public ShelfLinearAdapter(Context context, List list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public void addClickEvent(IClickEvent callback) {
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shelf_book_linear, null);
        return new BookVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookVH) {
            ((BookVH) holder).onBind(position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void showCheckBox() {
        isShow = true;
        notifyDataSetChanged();   //刷新一遍

    }

    @Override
    public void hideCheckBox() {
        isShow = false;
        notifyDataSetChanged();
    }

    @Override
    public void checkedAllBox() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCK(true);
        }
        isShow = true;
        notifyDataSetChanged();
    }

    @Override
    public void unCheckedAllBox() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCK(false);
        }
        isShow = true;
        notifyDataSetChanged();
    }


    private class BookVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView name;
        TextView author;
        TextView time;
        CheckBox cb;
        int position;

        public BookVH(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.img_book_cover);
            name = (TextView) itemView.findViewById(R.id.tv_book_name);
            author = (TextView) itemView.findViewById(R.id.tv_book_author);
            time = (TextView) itemView.findViewById(R.id.tv_book_time);
            cb = (CheckBox) itemView.findViewById(R.id.cb_book_select);
            itemView.findViewById(R.id.ll_item).setOnClickListener(this);
            cover.setOnClickListener(this);
            cb.setOnClickListener(this);
        }

        public void onBind(final int position) {
            this.position = position;
            cb.setChecked(list.get(position).isCK());
            if (isShow) {
                cb.setVisibility(View.VISIBLE);
            } else {
                cb.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cb_book_select:
                    if (cb.isChecked()) {
                        list.get(position).setCK(true);
                    } else {

                        list.get(position).setCK(false);
                    }
                    break;
                case R.id.ll_item:
                case R.id.img_book_cover:
                    if (isShow) {
                        if (cb.isChecked()) {
                            cb.setChecked(false);
                            list.get(position).setCK(false);
                        } else {
                            cb.setChecked(true);
                            list.get(position).setCK(true);
                        }
                    } else {
                        callback.onEvent(cover);
                    }
                    break;
            }
        }
    }


}
