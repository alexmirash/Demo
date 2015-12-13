package com.alex.mirash.demo.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.menu.item.MenuItemClickListener;
import com.alex.mirash.demo.menu.item.strategy.MenuNavigable;
import com.alex.mirash.demo.menu.item.strategy.NavigationMenuMainItem;
import com.alex.mirash.demo.menu.item.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mirash
 */
public class MenuItemsContainer extends FrameLayout {
    private List<MenuNavigable> mItems;
    private ViewGroup mItemsContainer;
    private MenuItemClickListener mMenuItemClickListener;
    private MenuItemClickListener mOnMenuItemClickListener = new MenuItemClickListener() {
        @Override
        public void onMenuItemClick(MenuNavigable menuNavigable, MenuItem menuItem) {
            if (mMenuItemClickListener != null) {
                mMenuItemClickListener.onMenuItemClick(menuNavigable, menuItem);
            }
        }
    };

    public MenuItemsContainer(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.navigation_menu, this);
        mItemsContainer = (ViewGroup) findViewById(R.id.items_container);
        mItems = new ArrayList<>(NavigationMenuMainItem.values().length);
        Collections.addAll(mItems, NavigationMenuMainItem.values());
        boolean addSeparator = false;
        for (MenuNavigable menuNavigable : mItems) {
            if (addSeparator) {
                addSeparator();
            }
            addItem(menuNavigable);
            addSeparator = true;
        }
    }

    private void addItem(MenuNavigable menuNavigable) {
        MenuItem menuItem = menuNavigable.createMenuItem(getContext(), mOnMenuItemClickListener);
        mItemsContainer.addView(menuItem,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addSeparator() {
        View separator = new View(getContext());
        separator.setBackgroundColor(getResources().getColor(R.color.menu_item_separator_color));
        mItemsContainer.addView(separator, new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.navigation_menu_separator_height)));
    }

    public void setOnItemClickListener(MenuItemClickListener listener) {
        mMenuItemClickListener = listener;
    }
}
