package com.baiheplayer.desk.control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.baiheplayer.desk.connect.MDnsCallbackInterface;
import com.baiheplayer.desk.connect.MDnsHelper;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.drawer.Drawer;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Administrator on 2017/3/2.
 */

public class DeviceService extends Service {
    MDnsHelper mDnsHelper;
    MDnsCallbackInterface mDnsCallback;

    public static int CONNECT_STATE_UNRECEIVED = 0;
    public static int CONNECT_STATE_RECEIVED = 1;
    private int currentState = CONNECT_STATE_UNRECEIVED;
    private int port;
    private String AP_IP;
    private DatagramSocket datagramSocket;
    private UdpHelper udpHelper;
    private Thread receiver;
    private DeviceManager dm;

    @Override
    public void onCreate() {
        super.onCreate();
        dm = DeviceManager.getInstance();
        UdpHelper.getOb_Socket();
        UdpHelper.executeObserve(new IObserver() {
            @Override
            public void  onListen(InetAddress address,int port,byte[] cache) {

                Log.i("ping", "监听到："+Thread.currentThread().getName()+" " + HexTool.bytesToHexString(cache));
                if(address.getHostName()!=null && dm.getDevice(address.getHostName())!=null){
                    Drawer d = (Drawer)dm.getDevice(address.getHostName());
                    d.onFresh(HexTool.bytesToHexString(cache));
                }
                // TODO: 2017/3/17 立刻查询一次状态
                Log.i("ping", "消息来源" + address.getHostName() + " " + port);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        dm = DeviceManager.getInstance();
//        UdpHelper.getOb_Socket();
//        UdpHelper.executeObserve(new IObserver() {
//            @Override
//            public void  onListen(InetAddress address,int port,byte[] cache) {
//
//                Log.i("ping", "监听到："+Thread.currentThread().getName()+" " + HexTool.bytesToHexString(cache));
//                if(address.getHostName()!=null && dm.getDevice(address.getHostName())!=null){
//                    Drawer d = (Drawer)dm.getDevice(address.getHostName());
//                    d.onFresh(HexTool.bytesToHexString(cache));
//                }
//                // TODO: 2017/3/17 立刻查询一次状态
//                Log.i("ping", "消息来源" + address.getHostName() + " " + port);
//
//            }
//        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}