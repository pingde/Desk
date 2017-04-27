package com.baiheplayer.desk.global;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 存储
 * Created by Administrator on 2017/3/7.
 */
@Table(name = "device")
public class Device {

    public static final int TYPE_DRAWER = 1;
    public static final int TYPE_CHEST = 2;

    @Column(name = "mac", isId = true)
    private String mac;
    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private int port;
    @Column(name = "type")
    private int type;
    @Column(name = "user")
    private String user;

    public Device() {
    }

    public Device(String ip) {
        this.host = ip;
    }

    public Device(String host, int port, String mac) {
        this.host = host;
        this.port = port;
        this.mac = mac;
    }

    public void bind(String user, int type) {
        this.user = user;
        this.type = type;
    }

    @Override
    public String toString() {
        return host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
