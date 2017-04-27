package com.baiheplayer.desk.drawer.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.connect.NetworkUtil;
import com.baiheplayer.desk.connect.UDP;
import com.baiheplayer.desk.connect.Tool;
import com.baiheplayer.desk.connect.Wifi;
import com.baiheplayer.desk.control.HexTool;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.Device;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.baiheplayer.desk.global.activity.IdListActivity;
import com.baiheplayer.desk.global.event.DeviceEvent;
import com.baiheplayer.desk.global.logic.CheckChangeImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.activity_drawer_step_wifi)
public class StepWifiActivity extends BaseActivity {
    @ViewInject(R.id.et_wifi_account)
    private EditText mWifi;
    @ViewInject(R.id.et_wifi_password)
    private EditText mPassword;
    @ViewInject(R.id.img_wifi_search)
    private ImageButton mSearch;
    @ViewInject(R.id.cb_wifi_seen)
    private CheckBox mLook;
    @ViewInject(R.id.btn_trigger_next)
    private Button mButton;

    private WifiManager.MulticastLock lock;
    private WifiManager manager;
    private ProgressDialog dialog;
    private Executor executor;
    private UDP udp;
    public final int RESQEST_SSID_LIST = 1;
    private boolean needSearch = true;
    private boolean needConfig = true;
    private boolean needReload = true;
    // 获得ssid列表指令
    private final byte[] searchCode = new byte[]{(byte) 0xff, 0x00, 0x01, 0x01, 0x02};
    private final byte[] searchKey = "www.usr.cn".getBytes();
    private final String socketUC = "AT+SOCKA=UDPC,";
    private final String socketPORT = ","+Constant.EX_PORT+"\r\n";
    private final byte[] socketRESET = "AT+Z\r\n".getBytes();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Tool.REC_DATA:// 解析接收到的数据
                    byte[] data = (byte[]) msg.obj;
                    Log.i("chen", "接收到：" + HexTool.bytesToHexString(data));
                    Tool.bytesToHexString(data);

                    if ((data[0] & 0xff) == 0xff) {  //查询与连接
                        decodeData(data);
                    }

                    if ((data[0] & 0xff) == '1') {  //返回192.168.0.148,D8B04CB327FD,USR-C322,2.17.14

                        if(needSearch){
                            String info = new String(data);
                            String[] part= info.split(",");
                            needSearch = false;
                            udp.setIP(part[0]);   //对着目标模块？
                            handler.sendEmptyMessageDelayed(Tool.EXE_SET, 500);   //发起设置
                            // TODO: 2017/4/5  将搜索到的桌子数据存入到本地数据库
                            try {
                                Drawer drawer = new Drawer(part[0], Constant.EX_PORT,part[1]);
                                drawer.bind(Constant.USER_PHONE,Device.TYPE_DRAWER);
                                DeviceManager.getInstance().add(drawer);
                                EventBus.getDefault().post(new DeviceEvent());
                                Log.i("chen","存储成功");
                            } catch (DbException e) {
                                e.printStackTrace();
                                Log.i("chen","存储失败");
                            }
                        }

                    }
                    if ((data[3] & 0xff) == 'O' && (data[4] & 0xff) == 'K') {  //返回的OK
                       if (needReload) {
                           needReload = false;
                           handler.sendEmptyMessageDelayed(Tool.EXE_CPL, 500);
                           showInfo("设置成功，正在重启");
                       } else {
                           showInfo("重启成功");
                       }
                    }
                    break;
                case Tool.EXE_FRESH:    //刷新一次
                    udp.send(searchKey);
                    Log.i("chen", "发送一次查询指令");
                    break;
                case Tool.EXE_SET:      //设置wifi模块的服务器
//                    String hostName = "AT+SOCKA=UDPC,192.168.0.155,8899\r\n";
                    String hostName = socketUC+Tool.getIPAddressString()+socketPORT;
                    udp.send(hostName.getBytes());
                    break;
                case Tool.EXE_CPL:      //重启模块
                    udp.send(socketRESET);
                    break;
                default:
                    break;
            }
        }
    };
    @Subscribe
    @Override
    public void onView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mLook.setOnCheckedChangeListener(new CheckChangeImpl(mPassword));
        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        lock = manager.createMulticastLock("fawifi");
        lock.acquire();
        dialog = new ProgressDialog(this);
        executor = Executors.newSingleThreadExecutor();
        udp = new UDP(handler);
        dialog.setMessage("检索无线路由...");
        showInfo("开始连接");
    }

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        onBackPressed();
    }


    @Event(R.id.img_wifi_search)
    private void searchWifiAP(View view) {
        dialog.show();
        udp.send(searchCode);// 发送收索SSID指令
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }


    /**
     * 点击连接
     *
     * @param view
     */
    @Event(R.id.btn_trigger_next)
    private void ensureConnect(View view) {
        String ssid = mWifi.getText().toString();
        String pasd = mPassword.getText().toString();

        if (TextUtils.isEmpty(ssid)) {
            Snackbar.make(mPassword, "please input ssid", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pasd)) {
            pasd = "";
        }

        udp.setTargetPort(48899);  //48899端口：C32x系列的端口
        byte[] data = Tool.generate_02_data(ssid, pasd, 0);
        udp.send(data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESQEST_SSID_LIST && data != null) {
            String ssid = data.getStringExtra("ssid");
            mWifi.setText(ssid);
        }
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void decodeData(byte[] data) {
        if ((data[0] & 0xff) != 0xff)// 如果接收到的数据不是0xff开头,那么丢弃
            return;
        switch (data[3] & 0xff) {
            case 0x81:// 找到的所有AP路由器
                dialog.dismiss();
                ArrayList<Wifi> ssids = Tool.decode_81_data(data);
                if (ssids.size() != 0) {
                    Intent intent = new Intent(this, IdListActivity.class);
                    intent.putExtra("ssids", ssids);
                    startActivityForResult(intent, RESQEST_SSID_LIST);
                }
                break;
            case 0x82:// 连接指定wifi
                int[] values = Tool.decode_82_data(data);
                if (values[0] == 0)
                    showInfo("没有找到该路由");
                else if (values[1] == 0)
                    Toast.makeText(this, "长度不正确", Toast.LENGTH_SHORT).show();
                else if (values[0] == 1 && values[1] == 1) {
                    showInfo("连接成功，正在跳转");
                    configModule();
                }
                break;
        }

    }

    private void configModule() {
        needSearch = true;
        needReload = true;
        needConfig = true;
        String ssid = mWifi.getText().toString();
        boolean isSucc = NetworkUtil.connectAP(manager, ssid);
        showInfo("跳转成功，正在设置");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (needSearch) {
                    handler.sendEmptyMessage(Tool.EXE_FRESH);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 实时反映进度
     * @param info
     */
    private void showInfo(String info){
        mButton.setText(info);
    }


    private void config(View view) {
//        configModule();
        byte[] data ="192.168.0.148,D8B04CB327FD,USR-C322,2.17.14".getBytes();
        String info = new String(data);
        String[] part = info.split(",");
        Device device = new Device(part[0],8899,part[1]);
        device.bind(Constant.USER_PHONE,Device.TYPE_CHEST);
        try {
            DeviceManager.getInstance().add(device);
            Log.i("chen","成功");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i("chen","失败");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);  //取消订阅
        udp.close();
    }


//
}
