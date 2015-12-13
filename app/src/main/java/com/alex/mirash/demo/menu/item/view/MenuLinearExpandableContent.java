package com.alex.mirash.demo.menu.item.view;

import android.content.Context;
import android.content.res.Resources;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */
public class MenuLinearExpandableContent extends LinearLayout implements ISizeDefined {
    private List<ISizeDefined> mItems;

    public MenuLinearExpandableContent(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        mItems = new ArrayList<ISizeDefined>(2);
    }

    public void addItem(MenuItem view) {
        addView(view);
        mItems.add(view);
    }

    @Override
    public int getDefinedHeight(Resources res) {
        int height = 0;
        for (ISizeDefined item : mItems) {
            height += item.getDefinedHeight(res);
        }
        return height;
    }
}
