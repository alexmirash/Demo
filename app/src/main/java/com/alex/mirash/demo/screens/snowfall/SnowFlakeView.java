package com.alex.mirash.demo.screens.snowfall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * @author Mirash
 */
public class SnowFlakeView implements SnowDrawable {
    private static final float MAX_ANGLE_DEGREES = 70;
    private static final float DY_INCREASE_DISPERSION = 2.25f;
    private static final float MIN_DY = 1f;

    private float mDy;
    private Bitmap mPicture;
    private Matrix mMatrix;

    private int mSteps;
    private int mLiveSteps;

    public SnowFlakeView(Bitmap picture, float w) {
        mPicture = picture;
        mMatrix = new Matrix();
        float scaleFactor = 0.5f + (float) (Math.random()) * 1.5f;
        float angle = (float) ((Math.random() - 0.5f) * MAX_ANGLE_DEGREES);
        float startX = (float) (w * Math.random() - mPicture.getWidth() * scaleFactor / 2);
        float startY = -mPicture.getHeight() * scaleFactor;
        mDy = MIN_DY + (float) (Math.random() * DY_INCREASE_DISPERSION);
        if (angle < 0) {
            mLiveSteps = (int) (((w - startX) / Math.sin(Math.toRadians(-angle))) / (mDy * scaleFactor));
        } else {
            mLiveSteps = (int) (((startX + mPicture.getWidth() * scaleFactor) / Math.sin(Math.toRadians(angle))) / (mDy * scaleFactor));
        }
        mMatrix.postScale(scaleFactor, scaleFactor);
        mMatrix.postRotate(angle, mPicture.getWidth() * scaleFactor / 2, mPicture.getHeight() * scaleFactor / 2);
        mMatrix.postTranslate(startX, startY);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mPicture, mMatrix, null);
    }

    @Override
    public boolean updateState() {
        mMatrix.preTranslate(0, mDy);
        return mSteps++ < mLiveSteps;
    }
}
