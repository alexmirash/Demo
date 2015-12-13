package com.alex.mirash.demo.screens.rain.old;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class RainDropThreadOld extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private RainDropView mRainDropView;
    private boolean mIsRunning;

    public RainDropThreadOld(SurfaceHolder holder, RainDropView view) {
        mRainDropView = view;
        mSurfaceHolder = holder;
    }

    public void setIsRunning(boolean isRunning) {
        Log.d("RAIN", "set is running = " + isRunning);
        mIsRunning = isRunning;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (mIsRunning) {
            try {
                canvas = mSurfaceHolder.lockCanvas();
                mRainDropView.drawRainMatrix(canvas);
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}