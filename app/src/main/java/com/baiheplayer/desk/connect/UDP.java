package com.baiheplayer.desk.connect;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 济南有人物联网    刘金启
 */
public class UDP extends Thread {
    private Handler handler;
    private DatagramSocket socket;
    private String IP = "255.255.255.255";
    private int PORT = 26000;
    private int targetPort = 48899;  //USR-C322系列指定的修改端口
    private boolean receive = true;
    private boolean send = true;
    private Executor executor;
    private Queue<byte[]> msgQueue;     //发送字符数组的队列
//    private ReceiveThread receiveThread;    //接收线程

    // TODO: 2017/3/31 需要单例模式？

    public UDP(Handler handler) {
        this.handler = handler;
        receive = true;
        msgQueue = new LinkedList<>();
        executor = Executors.newSingleThreadExecutor();
        init();
    }

    public void init() {

        try {
            socket = new DatagramSocket(null);
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(PORT));
            start();
//            receiveThread = new ReceiveThread();
//            receiveThread.start();
//            commandThread = new CommandThread();
//            commandThread.start();
        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("chen", "Search Thread init fail");
            return;
        }

    }

    @Override
    public void run() {
        if (socket == null) {
            return;
        }
        Log.i("chen", "开始执行监听线程");
        super.run();
        byte[] data = new byte[128];
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        try {
            while (receive) {
                socket.receive(receivePacket);
                if (null != handler) {
                    byte[] recData = new byte[receivePacket.getLength()];
                    System.arraycopy(data, 0, recData, 0, recData.length);
                    Message msg = handler.obtainMessage(Tool.REC_DATA, recData);
                    handler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class CommandThread extends Thread {

        @Override
        public void run() {
            super.run();
            synchronized (this) {
                while (send) {
                    // 当队列里的消息发送完毕后，线程等待
                    try {
                        while (msgQueue.size() > 0) {
                            if (socket == null) {
                                return;
                            }
                            byte[] msg = msgQueue.poll();
                            Log.i("chen", "发送： " + new String(msg));
                            DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(IP), targetPort);
                            socket.send(sendPacket);
                        }
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public synchronized void send(final byte[] msg) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (socket == null) {
                    return;
                }
                Log.i("chen", Thread.currentThread().getName() + "发送： " + new String(msg));
                try {
                    DatagramPacket sendPacket = new DatagramPacket(msg,
                            msg.length, InetAddress.getByName(IP), targetPort);
                    socket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void close() {
        send = false;
        receive = false;
        if (socket == null)
            return;
        socket.close();
        Log.i("chen", "最后的Socket" + socket);
    }

    private void sendErrorMsg(String info) {

    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public void setIP(String ip) {
        this.IP = ip;
    }

    public String getLocalHost() {
        return socket.getLocalAddress().getHostName();
    }
}
