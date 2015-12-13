package com.alex.mirash.demo.menu.item;


import com.alex.mirash.demo.menu.item.strategy.MenuNavigable;
import com.alex.mirash.demo.menu.item.view.MenuItem;

/**
 * @author Mirash
 */
public interface MenuItemClickListener {
    void onMenuItemClick(MenuNavigable menuNavigable, MenuItem menuItem);
}
