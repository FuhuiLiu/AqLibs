package com.aqLibs.common;

/**
 * Created by AqCxBoM on 2017/11/4.
 */
public class Hex {
    private static byte byte2Hex(byte byteArg)
    {
        byte v0;
        //           'a'             'f'
        if (byteArg < 97 || byteArg > 102)
        {
            //            'F'              'A'
            if (byteArg <= 70 && byteArg >= 65)//byte在 'A' - 'F' 之间的转成相应 A-F
            {
                return ((byte) (byteArg - 55));
            }
            //            '9'               '0'//byte在 '0' - '9' 之间的转成相应 0-9
            if (byteArg <= 57 && byteArg >= 48)
            { //                       '0'
                return ((byte) (byteArg - 48));
            }

            v0 = 0;
        }
        else //byte在 'a' - 'f' 之间的转成相应 a-f
        {//                       ''
            v0 = ((byte) (byteArg - 87));
        }

        return v0;
    }

    public static byte[] toByteAry(String strAry) {
        byte[] byteAry = strAry.getBytes();
        int byteLen = byteAry.length / 2;
        byte[] temp = new byte[byteLen];
        int i;
        for(i = 0; i < byteLen; ++i) {
            temp[i] = ((byte)(byte2Hex(byteAry[i * 2]) << 4 | byte2Hex(byteAry[i * 2 + 1])));
        }

        return temp;
    }

    /**
     * 转换字节数据为对应的16进制字符串
     * @param byAry
     * @return
     */
    public static String toHex(byte[] byAry) {
        //要将每一位的byte转成对应的16进制值，所以空间要扩展1倍
        StringBuffer sb = new StringBuffer(byAry.length << 1);
        try {
            //StringBuffer的append方法方式后面的转换
            for (int i = 0; i < byAry.length; i++) {
                //取高位转成对应的16进制
                char bHight = Character.forDigit((byAry[i] & 0xf0) >> 4, 16);
                //取低位转成对应的16进制
                char bLow = Character.forDigit(byAry[i] & 0xf, 16);
                sb.append(bHight);
                sb.append(bLow);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 转换byte数组为对应16进制的字符串
     * @param mSignature
     * @return
     */
    public static String toHex2(byte[] mSignature) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mSignature.length; i++) {
            byte bt = mSignature[i];
            String str = String.format("%02x", bt);
            sb.append(str);
        }
        return sb.toString();
    }
}