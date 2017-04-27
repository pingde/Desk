package com.baiheplayer.desk.global.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baiheplayer.desk.DeskApp;
import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Chest;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.activity.StepTrigActivity;
import com.baiheplayer.desk.global.Device;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.baiheplayer.desk.global.event.DeviceEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
@ContentView(R.layout.activity_cho)
public class ChoActivity extends BaseActivity {
    int i = 1;
    DbManager db;

    @Subscribe
    @Override
    public void onView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        db = x.getDb(DeskApp.getInstance().getDaoConfig());
    }

    @Event(R.id.ll_choose_drawer)
    private void chooseDrawer(View view) {
        startActivity(new Intent(this, StepTrigActivity.class));
    }

    @Event(R.id.ll_choose_chest)
    private void chooseChest(View view) {
        Snackbar.make(view, "敬请期待", Snackbar.LENGTH_SHORT).show();
    }

    @Event(R.id.add_data)
    private void addData(View view) {
        String ip = "192.168.0.18" + i;
        Drawer drawer = new Drawer(ip, 8899, ip);
        drawer.pwd_l = "1234";
        drawer.pwd_r = "6666";
        try {
            db.save(drawer);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
        i++;
    }

    @Event(R.id.del_data)
    private void delData(View view) {
        try {
            db.delete(Drawer.class);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.upd_data)
    private void updData(View view) {
        String ip = "192.168.0.5";
        Drawer drawer = new Drawer(ip, 8899, ip);
        drawer.pwd_l = "5555";
        drawer.pwd_r = "ABCD";
        try {
            db.update(drawer);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.mod_data)
    private void modData(View view) {
        String ip = "192.168.0.18"+i;
        Drawer drawer = new Drawer(ip, 8899, ip);
        drawer.pwd_l = "1111";
        drawer.pwd_r = "2222";
        try {
            db.saveOrUpdate(drawer);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.show_data)
    private void showData(View view) {
        try {
            List<Drawer> drawers = db.selector(Drawer.class).findAll();
            for(Drawer drawer:drawers){
                Log.i("chen",drawer.getHost());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

/***********************************************************************************/
    @Event(R.id.add_data2)
    private void addData2(View view) {
        String ip = "192.168.0.23" + i;
        Chest chest = new Chest(ip, 8899, ip);

        try {
            db.save(chest);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
        i++;
    }

    @Event(R.id.del_data2)
    private void delData2(View view) {
        try {
            db.delete(Chest.class);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.upd_data2)
    private void updData2(View view) {
        String ip = "192.168.0.5";
        Chest chest = new Chest(ip, 8899, ip);

        try {
            db.update(chest);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.mod_data2)
    private void modData2(View view) {
        String ip = "192.168.0.18"+i;
        Chest chest = new Chest(ip, 8899, ip);
        try {
            db.saveOrUpdate(chest);
            EventBus.getDefault().post(new DeviceEvent());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.show_data2)
    private void showData2(View view) {
        try {
            List<Chest> chests = db.selector(Chest.class).findAll();
            for(Chest chest:chests){
                Log.i("chen",chest.getHost());
                Toast.makeText(this,chest.getHost(),Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
