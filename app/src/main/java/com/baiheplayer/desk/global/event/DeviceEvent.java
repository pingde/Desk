package com.baiheplayer.desk.global.event;

import com.baiheplayer.desk.global.Device;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class DeviceEvent {
    private Device devices;
    private boolean needFlush;

    public DeviceEvent() {
    }

    public DeviceEvent(Device devices) {
        this.devices = devices;
    }

    public Device getDevice(){
        return devices;
    }

    public Device getDevices() {
        return devices;
    }

    public void setDevices(Device devices) {
        this.devices = devices;
    }

    public boolean isNeedFlush() {
        return needFlush;
    }

    public void setNeedFlush(boolean needFlush) {
        this.needFlush = needFlush;
    }
}
