package com.aqLibs.common.Encrypt;

import com.aqLibs.common.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtils {


    /**
     * 加密，结果bytes转成对应的HexString
     */
    public static String encodeToStringHex(String str, String key, String iv) {

        String fillResult = fill16(str);
        byte[] encode = encode(fillResult.getBytes(), key, iv);
        return Hex.toHex(encode);
    }

    public static byte[] encode(byte[] bytes, String key, String iv){

        byte[] result = {};
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, ((Key)sks), ((AlgorithmParameterSpec)ips));
            result = cipher.doFinal(bytes);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String fill16(String str) {
        int len = str.length() % 16;
        for(int i = 0; i < 16 - len; ++i) {
            str = str + ' ';
        }

        return str;
    }

    public static String decodeStringHex(String str, String key, String iv){
        byte[] bytes = Hex.toByteAry(str);
        byte[] decode = decode(bytes, key, iv);
        return new String(decode);
    }

    public static byte[] decode(byte[] str, String key, String iv){

        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, sks, ips);
            str = cipher.doFinal((str));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return str;
    }
}
