package com.aqLibs.permission;

/**
 * author: AqCxBoM
 * date: On 2018/8/22
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    protected Handler handler;

    public BaseActivity() {
        super();
    }

    public void ShowToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    protected abstract int getLayout();

    protected void onCreate(Bundle arg1) {
        super.onCreate(arg1);
        handler = new Handler();
        setContentView(getLayout());
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}