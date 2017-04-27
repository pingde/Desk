package com.baiheplayer.desk.drawer.view;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface Behave {
    boolean isRun();                //当前是否在loading
    void changSize(boolean isUp);   //圆的缩放
    void close();                   //抽屉关闭
    void open();                    //抽屉打开
    void lock();                    //抽屉上锁
    void error();                   //抽屉错误
    void exeCmd();                  //执行命令
    void cancel();                  //取消加载
}
