package com.baiheplayer.desk.global;


import android.util.Log;

import com.baiheplayer.desk.DeskApp;
import com.baiheplayer.desk.chest.Chest;
import com.baiheplayer.desk.drawer.Drawer;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理类
 * Created by Administrator on 2017/3/7.
 */

public class DeviceManager {
    private DbManager db;
    private List<Device> devices;
    private static DeviceManager manager;

    private DeviceManager() {
        devices = new ArrayList<>();
        db = x.getDb(DeskApp.getInstance().getDaoConfig());
    }

    public synchronized static DeviceManager getInstance() {
        if (manager == null) {
            manager = new DeviceManager();
        }
        return manager;
    }

    /**
     * 装载存储的连接设备的数据
     *
     */
    public synchronized void loadData() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                Log.i("chen",Thread.currentThread().getName());
                try {
                    devices.clear();
                    List<Drawer> drawersFromDb = db.selector(Drawer.class).findAll();
                    List<Chest> chestsFromDb = db.selector(Chest.class).findAll();
                    if (drawersFromDb != null){
                        devices.addAll(drawersFromDb);
                    }
                    if (chestsFromDb != null)
                        devices.addAll(chestsFromDb);

                    Log.i("chen", "获取······db成功");
                } catch (DbException e) {
                    e.printStackTrace();
                    Log.i("chen", "获取······db失败");
                }
            }
        });

    }

    /**
     * 刷新数据到
     */
    public void flushData(){
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Drawer> drawersFromDb = db.selector(Drawer.class).findAll();
                    List<Chest> chestsFromDb = db.selector(Chest.class).findAll();
                    if (drawersFromDb != null )
                        for(Drawer drawer:drawersFromDb){
                            if(!devices.contains(drawer)){
                                devices.add(drawer);
                            }
                        }
                        devices.addAll(drawersFromDb);
                    if (chestsFromDb != null)
                        devices.addAll(chestsFromDb);

                    Log.i("chen", "获取······db成功");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("chen", "获取······db失败");
                }
            }
        });
    }

    private void addIntoList(List<Device> deviceFromDb){
        for(Device device:deviceFromDb){
            boolean isContain = false;
        }

    }



    /**
     * 查询自己有没有，没有就算了不管null。
     *
     * @param ip
     * @return
     */
    public Device getDevice(String ip) {
        for (Device device : devices) {
            if (device.getHost().equals(ip)) {
                return device;
            }
        }
//        Drawer d = new Drawer(ip);
        return null;
    }

    /**
     * 查询自己有没有，没有就新建一个，下一次就有了
     * 以后可能不需要
     *
     * @param ip
     * @param port
     * @param name
     * @return
     */
    public Device getDevice(String ip, int port, String name) {
        for (Device device : devices) {
            if (device.getHost().equals(ip)) {
                return device;
            }
        }
        Drawer d = new Drawer(ip, port, name);
        devices.add(d);
        return d;
    }

    public Device getChest(String ip, int port, String name) {
        for (Device device : devices) {
            if (device.getHost().equals(ip)) {
                return device;
            }
        }
        Chest d = new Chest(ip, port, name);
        devices.add(d);
        return d;
    }

    public List<Device> getAllSavedDevices() {
        return devices;
    }


    /**
     * 保存到dm的集合devices中
     *
     * @param d
     * @throws DbException
     */
    public synchronized void add(Device d) throws DbException {
        String mac = d.getMac();
        boolean isContain = false;
        for (Device device : devices) {
            if (device.getMac().equals(mac)) {
                isContain = true;
                break;
            }
        }
        if (!isContain) {
            devices.add(d);
        }

        db.saveOrUpdate(d);

    }

    /**
     * 从数据库移除，
     *
     * @param device
     * @throws DbException
     */
    public synchronized void delDevice(Device device) {
        String mac = device.getMac();
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getMac().equals(mac)) {
                try {
                    db.delete(device);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                devices.remove(i);
                return;
            }
        }
    }
}
