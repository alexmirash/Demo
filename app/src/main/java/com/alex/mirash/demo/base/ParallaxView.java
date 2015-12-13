package com.alex.mirash.demo.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.alex.mirash.demo.R;


/**
 * @author Mirash
 */
public class ParallaxView extends FrameLayout {
    private static final float DEFAULT_PARALLAX_VALUE = 0.25f;

    private float mParallaxValue;
    private MarginLayoutParams mLayoutParams;

    public ParallaxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandButton);
            mParallaxValue = a.getFloat(R.styleable.ParallaxView_parallaxValue, DEFAULT_PARALLAX_VALUE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mLayoutParams = (MarginLayoutParams) getLayoutParams();
    }

    public void setParallaxValue(float parallaxValue) {
        mParallaxValue = parallaxValue;
    }

    public void setOffset(float offset) {
        int parallaxOffset = (int) (offset * mParallaxValue);
        mLayoutParams.leftMargin = parallaxOffset;
        mLayoutParams.rightMargin = -parallaxOffset;
        requestLayout();
    }
}
