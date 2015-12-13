package com.alex.mirash.demo.properties;


import com.alex.mirash.demo.MirashApplication;
import com.alex.mirash.demo.properties.local.ScrollProperties;

/**
 * @author Mirash
 */
public class GlobalProperties {
    private ScrollProperties mScrollProperties;

    public GlobalProperties() {
        mScrollProperties = new ScrollProperties();
    }

    public static float getMinimalFlingScrollDistance() {
        return MirashApplication.getGlobalProperties().mScrollProperties.getMinimalFlingSwapPageDistance();
    }
}
