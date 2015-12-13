package com.alex.mirash.demo.screens.rain;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * @author Mirash
 */
public class RippleEffectController implements SurfaceHolder.Callback {
    //view size
    private int mWidth;
    private int mHeight;
    //origin bitmap size

    private int mOriginBitmapWidth;
    private int mOriginBitmapHeight;

    //shifted sizes of bitmap
    private int mShiftWidth;
    private int mShiftHeight;

    private Bitmap mOriginBitmap;
    private Bitmap mRippleBitmap;

    private double mScaleX;
    private double mScaleY;

    int index;
    int refractionX;
    int refrectionY;
    int mPixelsCount;

    short mRippleMap[];
    int mOriginBitmapPixels[];
    int mRipplePixels[];

    int mOldIndex;
    int mNewIndex;
    int mapInd;

    int rippleRadius = 9;
    int wavePowerMax = 1024;
    int wavePowerInit = 512;
    int damping = 5;

    boolean dragMode = true;
    boolean pause = false;
    boolean raining = false;

    private RainView mRainView;
    private RippleEffectTask mRippleTask;

    public void init(RainView rainView, int imageResourceId) {
        mRainView = rainView;
        initTouchListener();
        Resources res = mRainView.getResources();

        SurfaceHolder surfaceHolder = mRainView.getHolder();
        surfaceHolder.addCallback(this);

        mOriginBitmap = BitmapFactory.decodeResource(res, imageResourceId);
        mRippleBitmap = BitmapFactory.decodeResource(res, imageResourceId);

        mOriginBitmapWidth = mOriginBitmap.getWidth();
        mOriginBitmapHeight = mOriginBitmap.getHeight();

        mPixelsCount = mOriginBitmapWidth * (mOriginBitmapHeight + 2) * 2;  //TODO 2?
        mRippleMap = new short[mPixelsCount];

        mOriginBitmapPixels = new int[mOriginBitmapWidth * mOriginBitmapHeight];
        mOriginBitmap.getPixels(mOriginBitmapPixels, 0, mOriginBitmapWidth, 0, 0, mOriginBitmapWidth, mOriginBitmapHeight);

        mRipplePixels = new int[mOriginBitmapWidth * mOriginBitmapHeight];
        mRippleBitmap.getPixels(mRipplePixels, 0, mOriginBitmapWidth, 0, 0, mOriginBitmapWidth, mOriginBitmapHeight);

        mShiftWidth = mOriginBitmapWidth >> 1;
        mShiftHeight = mOriginBitmapHeight >> 1;
        mOldIndex = mOriginBitmapWidth;
        mNewIndex = mOriginBitmapWidth * (mOriginBitmapHeight + 3);
    }

    //imitation of the ripple effect. Changes all necessary pixels before canvas' repainting
    public void nextFrame() {
        index = mOldIndex;
        mOldIndex = mNewIndex;
        mNewIndex = index;
        index = 0;
        mapInd = mOldIndex;
        short wavePower;
        for (int y = 0; y < mOriginBitmapHeight; y++) {
            for (int x = 0; x < mOriginBitmapWidth; x++) {
                wavePower = (short) ((mRippleMap[mapInd - mOriginBitmapWidth] + mRippleMap[mapInd + mOriginBitmapWidth] + mRippleMap[mapInd - 1] + mRippleMap[mapInd + 1]) >> 1);
                wavePower -= mRippleMap[mNewIndex + index];
                wavePower -= wavePower >> damping;
                mRippleMap[mNewIndex + index] = wavePower;

                //if wavePower equals 0 then pixel stands still,
                //else calculate offset and calculate wave
                wavePower = (short) (wavePowerMax - wavePower);

                //calculate offsets
                refractionX = ((x - mShiftWidth) * wavePower / wavePowerMax) + mShiftWidth;
                refrectionY = ((y - mShiftHeight) * wavePower / wavePowerMax) + mShiftHeight;

                //check bounds
                if (refractionX >= mOriginBitmapWidth) refractionX = mOriginBitmapWidth - 1;
                if (refractionX < 0) refractionX = 0;
                if (refrectionY >= mOriginBitmapHeight) refrectionY = mOriginBitmapHeight - 1;
                if (refrectionY < 0) refrectionY = 0;

                //change current pixel
                mRipplePixels[index] = mOriginBitmapPixels[refractionX + (refrectionY * mOriginBitmapWidth)];

                mapInd++;
                index++;
            }
        }
    }

    public void initTouchListener() {
        mRainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!pause) {
                    createRainDrop((int) (event.getX() * mScaleX), (int) (event.getY() * mScaleY));
                }
                return dragMode;
            }
        });
    }


    //initialize wave power of area with center in touch position and ripple radius
    public void createRainDrop(int cX, int cY) {
        for (int j = cY - rippleRadius; j < cY + rippleRadius; j++) {
            for (int k = cX - rippleRadius; k < cX + rippleRadius; k++) {
                if (j >= 0 && j < mOriginBitmapHeight && k >= 0 && k < mOriginBitmapWidth && distance(k, j, cX, cY) < rippleRadius) {
                    mRippleMap[mOldIndex + (j * mOriginBitmapWidth) + k] += wavePowerInit;
                }
            }
        }
    }


    //returns distance between two points
    public static double distance(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private SurfaceHolder mSurfaceHolder;

    //surface callbacks
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }

    public void run() {
        mWidth = mRainView.getWidth();
        mHeight = mRainView.getHeight();

        mScaleX = (double) mOriginBitmapWidth / mWidth;
        mScaleY = (double) mOriginBitmapHeight / mHeight;
        if (mRippleTask != null) {
            mRippleTask.cancel(true);
        }
        mRippleTask = new RippleEffectTask();
        mRippleTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mRippleTask != null) {
            mRippleTask.cancel(true);
            mRippleTask = null;
        }
    }

    //
    private class RippleEffectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled()) {
                mRippleBitmap = Bitmap.createBitmap(mRipplePixels, mOriginBitmapWidth, mOriginBitmapHeight, Bitmap.Config.RGB_565);
                mRippleBitmap = Bitmap.createScaledBitmap(mRippleBitmap, mWidth, mHeight, true);
                if (!pause) {
                    if (raining) {
                        createRainDrop((int) (Math.random() * mOriginBitmapWidth), (int) (Math.random() * mOriginBitmapHeight));
                    }
                    nextFrame();
                }
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawBitmap(mRippleBitmap, 0, 0, null);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... bitmap) {
        }
    }
}
