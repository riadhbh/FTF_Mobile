package com.foot.basicdrawer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;

/**
 * Created by riadh on 2/13/2017.
 */

public class ExitActivity extends Activity
{
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        if(android.os.Build.VERSION.SDK_INT >= 21)
        {
            finishAndRemoveTask();
            finishAffinity();
        }
        finish();
        System.exit(0);
        Process.killProcess(Process.myPid());
        ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(getPackageName());
    }

}