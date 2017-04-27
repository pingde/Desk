package com.baiheplayer.desk.chest.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Book;
import com.baiheplayer.desk.chest.activity.AddActivity;
import com.baiheplayer.desk.chest.activity.BookActivity;
import com.baiheplayer.desk.chest.present.IClickEvent;
import com.baiheplayer.desk.chest.present.ShelfGridAdapter;
import com.baiheplayer.desk.chest.present.ShelfLinearAdapter;
import com.baiheplayer.desk.chest.present.ShelfPresent;
import com.baiheplayer.desk.global.Share;
import com.baiheplayer.desk.global.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
@ContentView(R.layout.fragment_shelf)
public class ShelfFragment extends BaseFragment {
    private static ShelfFragment fragment;

    public ShelfFragment() {
    }

    public static ShelfFragment getInstance() {
        if (fragment == null) {
            fragment = new ShelfFragment();
        }
        return fragment;
    }


    Context context;
    LinearLayoutManager llm;
    GridLayoutManager glm;
    ShelfGridAdapter gridAdapter;
    ShelfLinearAdapter linearAdapter;
    ShelfPopup popup;       //弹窗
    List<Book> books;       //数据集合
    ShelfPresent present;
    private static final String TYPE = "SHELF_TYPE";
    private static final String DIS_AS_GRID = "网格显示";
    private static final String DIS_AS_LINEAR = "列表显示";
    private static int TYPE_GRID = 1;
    private static int TYPE_LINEAR = 2;
    private static int useType = 1;
    private boolean isInEdit = false;
    private TextView typeNotice;
    @ViewInject(R.id.rv_book_shelf)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.rl_hide_mode)
    private RelativeLayout mTitle;
    @ViewInject(R.id.ll_hide_mode)
    private LinearLayout mButton;
    @ViewInject(R.id.tv_select_mode)
    private TextView mAll;
    @ViewInject(R.id.tv_select_finish)
    private TextView mCpl;

    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
        Share.init(context.getApplicationContext());
        useType = Share.getInt(TYPE, TYPE_GRID);
        books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());


    }

    @Override
    public void onView() {
        present = new ShelfPresent(context, mRecyclerView, books);
        present.init(useType == TYPE_LINEAR);
        present.addClickEvent(new IClickEvent() {
            @Override
            public void onEvent(View view) {
                Log.i("chen","界面跳转");
                ActivityOptionsCompat compat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(),view,getString(R.string.transition));
                ActivityCompat.startActivity(getActivity(),new Intent(context, BookActivity.class),compat.toBundle());
            }
        });
        initPop();
    }


    private void initPop() {
        // TODO: 2017/3/22 不够带感
        View v = View.inflate(context, R.layout.pop_clean_shelf, null);
        typeNotice = (TextView) v.findViewById(R.id.tv_display_type);
        TextView syncType = (TextView) v.findViewById(R.id.tv_sync_type);
        TextView editShelf = (TextView) v.findViewById(R.id.tv_edit_shelf);
        TextView scanShelf = (TextView) v.findViewById(R.id.tv_scan_shelf);
        v.findViewById(R.id.rl_display_type).setOnClickListener(listener);
        v.findViewById(R.id.rl_sync_type).setOnClickListener(listener);
        v.findViewById(R.id.rl_edit_shelf).setOnClickListener(listener);
        v.findViewById(R.id.rl_scan_shelf).setOnClickListener(listener);
        typeNotice.setOnClickListener(listener);
        syncType.setOnClickListener(listener);
        editShelf.setOnClickListener(listener);
        scanShelf.setOnClickListener(listener);
        if (useType == TYPE_GRID) {
            typeNotice.setText(DIS_AS_LINEAR);
        }
        if (useType == TYPE_LINEAR) {
            typeNotice.setText(DIS_AS_GRID);
        }
        popup = new ShelfPopup(v);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_display_type:
                case R.id.tv_display_type:
                    if (useType == TYPE_GRID) {   //当前网格展示
                        showAsLinear();
                    } else {                    //当前线性展示或其他
                        showAsGrid();
                    }
                    break;
                case R.id.rl_sync_type:
                case R.id.tv_sync_type:
                    break;
                case R.id.rl_edit_shelf:
                case R.id.tv_edit_shelf:        //开启编辑模式
                    mTitle.setVisibility(View.VISIBLE);
                    present.enterMode();
                    popup.dismiss();
                    isInEdit = true;
                    break;
                case R.id.rl_scan_shelf:
                case R.id.tv_scan_shelf:
                    startActivity(new Intent(context, AddActivity.class));
                    break;
            }
        }
    };

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goback(View view){
        getActivity().onBackPressed();
    }

    //全选或者全不选
    @Event(R.id.tv_select_mode)
    private void modeComplete(View view) {
        if (mAll.getText().toString().equals("全不选")) {
            present.unCheckedBox();
            mAll.setText("全选");
        } else if (mAll.getText().toString().equals("全选")) {
            present.checkAllBox();
            mAll.setText("全不选");
        }
    }

    @Event(R.id.tv_select_finish)   //代表完成
    private void modeSelectAll(View view) {
        present.deleteBook();
        exitEditMode();
    }

    public void exitEditMode(){
        mTitle.setVisibility(View.GONE);
        present.exitMode();
        isInEdit = false;
        ObjectAnimator anim = ObjectAnimator.ofFloat(mTitle,"translationY",0.0f,-1.0f);
        anim.setDuration(1000);
        anim.start();
    }

    @Event(R.id.img_add_device)
    private void displayPop(View view) {
        popup.showIn(view);
    }

    public boolean isInEditMode(){
        return isInEdit;
    }


    private void showAsLinear() {
        present.displayAsLinear();
        useType = TYPE_LINEAR;
        Share.putInt(TYPE, TYPE_LINEAR);
        typeNotice.setText(DIS_AS_GRID);
        popup.dismiss();
    }

    private void showAsGrid() {
        present.displayAsGrid();
        useType = TYPE_GRID;
        Share.putInt(TYPE, TYPE_GRID);
        typeNotice.setText(DIS_AS_LINEAR);
        popup.dismiss();
    }
}
