package com.baiheplayer.desk.chest.present;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.baiheplayer.desk.chest.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理两个Adapter 以及切换
 * Created by Administrator on 2017/3/22.
 */

public class ShelfPresent {
    Context context;
    LinearLayoutManager llm;
    GridLayoutManager glm;
    ShelfGridAdapter gridAdapter;
    ShelfLinearAdapter linearAdapter;
    List<Book> books;       //数据集合
    List<Boolean> checkBoxes;
    RecyclerView mRecyclerView;

    public ShelfPresent(Context context,RecyclerView mRecyclerView,List books){
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.books = books;
        checkBoxes = new ArrayList<>();
        for(int i=0;i<books.size();i++){
            this.books.get(i).setCK(false);
            checkBoxes.add(false);
        }
    }

    public void init(boolean isLinear){
        glm = new GridLayoutManager(context,3);
        llm = new LinearLayoutManager(context);
        linearAdapter = new ShelfLinearAdapter(context,books);
        gridAdapter = new ShelfGridAdapter(context,books);
        if(isLinear){
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(linearAdapter);
        } else {
            mRecyclerView.setLayoutManager(glm);
            mRecyclerView.setAdapter(gridAdapter);
        }
    }

    public void addClickEvent(IClickEvent clickEvent){
        gridAdapter.addClickEvent(clickEvent);
        linearAdapter.addClickEvent(clickEvent);
    }

    //以网格的形式展示书本
    public void displayAsGrid(){
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.swapAdapter(gridAdapter,true);
    }
    //以条目的形式展示书本
    public void displayAsLinear(){
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.swapAdapter(linearAdapter,true);
    }

    public void enterMode(){
        linearAdapter.showCheckBox();
        gridAdapter.showCheckBox();
    }

    public void exitMode(){
        linearAdapter.hideCheckBox();
        gridAdapter.hideCheckBox();
    }

    public void checkAllBox(){
        linearAdapter.checkedAllBox();
        gridAdapter.checkedAllBox();
    }

    public void unCheckedBox(){
        linearAdapter.unCheckedAllBox();
        gridAdapter.unCheckedAllBox();
    }

    //选书
    public int chooseBook(){
        int num = 0;
        for(int i=0;i<checkBoxes.size();i++){
            if(checkBoxes.get(i)){
                num = num+1;
            }
        }
        return num;
    }
    //删除书
    public void deleteBook(){
        List<Book> temp = new ArrayList<>();
        for(int i=0;i<books.size();i++){
            if(!books.get(i).isCK()){
                temp.add(books.get(i));
            }
        }
        books.clear();
        books.addAll(temp);
        gridAdapter.notifyDataSetChanged();
        linearAdapter.notifyDataSetChanged();
    }
}
