package com.baiheplayer.desk;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.baiheplayer.desk.global.Share;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class DeskApp extends Application {
    private static DeskApp instance;

    public static DeskApp getInstance() {
        return instance;
    }

    private static DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    private List<Activity> activityList = new ArrayList<>();//存放Activity的List集合

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        daoConfig = new DbManager.DaoConfig().setDbName("db_device.db").setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        Share.init(this);
    }


    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void finishAllActivity() {
        if (activityList != null) {
            int size = activityList.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity activity = activityList.get(i);
                if (activity != null) {
                    activity.finish();
                }
                activityList.remove(activity);
            }
        }
    }
}
