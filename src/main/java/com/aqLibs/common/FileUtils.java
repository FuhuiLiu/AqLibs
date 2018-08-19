package com.aqLibs.common;

import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 写入数据到文件
     * @param filePath 文件路径
     * @param context 写入内容
     * @return 写入结果
     */
    public static boolean writeFile(String filePath, String context)
            throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            boolean mkdir = parentFile.mkdir();
        }

        if (!file.exists())
            file.createNewFile();
        StringBuffer sb = new StringBuffer();
        sb.append(context);
        //写入文件
        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(sb.toString().getBytes());
        fos.close();
        return true;
    }

    /**
     * 读取参数路径的文件内容并返回
     * @param path 文件路径
     * @return 文件内容 或者空
     */
    public static String readFile(String path) throws IOException {

        FileInputStream fis = new FileInputStream(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] byteAry = new byte[1024];

        int nRead;
        do{
            nRead = fis.read(byteAry);
            if(nRead > 0)
                baos.write(byteAry, 0, nRead);
        }while (nRead > 0);

        return baos.toString();
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    public static ArrayList<String> getAllFiles(String dirPath, String _type) {
        ArrayList<String> result = new ArrayList<>();
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return result;
        }

        File[] files = f.listFiles();

        if(files==null){//判断权限
            return result;
        }

        for (File _file : files) {//遍历目录
            if(_file.isFile() && _file.getName().endsWith(_type)){
                String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
                result.add(filePath);
            } else if(_file.isDirectory()){//查询子目录
                ArrayList<String> allFiles = getAllFiles(_file.getAbsolutePath(), _type);
                result.addAll(allFiles);
            } else{
            }
        }
        return result;
    }
}
