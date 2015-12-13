package com.alex.mirash.demo.screens.snowfall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import com.alex.mirash.demo.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mirash
 */
public class SnowFallController implements SurfaceHolder.Callback {
    private static final int MIN_DELAY = 50;
    private static final int MAX_DELAY = 2500;
    //view size
    private int mWidth;

    private SurfaceHolder mSurfaceHolder;
    private SnowFallSurfaceView mSnowFallSurfaceView;
    private SnowFallEffectDrawTask mSnowFallTask;

    private Bitmap mSnowFlakeBitmap;
    private List<SnowDrawable> mSnowFallElements;

    private Handler mHandler;
    private Runnable mSnowFlakeCreateRunnable;

    public void init(SnowFallSurfaceView snowFallView) {
        Log.d("SNOW", "init");
        mSnowFallSurfaceView = snowFallView;
        mSnowFlakeBitmap = BitmapFactory.decodeResource(snowFallView.getResources(), R.drawable.snowflake);
        mSnowFallElements = new CopyOnWriteArrayList<>();
        SurfaceHolder surfaceHolder = mSnowFallSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void run() {
        Log.d("SNOW", "run");
        stop();
        mWidth = mSnowFallSurfaceView.getWidth();
        mSnowFallTask = new SnowFallEffectDrawTask();
        mSnowFallTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        initTimer();
    }

    public void stop() {
        if (mSnowFallTask != null) {
            mSnowFallTask.cancel(true);
            mSnowFallTask = null;
        }
        if (mHandler != null && mSnowFlakeCreateRunnable != null) {
            mHandler.removeCallbacks(mSnowFlakeCreateRunnable);
            mHandler = null;
            mSnowFlakeCreateRunnable = null;
        }
    }

    private class SnowRunnable implements Runnable {
        @Override
        public void run() {
            createSnowFlake();
            mSnowFlakeCreateRunnable = new SnowRunnable();
            mHandler.postDelayed(mSnowFlakeCreateRunnable, getDelay());
        }
    }

    private void initTimer() {
        mHandler = new Handler();
        mSnowFlakeCreateRunnable = new SnowRunnable();
        mHandler.postDelayed(mSnowFlakeCreateRunnable, getDelay());
    }

    private long getDelay() {
        return MIN_DELAY + (long) (Math.random() * MAX_DELAY);
    }

    private void drawElements(Canvas canvas) {
        for (SnowDrawable drawable : mSnowFallElements) {
            drawable.draw(canvas);
        }
    }

    private void updateElementsState() {
        for (SnowDrawable drawable : mSnowFallElements) {
            if (!drawable.updateState()) {
                mSnowFallElements.remove(drawable);
            }
        }
        Log.d("OMFG_WTF", String.valueOf(mSnowFallElements.size()));
    }

    public void createSnowFlake() {
        Log.d("SNOW", "createSnowFlake");
        mSnowFallElements.add(new SnowFlakeView(mSnowFlakeBitmap, mWidth));
    }

    //
    private class SnowFallEffectDrawTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled()) {
                updateElementsState();
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                drawElements(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
            return null;
        }
    }

    //surface callbacks
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        run();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }
}
