package com.alex.mirash.demo.menu.item.strategy;

import android.content.Context;
import android.view.View;

import com.alex.mirash.demo.MainActivity;
import com.alex.mirash.demo.R;
import com.alex.mirash.demo.menu.item.MenuItemClickListener;
import com.alex.mirash.demo.menu.item.view.MenuItem;
import com.alex.mirash.demo.menu.item.view.MenuItemSub;
import com.alex.mirash.demo.utils.LogUtils;


/**
 * @author Mirash
 */
public enum NavigationSubItem implements MenuNavigable<MainActivity> {
    FONT_SETTINGS {
        @Override
        public String getTitle() {
            return "font settings";
        }

        @Override
        public int getIconResourceId() {
            return 0;
        }
    },
    MARGIN_SETTINGS {
        @Override
        public String getTitle() {
            return "margin settings";
        }

        @Override
        public int getIconResourceId() {
            return R.drawable.ic_launcher;
        }
    };

    @Override
    public void handleItemClick(MainActivity mainActivity) {
        LogUtils.log("onItemClick " + getTag());
    }

    @Override
    public String getTag() {
        return name();
    }

    @Override
    public MenuItem createMenuItem(Context context, final MenuItemClickListener menuItemClickListener) {
        MenuItemSub item = new MenuItemSub(context);
        item.setTitle(getTitle());
        item.setImageResource(getIconResourceId());
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItemClickListener.onMenuItemClick(NavigationSubItem.this, (MenuItem) v);
            }
        });
        return item;
    }
}
