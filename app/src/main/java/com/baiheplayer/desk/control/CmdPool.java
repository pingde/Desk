package com.baiheplayer.desk.control;

/**
 * 命令池
 * 用或的关系和上一条信息比对
 * <p>
 * Created by Administrator on 2017/3/3.
 */
public interface CmdPool {
    int NONE = 0;
    int LEFT = 1;
    int RIGHT = 2;
    int BOTH = 3;

    String A = "AA";
    String O = "00";
    String F_ = "F0";
    String _F = "0F";
    String F = "FF";
    /**
     * 来自设备的数据  A55A    3D           00000001  AA      AA      AA FD      AA     AA    0F       32    FF   0D
     * 数据帧头  数据显示指令  设备号    1号抽屉  2号抽屉  灯带及颜色  1号锁  2号锁  抽屉警报  电量  校验  结束帧头
     * <p>
     * 来自设备的复位  A55A     BA          00000001  88            0D
     * 数据帧头  数据显示指令  设备号    请求一键复位    结束帧头
     */
    String DEVICE_STATE = "A5 5A 3D 00 00 00 01 AA AA AA FD AA AA 0F 32 FF 0D";

    /**
     * 设置数据格式    A55A     0B       00000001   04 03        05 03       0D
     * 开始帧头  数据指令   设备id号   1号抽屉密码   2号抽屉密码   结束帧头
     * <p>
     * 发送数据格式    A55A     D3       00000001   B0           00           00          AA       55      AAAA    AAAA    0D
     * 开始帧头  控制指令   设备id号   数据指令      1号抽屉开关   2号抽屉开关  灯带开关  颜色值   1锁密码 2锁密码  数据帧尾
     * <p>
     * 确定复位格式   A55A      D3       00000001   BA           88           0D
     * 开始帧头  控制指令   设备id号   复位数据指令   回应复位     结束帧头
     * <p>
     * 查询设备参数   A55A      D3       00000001   A0           F1（F2）         0D
     * 开始帧头  控制指令   设备id号   查询指令      查询设备运行状态  数据帧尾
     */

    // A5 5A 3D 00 00 00 01 AA AA AA FD AA AA 0F 32 FF 0D
    String DEVICE_BASIC = "A55A0B00000001040305030D";
    String OB_DVS = "A55A3D";    //监听设备
    String SET_SEC = "A55A0B";   //设置设备
    String CMD_EXE = "A55AD3";   //发送指令
    String RES_OBS = "A55ABA";   //复位指令帧头
    String OPT_DAT = "B0";       //数据操作指令
    String END_FLAG = "0D";      //结束帧头
    String OPT_SER = "A0";       //查询设备指令
    String IS_RUN = "F1";        //设备运行状态
    String IS_SEC = "F2";        //设设备密码信息

    String DEVICE_ID = "00000001";

    String LV_NA = "C4C4C4";    //200 200 200
    String LV_BL = "6E6EFF";    //110 110 255
    String LV_PI = "FF6464";    //255 100 100
    String LV_0 = "F2ECd2";    //255 100 100
    String LV_1 = "ECDFA6";    //255 100 100
    String LV_2 = "F4C690";    //255 100 100
    String LV_3 = "F3A782";    //255 100 100
    String LV_4 = "EC75A5";    //255 100 100
    String LV_5 = "EC7575";    //255 100 100
    String LV_6 = "F0A3A3";    //255 100 100
    String LV_7 = "F8AFC0";    //255 100 100
    String LV_8 = "C96CEB";    //255 100 100
    String LV_9 = "A26CEB";    //255 100 100
    String LV_10 = "7B6CEB";    //255 100 100
    String LV_11 = "6C96EB";    //255 100 100
    String LV_12 = "B6EB8F";    //255 100 100
    String LV_13 = "6CEBA5";    //255 100 100
    String LV_14 = "6CEBD2";    //255 100 100
    String LV_15 = "6CD2EB";    //255 100 100

    String LV_AI = "0F";



    void onFresh(String info);
}
