package com.alex.mirash.demo;

import android.app.Application;
import android.content.Context;

import com.alex.mirash.demo.properties.GlobalProperties;
import com.alex.mirash.demo.utils.LogUtils;
import com.alex.mirash.demo.utils.MirashUtils;

/**
 * @author Mirash
 */
public class MirashApplication extends Application {
    private static MirashApplication mInstance;
    private GlobalProperties mGlobalProperties;

    public static MirashApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static GlobalProperties getGlobalProperties() {
        return mInstance.mGlobalProperties;
    }

    @Override
    public void onCreate() {
        LogUtils.log("application onCreate");
        super.onCreate();
        mInstance = this;
        mGlobalProperties = MirashUtils.getDefaultGlobalProperties();
    }


}
