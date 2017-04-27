package com.baiheplayer.desk.control;

/**
 * Created by Administrator on 2017/3/3.
 */

public class HexTool {

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    //A55A00000001040305030D

    /**
     * "A55AD300000001B0AAAA0055AAAA0D"
     */
    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ","");
        s = s.toUpperCase();
        int len = s.length() / 2;
        byte[] d = new byte[len];
        for (int i = 0; i < len; i++) {
            char a = s.charAt(2 * i);
            char b = s.charAt(2 * i + 1);
            int sum = getInt(a) * 16 + getInt(b);
            sum = sum & 0xFF;
            d[i] = (byte) sum;
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static int getInt(char c) {
        switch (c) {
            case '0':return 0;
            case '1':return 1;
            case '2':return 2;
            case '3':return 3;
            case '4':return 4;
            case '5':return 5;
            case '6':return 6;
            case '7':return 7;
            case '8':return 8;
            case '9':return 9;
            case 'A':return 10;
            case 'B':return 11;
            case 'C':return 12;
            case 'D':return 13;
            case 'E':return 14;
            case 'F':return 15;
        }
        return 0;
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}