package com.alex.mirash.demo.menu.item.view;

import android.content.Context;
import android.content.res.Resources;

import com.alex.mirash.demo.R;


/**
 * @author Mirash
 */
public class MenuItemSub extends MenuItem {
    public MenuItemSub(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.navigation_menu_sub_item;
    }

    @Override
    public int getDefinedHeight(Resources res) {
        return res.getDimensionPixelSize(R.dimen.navigation_menu_sub_item_height);
    }
}
