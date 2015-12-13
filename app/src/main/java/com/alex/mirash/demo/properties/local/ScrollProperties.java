package com.alex.mirash.demo.properties.local;

/**
 * @author Mirash
 */
public class ScrollProperties {
    private ScrollType mScrollType;
    private int mScrollEffectDuration;
    private float mMinimalFlingSwapPageDistance;

    public ScrollProperties() {
        mScrollType = ScrollType.VERIICAL;
        mScrollEffectDuration = 500;
        mMinimalFlingSwapPageDistance = 300;
    }

    public float getMinimalFlingSwapPageDistance() {
        return mMinimalFlingSwapPageDistance;
    }

    public enum ScrollType {
        HORIZONTAl,
        VERIICAL;

        public boolean isVertical() {
            return VERIICAL.equals(this);
        }
    }
}
