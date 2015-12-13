package com.alex.mirash.demo.menu.item.strategy;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alex.mirash.demo.MainActivity;
import com.alex.mirash.demo.R;
import com.alex.mirash.demo.menu.item.MenuItemClickListener;
import com.alex.mirash.demo.menu.item.view.MenuItem;
import com.alex.mirash.demo.menu.item.view.MenuItemSub;
import com.alex.mirash.demo.screens.fan.maze.MazeActivity;


/**
 * @author Mirash
 */
public enum NavigationFanSubItem implements MenuNavigable<MainActivity> {
    LABIRINTH {
        @Override
        public String getTitle() {
            return "Labirinth";
        }

        @Override
        public int getIconResourceId() {
            return 0;
        }
    };

    @Override
    public void handleItemClick(MainActivity mainActivity) {
        Intent intent = new Intent(mainActivity, MazeActivity.class);
        mainActivity.startActivity(intent);
        mainActivity.overridePendingTransition(R.anim.slide_down_top, R.anim.slide_top_down);
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
                menuItemClickListener.onMenuItemClick(NavigationFanSubItem.this, (MenuItem) v);
            }
        });
        return item;
    }
}
