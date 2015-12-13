package com.alex.mirash.demo.content;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;


/**
 * @author Mirash
 */
public class ReaderGesturesDetector {
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    private ReaderGestureListener mListener;

    private boolean mIsScaleEnabled = true;

    public ReaderGesturesDetector(Context context, ReaderGestureListener listener) {
        mListener = listener;
        initGestureDetector(context);
        initScaleDetectorDetector(context);
    }

    private void initScaleDetectorDetector(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return mListener.onScale(detector.getScaleFactor());
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return mListener.onScaleBegin();
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                mListener.onScaleEnd(detector.getScaleFactor());
            }
        });
    }

    private void initGestureDetector(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return mListener.onDown(motionEvent);
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                mListener.onLongPress(motionEvent);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return mListener.onSingleTapUp(motionEvent);
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return mListener.onScroll(motionEvent, motionEvent2, v, v2);
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                return mListener.onFling(motionEvent, motionEvent2, v, v2);
            }
        });
    }

    public boolean handleTouchEvent(MotionEvent event) {
        if (mIsScaleEnabled) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    public interface ReaderGestureListener {
        boolean onScale(float scaleFactor);

        boolean onScaleBegin();

        void onScaleEnd(float scaleFactor);

        boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2);

        boolean onSingleTapUp(MotionEvent motionEvent);

        boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2);

        void onLongPress(MotionEvent motionEvent);

        boolean onDown(MotionEvent motionEvent);
    }
}
