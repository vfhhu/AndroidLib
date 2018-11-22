package xyz.vfhhu.lib.android.utils;

import java.nio.ByteBuffer;

/**
 * Created by leo3x on 2018/4/20.
 */

public class HexUtil {
    public static String byte2hex(byte[] b,String split,boolean isUpperCase){ //二行制转字符串
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++){
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if(isUpperCase)stmp=stmp.toUpperCase();
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1)  hs=hs+split;
        }
        return hs;
    }
    public static byte[] hex2byte(String s) {
        s=hexStringFormate(s);
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    public static String hexStringFormate(String s){
        return s.replaceAll("[^0-9A-Fa-f]", "");
    }


    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }
    public static byte[] longToByte8(long lo) {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((lo >>> offset) & 0xFF);
        }
        return targets;
    }

    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }
    public static byte unsignedIntToByte1(int s) {
        byte targets = (byte) (s & 0xFF);
        return targets;
    }


    public static int byte2ToUnsignedShort(byte[] bytes) {
        return byte2ToUnsignedShort(bytes, 0);
    }

    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int high = bytes[off];
        int low = bytes[off + 1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }
    public static int byte4ToInt(byte[] bytes, int off) {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }
    public static int byteToInt(byte[] bytes, int off) {
        if(off>0){
            byte[] tmp=new byte[bytes.length-off];
            System.arraycopy(bytes,off,tmp,0,(bytes.length-off));
            bytes=tmp;
        }
        if(bytes.length<4){
            byte[] tmp=new byte[4];
            System.arraycopy(bytes,0,tmp,(4-bytes.length),bytes.length);
            bytes=tmp;
        }
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getInt();

    }
    public static long byteToLong(byte[] bytes, int off) {
        if(off>0){
            byte[] tmp=new byte[bytes.length-off];
            System.arraycopy(bytes,off,tmp,0,(bytes.length-off));
            bytes=tmp;
        }
        if(bytes.length<8){
            byte[] tmp=new byte[8];
            System.arraycopy(bytes,0,tmp,(8-bytes.length),bytes.length);
            bytes=tmp;
        }
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();



//        int arr[]=new int[8];
//        for(int i:arr){
//            arr[i]=bytes[i+off] & 0xFF;
//        }
//        return (arr[0] << 56) | (arr[1] << 48) | (arr[2] << 40) | (arr[3] << 32) | (arr[4] << 24) | (arr[5] << 16) | (arr[6] << 8) | arr[7];

    }
    public static int byteToUnsignedInt(byte bytes) {
        return  bytes & 0xFF;
    }


}
