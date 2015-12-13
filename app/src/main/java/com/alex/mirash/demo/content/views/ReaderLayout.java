package com.alex.mirash.demo.content.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alex.mirash.demo.content.ReaderGesturesDetector;
import com.alex.mirash.demo.properties.GlobalProperties;

import static com.alex.mirash.demo.utils.LogUtils.log;


/**
 * @author Mirash
 */
public class ReaderLayout extends FrameLayout implements ReaderGesturesDetector.ReaderGestureListener {
    private ReaderGesturesDetector mGestureDetector;
    private ReaderTextView mTextView;

    private boolean mIsScrollVertical = true;

    private final int DEFAULT_TEXT_SIZE = 15;

    public ReaderLayout(Context context) {
        super(context);
        init(context);
    }

    public ReaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReaderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mTextView = new ReaderTextView(context);
        mTextView.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 90;
        lp.rightMargin = 90;
        lp.topMargin = 90;
        lp.bottomMargin = 90;
        mTextView.setBackgroundColor(Color.GRAY);
        mTextView.setTextColor(Color.WHITE);
        addView(mTextView, lp);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DEFAULT_TEXT_SIZE);

        mGestureDetector = new ReaderGesturesDetector(context, this);
    }

    public void showPreviousPage() {
        log("showPrevPage");
    }

    public void showNextPage() {
        log("showNextPage");
    }

    private void handleFlingEvent(float flingDistance) {
        if (flingDistance > GlobalProperties.getMinimalFlingScrollDistance()) {
            showNextPage();
        } else if (flingDistance < -GlobalProperties.getMinimalFlingScrollDistance()) {
            showPreviousPage();
        }
    }


    public void setText(String text) {
        mTextView.setText(text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.handleTouchEvent(event);
    }

    /*~~~~~~~~~~~~~~ reader gestures events ~~~~~~~~~~~~*/
    @Override
    public boolean onScale(float scaleFactor) {
        log("scaleFactor " + scaleFactor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextView.getFreezedTextSize() * scaleFactor);
        return false;
    }

    @Override
    public boolean onScaleBegin() {
        log("onScaleBegin");
        mTextView.freezeTextSize();
        return true;
    }

    @Override
    public void onScaleEnd(float scaleFactor) {
        log("onScaleEnd " + scaleFactor);
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        log("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        log("onFling " + v + "; " + v2);
        handleFlingEvent(mIsScrollVertical ? v2 : v);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        log("onDown");
        return true;
    }
}
