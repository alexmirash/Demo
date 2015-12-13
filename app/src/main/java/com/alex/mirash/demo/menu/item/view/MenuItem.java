package com.alex.mirash.demo.menu.item.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.mirash.demo.R;


/**
 * @author Mirash
 */
public abstract class MenuItem extends FrameLayout implements ISizeDefined {
    protected ImageView mIconView;
    protected TextView mTitleView;

    public MenuItem(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, getLayoutId(), this);
        mIconView = (ImageView) findViewById(R.id.menu_item_icon);
        mTitleView = (TextView) findViewById(R.id.menu_item_title);
        initElements();
    }

    protected void initElements() {
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setImageResource(int resourceId) {
        mIconView.setImageResource(resourceId);
    }

    abstract protected int getLayoutId();

}
