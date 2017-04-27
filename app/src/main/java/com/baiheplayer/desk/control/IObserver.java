package com.baiheplayer.desk.control;

import java.net.InetAddress;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface IObserver {
    void onListen(InetAddress address, int port, byte[] cache);
}
