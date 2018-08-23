package com.aqLibs.permission;

/**
 * author: AqCxBoM
 * date: On 2018/8/22
 */

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermisstionUtils {
    public static String[] checkPermission(Activity act, String[] pers) {
        String[] result = null;
        if(Build.VERSION.SDK_INT < 23) {
            return result;
        }

        ArrayList<String> list = new ArrayList();
        for (String per:pers){
            if (ContextCompat.checkSelfPermission(act, per) != 0){
                list.add(per);
            }
        }

        int size = list.size();
        if(size < 1) {
            return result;
        }

        return list.toArray(new String[size]);
    }
}
