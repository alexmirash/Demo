package com.alex.mirash.demo.screens.rain.old;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RainDropView extends SurfaceView implements SurfaceHolder.Callback {
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

    private RainDropThreadOld mRainDropThread;

    int index,
            refractionX,
            refrectionY;
    int mPixelsCount;

    short mRippleMap[];
    int mOriginBitmapPixels[];
    int mRipplePixels[];

    int mOldIndex,
            mNewIndex,
            mapInd;

    int rippleRadius = 6;
    int wavePowerMax = 1024;
    int wavePowerInit = 512;
    int damping = 5;

    boolean dragMode = false;
    boolean pause = false;
    boolean raining = false;


    public RainDropView(Context context, int imageResourceId) {
        super(context);
        init(imageResourceId);
    }

    private void init(int imageResourceId) {
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        mRainDropThread = new RainDropThreadOld(surfaceHolder, this);

        mOriginBitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);
        mRippleBitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("RAIN", "onMeasure");
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mScaleX = (double) mOriginBitmapWidth / mWidth;
        mScaleY = (double) mOriginBitmapHeight / mHeight;
    }

    //imitation of the ripple effect. Changes all necessary pixels before canvas' repainting
    public void nextFrame() {
        time = System.currentTimeMillis();
        index = mOldIndex;
        mOldIndex = mNewIndex;
        mNewIndex = index;
        index = 0;
        mapInd = mOldIndex;
        short wavePower;
        mShouldDraw = false;
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
                Log.d("RAIN", "refraction : " + x + ", " + refractionX + "; " + y + ", " + refrectionY);

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
        Log.d("RAIN", "nextframetime: " + (System.currentTimeMillis() - time));
    }

    private static long time;
    private boolean mShouldDraw;

    public void drawRainMatrix(Canvas canvas) {
        time = System.currentTimeMillis();
        mRippleBitmap = Bitmap.createBitmap(mRipplePixels, mOriginBitmapWidth, mOriginBitmapHeight, Config.RGB_565);
        mRippleBitmap = Bitmap.createScaledBitmap(mRippleBitmap, mWidth, mHeight, true);
        Log.d("RAIN", "bitmapcreatetime: " + (System.currentTimeMillis() - time));
        if (!pause) {
            if (raining) {
                onTouchScreen((int) (Math.random() * mOriginBitmapWidth), (int) (Math.random() * mOriginBitmapHeight));
            }
            nextFrame();
        }
        time = System.currentTimeMillis();
        canvas.drawBitmap(mRippleBitmap, 0, 0, null);
        Log.d("RAIN", "bitmapdraw: " + (System.currentTimeMillis() - time));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!pause) onTouchScreen((int) (event.getX() * mScaleX), (int) (event.getY() * mScaleY));

        return dragMode;
    }


    //initialize wave power of area with center in touch position and ripple radius
    public void onTouchScreen(int cX, int cY) {
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

    public void setRippleSize(int rad) {
        rippleRadius = rad;
    }

    public void pause() {
        pause = !pause;
    }


    public void changeTouchMode() {
        dragMode = !dragMode;
    }

    public void setRaining() {
        raining = !raining;
    }

    //surface callbacks
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRainDropThread.setIsRunning(true);
        mRainDropThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mRainDropThread.setIsRunning(false);
        while (retry) {
            try {
                mRainDropThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
