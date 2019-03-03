package com.aqLibs.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author: AqCxBoM
 * date: On 2019/3/2
 */
public class PmsHook extends Application implements InvocationHandler {
    private static final int GET_SIGNATURES = 64;
    private String appPkgName;
    private Object base;
    private byte[][] sign;

    public void hook(Context context){
        try{
//            byte[] decode = Base64.decode("AQAAAoMwggJ/MIIB6KADAgECAgRNaRu4MA0GCSqGSIb3DQEBBQUAMIGCMQswCQYDVQQGEwJDTjEQ\nMA4GA1UECBMHQmVpamluZzEQMA4GA1UEBxMHQmVpamluZzEkMCIGA1UEChMbU2Fua3VhaSBUZWNo\nbm9sb2d5IENvLiBMdGQuMRQwEgYDVQQLEwttZWl0dWFuLmNvbTETMBEGA1UEAxMKQ0hFTiBMaWFu\nZzAgFw0xMTAyMjYxNTI2NDhaGA8yMTExMDIwMjE1MjY0OFowgYIxCzAJBgNVBAYTAkNOMRAwDgYD\nVQQIEwdCZWlqaW5nMRAwDgYDVQQHEwdCZWlqaW5nMSQwIgYDVQQKExtTYW5rdWFpIFRlY2hub2xv\nZ3kgQ28uIEx0ZC4xFDASBgNVBAsTC21laXR1YW4uY29tMRMwEQYDVQQDEwpDSEVOIExpYW5nMIGf\nMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6Cbcs7sFaBNm5HWa6Iia1AlTnjj1Z9n5vYfBCxkfw\nF+vIeZlUiiRNQFnR2HJOefcc70VvcawG4+wSiWR0bm8UC3WiOEH6G642kNzasM9G+1S15q9LYaF3\ndSP4GQE30Y3TVy9J3KlA9q0rWdjnw5q2KEqTe+MbpPkgv6mbMUltdQIDAQABMA0GCSqGSIb3DQEB\nBQUAA4GBAEip356jB7rL8yFDF9A+amWKNNU6FM/apxq1wFzpIEEx6+0mQAW8xCvCwMhuj44AWUCZ\n9u9iOU3uBRpxIAb97t/hclXTgoAVjZobjUBWzD2rSdmCG516FcHXkjegESzIDz2G9ER3n94490MN\nDwxrtfujB+r8HmAcQ8AiL90ArSL4\n", 0);
            byte[] decode = Base64.decode("MIICfzCCAeigAwIBAgIETWkbuDANBgkqhkiG9w0BAQUFADCBgjELMAkGA1UEBhMCQ04xEDAOBgNVBAgTB0JlaWppbmcxEDAOBgNVBAcTB0JlaWppbmcxJDAiBgNVBAoTG1Nhbmt1YWkgVGVjaG5vbG9neSBDby4gTHRkLjEUMBIGA1UECxMLbWVpdHVhbi5jb20xEzARBgNVBAMTCkNIRU4gTGlhbmcwIBcNMTEwMjI2MTUyNjQ4WhgPMjExMTAyMDIxNTI2NDhaMIGCMQswCQYDVQQGEwJDTjEQMA4GA1UECBMHQmVpamluZzEQMA4GA1UEBxMHQmVpamluZzEkMCIGA1UEChMbU2Fua3VhaSBUZWNobm9sb2d5IENvLiBMdGQuMRQwEgYDVQQLEwttZWl0dWFuLmNvbTETMBEGA1UEAxMKQ0hFTiBMaWFuZzCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAugm3LO7BWgTZuR1muiImtQJU5449WfZ+b2HwQsZH8BfryHmZVIokTUBZ0dhyTnn3HO9Fb3GsBuPsEolkdG5vFAt1ojhB+huuNpDc2rDPRvtUteavS2Ghd3Uj+BkBN9GN01cvSdypQPatK1nY58OatihKk3vjG6T5IL+pmzFJbXUCAwEAATANBgkqhkiG9w0BAQUFAAOBgQBIqd+eowe6y/MhQxfQPmplijTVOhTP2qcatcBc6SBBMevtJkAFvMQrwsDIbo+OAFlAmfbvYjlN7gUacSAG/e7f4XJV04KAFY2aG41AVsw9q0nZghudehXB15I3oBEsyA89hvREd5/eOPdDDQ8Ma7X7owfq/B5gHEPAIi/dAK0i+A==", 0);
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(decode));
            byte[][] byss = new byte[dis.read() & 255][];
            for (int i = 0; i < byss.length; i++){
                byss[i] = new byte[dis.readInt()];
                dis.readFully(byss[i]);
            }

            Class clzActivityThread = Class.forName("android.app.ActivityThread");
            Object objCurrentActivityThread = clzActivityThread.getDeclaredMethod("currentActivityThread").invoke(null);
            Field fieldPackageManager = clzActivityThread.getDeclaredField("sPackageManager");
            fieldPackageManager.setAccessible(true);
            Object objPackageManager = fieldPackageManager.get(objCurrentActivityThread);
            Class clzPackageManager = Class.forName("android.content.pm.IPackageManager");
            this.base = objPackageManager;
            this.sign = byss;
            this.appPkgName = context.getPackageName();
            Object v13 = Proxy.newProxyInstance(clzPackageManager.getClassLoader(), new Class[]{clzPackageManager}, this);
            fieldPackageManager.set(objCurrentActivityThread, v13);
            PackageManager pm = context.getPackageManager();
            Field v11 = pm.getClass().getDeclaredField("mPM");
            v11.setAccessible(true);
            v11.set(pm, v13);
            System.out.println("PmsHook success.");
        }
        catch (Exception e){
            System.err.println("PmsHook failed.");
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object pi;
        if ("getPackageInfo".equals(method.getName()) &&
                (((Integer) (objects[1]) & GET_SIGNATURES) != 0) ){
            Object pkgName = objects[0];
            if (appPkgName.equals(pkgName)){
                pi = method.invoke(base, objects);
                ((PackageInfo) pi).signatures = new Signature[sign.length];
                for (int i = 0; i < ((PackageInfo)pi).signatures.length; i++){
                    ((PackageInfo)pi).signatures[i] = new Signature(sign[i]);
                }
                return pi;
            }
        }

        pi = method.invoke(base, objects);

        return pi;
    }
}
