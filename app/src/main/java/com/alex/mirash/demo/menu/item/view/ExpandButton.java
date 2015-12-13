package com.alex.mirash.demo.menu.item.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alex.mirash.demo.R;


/**
 * @author Mirash
 */
public class ExpandButton extends ImageView {
    private Drawable mExpandedResourceId;
    private Drawable mCollapsedResourceId;

    public ExpandButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandButton);
            mExpandedResourceId = a.getDrawable(R.styleable.ExpandButton_expandedResourceId);
            mCollapsedResourceId = a.getDrawable(R.styleable.ExpandButton_collapsedResourceId);
        }
    }

    public void setExpandedState(boolean isExpanded) {
        setImageDrawable(isExpanded ? mExpandedResourceId : mCollapsedResourceId);
    }
}
