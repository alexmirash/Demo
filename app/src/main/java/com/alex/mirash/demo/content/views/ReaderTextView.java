package com.alex.mirash.demo.content.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Mirash
 */
public class ReaderTextView extends TextView {
    private float mFreezedTextSize;

    public ReaderTextView(Context context) {
        super(context);
    }

    public ReaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReaderTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public float getFreezedTextSize() {
        return mFreezedTextSize;
    }

    public void freezeTextSize() {
        mFreezedTextSize = getTextSize();
    }
}
