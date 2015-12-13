package com.alex.mirash.demo.screens.snowfall;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * @author Mirash
 */
public class SnowFallSurfaceView extends SurfaceView {
    public SnowFallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }
}
