package com.baiheplayer.desk.chest.present;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface IEditMode {
    void showCheckBox();
    void hideCheckBox();
    void checkedAllBox();
    void unCheckedAllBox();
    void addClickEvent(IClickEvent callback);
}
