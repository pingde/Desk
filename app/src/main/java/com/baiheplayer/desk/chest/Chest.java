package com.baiheplayer.desk.chest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Device;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
@Table(name = "Chest")
public class Chest extends Device implements CmdPool {

    public String id = "00000001";
    public String drawer_l = O;    //左抽屉状态o
    public String drawer_r = O;    //右抽屉状态o
    public String chest_l = O;       //灯带状态o
    public String chest_r = O ;    //灯光种类o
    public String lock_l = O;      //左边锁的状态o
    public String lock_r = O;      //右边锁的状态o
    public String alarm = O;       //故障    O _F F_ F
    public String power = O;       //电源状态
    public String lock_s = O;      //当前锁有无密码   O _F F_ F

    @Column(name = "pwd_l")
    public String pwd_l = A + A;     //左边锁密码
    @Column(name = "pwd_r")
    public String pwd_r = A + A;     //右边锁密码

    public boolean isFlush = false;
    private String lastInfo ;
    private String info;
    private Handler handler = new Handler(Looper.getMainLooper());

    private List<Drawer.ICurrentState> calls = new ArrayList<>();


    public Chest(){}


    public Chest(String ip) {
        super(ip);
    }

    public Chest(String host, int port, String mac) {
        super(host, port, mac);
    }

    /**
     * 定义唯一身份识别是mac值得hash值
     * @return
     */
    @Override
    public int hashCode() {
        return getMac().hashCode();
    }

    public interface ICurrentState {
        void onState();
    }

    public void addCurrentState(Drawer.ICurrentState callback) {
        calls.add(callback);
    }

    /**
     * A55A3D00000001AAAAAA556677AAAA0F32FF0D
     *
     * @param str
     */
    @Override
    public synchronized void onFresh(String str) {
        //A5 5A 3D 00 00 00 01 AA AA AA FD AA AA 0F 32 FF 0D
        info = str;
        if (info.startsWith(OB_DVS) && calls.size() != 0 && !info.equals(lastInfo)) {  //
            id = info.substring(6, 14);
            drawer_l = info.substring(14, 16);
            drawer_r = info.substring(16, 18);
            chest_l = info.substring(18, 20);
            chest_r = info.substring(20, 22);  //空了两个
            lock_l = info.substring(26, 28);
            lock_r = info.substring(28, 30);
            alarm = info.substring(30, 32);
            power = info.substring(32, 34);
            lock_s = info.substring(34, 36);
            Log.i("chen", "执行一次刷新");
            if(!isFlush){
                isFlush = true;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    for (Drawer.ICurrentState ics : calls) {
                        ics.onState();
                    }
                }
            });
        }
        lastInfo = info;
    }


    public String getCommand() {
        String cmd = CMD_EXE + id + OPT_DAT + drawer_l + drawer_r + chest_l + chest_r + pwd_l + pwd_r + END_FLAG;
        return cmd;
    }

    /***************被调用的方法***********************************************/
    public int getPowerLever() {
        return Integer.parseInt(power);
    }

    public String getHealth() {
        return alarm;
    }

    public boolean isDrawerLeftOpen() {       //左抽屉已开启？
        if (drawer_l.equals(A)) {
            return true;
        }
        return false;
    }

    public boolean isDrawerLeftLock() {       //左抽屉已上锁？
        if (lock_l.equals(O)) {
            return true;
        }
        return false;
    }

    public boolean isDrawerRightOpen() {      //右抽屉已开启？
        if (drawer_r.equals(A)) {
            return true;
        }
        return false;
    }

    public boolean isDrawerRightLock() {      //右抽屉已上锁？
        if (lock_r.equals(O)) {
            return true;
        }
        return false;
    }

    public boolean isChestLeftOpen() {    //左柜子打开了？
        if (chest_l.equals(A)) {
            return true;
        }
        return false;
    }
    public boolean isChestRightOpen() {    //右柜子打开了？
        if (chest_r.equals(A)) {
            return true;
        }
        return false;
    }

    /**
     * 判断左边抽屉是否有密码
     *
     * @return
     */
    public boolean isLeftHasLock() {
        if (lock_s.equals(F_) || lock_s.equals(F))
            return true;
        return false;
    }

    /**
     * 判断右边抽屉是否有密码
     *
     * @return
     */
    public boolean isRightHasLock() {
        if (lock_s.equals(_F) || lock_s.equals(F))
            return true;
        return false;
    }

    /**
     * 管理锁密码是否启用
     *
     * @param type
     * @param hasLock
     */
    public String codeForChangeLock(int type, boolean hasLock) {
        char temL = lock_s.charAt(0);
        char temR = lock_s.charAt(1);
        String temp_lock_s = null;
        String temp = "0";
        if (hasLock) {
            temp = "F";
        }

        if (type == LEFT) {
            temp_lock_s = temp+temR;
        }

        if (type == RIGHT) {
            temp_lock_s = temL+temp;
        }
        return SET_SEC + id + pwd_l + pwd_r + temp_lock_s + END_FLAG;
    }

    /**
     * 设置密码 新密码交给两个密码属性保管
     *
     * @param id
     * @param pwd1
     * @param pwd2
     * @param type
     * @return
     */
    public String setPassword(String id, String pwd1, String pwd2, int type) {
        String temp_lock_s = null;  // TODO: 2017/4/6 检查两个密码的有效性
        pwd_l = pwd1;
        pwd_r = pwd2;
        if (type == BOTH) {
            temp_lock_s = F;
        }
        if (type == LEFT) {
            temp_lock_s = F_;
        }
        if (type == RIGHT) {
            temp_lock_s = _F;
        }
        if (type == NONE) {
            temp_lock_s = O;
        }
        return SET_SEC + id + pwd1 + pwd2 + temp_lock_s + END_FLAG;
    }

    public String checkState() {
        Log.i("chen", "发送指令:" + CMD_EXE + id + OPT_SER + IS_RUN + END_FLAG);
        return CMD_EXE + id + OPT_SER + IS_RUN + END_FLAG;
    }


    /**
     * 正常开关抽屉的行为
     *
     * @param type
     * @param isOpen
     * @return
     */
    public String codeForDrawerOpen(int type, boolean isOpen) {
        if (type == LEFT) {
            if (isOpen) {
                drawer_l = A;
            } else {
                drawer_l = O;
            }
        }
        if (type == RIGHT) {
            if (isOpen) {
                drawer_r = A;
            } else {
                drawer_r = O;
            }
        }
        return getCommand();
    }

    /**
     * 抽屉带锁开锁
     *
     * @param type
     * @param pwd
     * @return
     */
    public String codeForDrawerUnlock(int type, String pwd) {

        if (type == LEFT) {
            drawer_l = A;
//            lock_l = A;   发送命令里面没有
            pwd_l = pwd;
        }
        if (type == RIGHT) {
            drawer_r = A;
//            lock_r = A;
            pwd_r = pwd;
        }
        return getCommand();
    }

    /**
     * 锁定指定抽屉
     *
     * @param type
     * @return
     */
    public String codeForDrawerLock(int type) {
        if (type == LEFT) {
            drawer_l = O;
//            lock_r = O;
            pwd_l = A + A;
        }
        if (type == RIGHT) {
            drawer_r = O;
//            lock_r = O;
            pwd_r = A + A;
        }
        return getCommand();
    }

    public String codeForChestOpen(int type,boolean isOpen){
        if (type == LEFT) {
            if (isOpen) {
                chest_l = A;
            } else {
                chest_l = O;
            }
        }
        if (type == RIGHT) {
            if (isOpen) {
                chest_r = A;
            } else {
                chest_r = O;
            }
        }
        return getCommand();
    }

}
