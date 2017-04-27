package com.baiheplayer.desk.global.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.connect.MDnsCallbackInterface;
import com.baiheplayer.desk.connect.MDnsHelper;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.global.Device;
import com.baiheplayer.desk.global.adapter.DeviceAdapter;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.activity.ChoActivity;
import com.baiheplayer.desk.global.event.DeviceEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
@ContentView(R.layout.fragment_device)
public class DeviceFragment extends BaseFragment {
    private static DeviceFragment deviceFragment;

    public DeviceFragment() {
    }

    public static DeviceFragment getInstance() {
        if (deviceFragment == null) {
            deviceFragment = new DeviceFragment();
        }

        return deviceFragment;
    }


    @ViewInject(R.id.img_add_device)
    private ImageView mAdd;
    @ViewInject(R.id.recycle_view)
    private RecyclerView mRecyclerView;
    private Context context;
    private List<Device> devices;
    private DeviceAdapter adapter;

    private MDnsHelper mDnsHelper;
    private MDnsCallbackInterface mDnsCallback;
    private DeviceManager dm;
    private JSONObject object;
    private static final int HAVE_DEVICE = 100;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HAVE_DEVICE:
                    Log.i("chen", "MDnsCallbackInterface---->" + object.toString());
                    Log.i("chen", "设备在线。。。");
                    try {
                        String host = (String) object.get("host");
                        String mac = (String) object.get("name");
                        int port = (int) object.get("port");
                        Log.i("chen", "Thread:" + Thread.currentThread().getName());
                        dm.getDevice(host, port, mac);
                        devices = dm.getAllSavedDevices();
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);   //事件订阅
        context = getContext();
        dm = DeviceManager.getInstance();
        dm.loadData();
        initMDns();
        Log.i("chen","检测数据");
        devices = dm.getAllSavedDevices();
        UdpHelper.getExepool();
        adapter = new DeviceAdapter(context, devices);
    }

    @Override
    public void onView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 实时修改数据显示。
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeviceEvent event){
        devices.clear();
        dm.loadData();  //刷新
//        devices = dm.getAllSavedDevices();
        adapter.notifyDataSetChanged();
    }

    @Event(R.id.img_add_device)
    private void addDevice(View v) {
        Intent intent = new Intent(context, ChoActivity.class);
        startActivity(intent);
    }

    private void initMDns() {
        mDnsCallback = new MDnsCallbackInterface() {
            @Override
            public void onDeviceResolved(JSONObject deviceJSON) {
                object = deviceJSON;
                handler.sendEmptyMessage(HAVE_DEVICE);
            }
        };
        mDnsHelper = new MDnsHelper();
        mDnsHelper.init(context, mDnsCallback);
        beginSearch();
    }

    private void beginSearch() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                mDnsHelper.startDiscovery();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        x.task().run(new Runnable() {
            @Override
            public void run() {
                mDnsHelper.stopDiscovery();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("chen","fragment已关闭");
        EventBus.getDefault().unregister(this);   //事件订阅
    }


}
