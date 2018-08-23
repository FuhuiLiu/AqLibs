package com.aqLibs.permission;

/**
 * author: AqCxBoM
 * date: On 2018/8/22
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.sankuai.aqlibs.R;

public class PermissionActivity extends BaseActivity {
    public static final String[] permissions =
            new String[]{
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.VIBRATE",
                    "android.permission.CAMERA"};
    private static final int PERMISSION_REQUEST_CODE = 0x101;
    private String[] permissions_need;
    private static Activity mContext;
    private void startOrgLaunchActivity() {
        Intent intent = new Intent();
        intent.setClassName(this,getString(R.string.org_launchActivity_path));
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayout() {
        return R.layout.acitivity_permission;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mContext = this;
        permissions_need = PermisstionUtils.checkPermission(this, permissions);
        if((permissions_need == null || permissions_need.length == 0)) {
            startOrgLaunchActivity();
            return;
        }

        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if(permissions.length != 0) {
                    ActivityCompat.requestPermissions(PermissionActivity.mContext,
                            permissions, PERMISSION_REQUEST_CODE);
                }

            }
        });

    }
    public void onRequestPermissionsResult(int code, String[] str, int[] ints) {
        if(code == PERMISSION_REQUEST_CODE) {
            startOrgLaunchActivity();
        }
    }
}