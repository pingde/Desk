package com.baiheplayer.desk.control;

import android.util.Log;


import com.baiheplayer.desk.global.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * UdpHelper帮助类
 */
public class UdpHelper {
    private static String target_ip = null;
    public static int target_port = 8899;
    private static DatagramSocket ob_socket;
    private static DatagramPacket ob_packet;
    private static DatagramPacket ex_packet;
    private static boolean ob_run = true;
    private static ExecutorService executor;

    public static DatagramSocket getOb_Socket() {
        if (ob_socket == null) {
            try {
                ob_socket = new DatagramSocket(target_port);

            } catch (SocketException e) {
                e.printStackTrace();
                Log.i("chen", "获得Socket失败");
            }
        }
        Log.i("chen", "获得Socket:" + ob_socket.hashCode());
        return ob_socket;
    }


    public static void getExepool() {
        if (ob_socket == null) {
            try {
                ob_socket = new DatagramSocket(target_port);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        Log.i("chen", "获得执行的Socket:" + ob_socket.hashCode());
        if(executor == null){
            executor = Executors.newCachedThreadPool();
        }

    }

    /**
     * 监听消息
     * @param observer
     */
    public static void executeObserve(IObserver observer) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new ObserverThread(observer));

    }

    private static class ObserverThread implements Runnable {

        byte[] cache;
        IObserver observer;

        ObserverThread(IObserver observer) {
            this.observer = observer;
            cache = new byte[20];
            ob_packet = new DatagramPacket(cache, cache.length);
        }

        @Override
        public void run() {
            while (ob_run) {
                try {
                    cache = null;           //清除接收数据
                    ob_socket.receive(ob_packet);
                    cache = ob_packet.getData();
                    InetAddress ia = ob_packet.getAddress();
                    observer.onListen(ia,ob_packet.getPort(),cache);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void executeCommand(String cmd) {
        // TODO: 2017/3/7 解耦，加上变量ip 
        InetAddress netAddress = null;
        try {
            netAddress = InetAddress.getByName(Constant.OPT_DEVICE_HOST);//"192.168.0.148"
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Log.i("chen", "发送内容：" + cmd);
        byte[] command = HexTool.hexStringToBytes(cmd);  //指令字符串转byte[]
        int cmd_len = command.length;
        ex_packet = new DatagramPacket(command, cmd_len, netAddress, 8899);

        executor.execute(new ExecutorThread(command));

    }

    private static class ExecutorThread implements Runnable {
        byte[] command;

        public ExecutorThread(byte[] command) {
            this.command = command;
        }

        @Override
        public void run() {
            try {
                Log.i("chen", "发送线程：" + Thread.currentThread().getName());
                ob_socket.send(ex_packet);
                Log.i("chen", "发送成功");

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("chen", "发送失败");
            }
        }
    }
}