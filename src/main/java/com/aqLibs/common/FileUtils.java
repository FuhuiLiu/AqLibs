package com.aqLibs.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileUtils {

    public static String readFile(String path){

        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int nRead;
            do {
                nRead = fis.read(bytes);
                if (nRead > 0)
                    baos.write(bytes, 0, nRead);
            }while (nRead > 0);

            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
