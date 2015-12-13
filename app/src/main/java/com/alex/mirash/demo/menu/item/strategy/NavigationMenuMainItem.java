package com.alex.mirash.demo.menu.item.strategy;

import android.content.Context;
import android.view.View;

import com.alex.mirash.demo.MainActivity;
import com.alex.mirash.demo.R;
import com.alex.mirash.demo.menu.item.MenuItemClickListener;
import com.alex.mirash.demo.menu.item.view.ISizeDefined;
import com.alex.mirash.demo.menu.item.view.MenuItem;
import com.alex.mirash.demo.menu.item.view.MenuItemExpand;
import com.alex.mirash.demo.menu.item.view.MenuLinearExpandableContent;
import com.alex.mirash.demo.screens.rain.RainFragment;
import com.alex.mirash.demo.screens.snowfall.SnowFallFragment;
import com.alex.mirash.demo.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

import static com.alex.mirash.demo.menu.item.strategy.NavigationFanSubItem.LABIRINTH;
import static com.alex.mirash.demo.menu.item.strategy.NavigationSubItem.FONT_SETTINGS;
import static com.alex.mirash.demo.menu.item.strategy.NavigationSubItem.MARGIN_SETTINGS;


/**
 * @author Mirash
 */
public enum NavigationMenuMainItem implements MenuNavigable<MainActivity> {
    RAIN {
        @Override
        public String getTitle() {
            return "RAIN";
        }

        @Override
        public int getIconResourceId() {
            return R.drawable.ic_launcher;
        }

        @Override
        public void handleItemClick(MainActivity mainActivity) {
            mainActivity.replaceFragment(new RainFragment());
        }
    },
    SNOW {
        @Override
        public String getTitle() {
            return "SNOW";
        }

        @Override
        public int getIconResourceId() {
            return R.drawable.snowflake;
        }

        @Override
        public void handleItemClick(MainActivity mainActivity) {
            mainActivity.replaceFragment(new SnowFallFragment());
        }
    },
    FAN(LABIRINTH) {
        @Override
        public String getTitle() {
            return "FAN";
        }

        @Override
        public int getIconResourceId() {
            return R.drawable.ic_launcher;
        }
    },
    ABOUT(FONT_SETTINGS) {
        @Override
        public String getTitle() {
            return "about";
        }

        @Override
        public int getIconResourceId() {
            return R.drawable.ic_launcher;
        }
    },
    SETTINGS(FONT_SETTINGS, MARGIN_SETTINGS, ABOUT) {
        @Override
        public String getTitle() {
            return "settings";
        }

        @Override
        public int getIconResourceId() {
            return 0;
        }
    },
    LIBRARY {
        @Override
        public String getTitle() {
            return "library";
        }

        @Override
        public int getIconResourceId() {
            return 0;
        }
    };

    @Override
    public String getTag() {
        return name();
    }

    //attributes
    private List<MenuNavigable> mSubItems;
    private boolean mIsExpandable;

    //constructors
    NavigationMenuMainItem() {
        this(false);
    }

    NavigationMenuMainItem(boolean expandable) {
        mIsExpandable = expandable;
    }

    NavigationMenuMainItem(MenuNavigable... menuSubItems) {
        this(true, menuSubItems);
    }

    NavigationMenuMainItem(boolean expandable, MenuNavigable... menuSubItems) {
        mSubItems = Arrays.asList(menuSubItems);
        mIsExpandable = expandable;
    }

    //stratrgies
    @Override
    public void handleItemClick(MainActivity mainActivity) {
        LogUtils.log("onItemClick " + getTag());
    }

    @Override
    public MenuItem createMenuItem(Context context, final MenuItemClickListener menuItemClickListener) {
        MenuItemExpand item = new MenuItemExpand(context);
        item.setTitle(getTitle());
        if (mIsExpandable) {
            item.setImageResource(getIconResourceId());
            item.setExpandableContent(createExpandableContent(context, menuItemClickListener));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MenuItemExpand) v).changeExpandState();
                }
            });
        } else {
            item.setExpandButtonVisibility(View.GONE);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuItemClickListener.onMenuItemClick(NavigationMenuMainItem.this, (MenuItem) v);
                }
            });
        }
        return item;
    }

    protected ISizeDefined createExpandableContent(Context context, MenuItemClickListener menuItemClickListener) {
        MenuLinearExpandableContent container = new MenuLinearExpandableContent(context);
        for (MenuNavigable item : mSubItems) {
            container.addItem(item.createMenuItem(context, menuItemClickListener));
        }
        return container;
    }

    public boolean isExpandable() {
        return mIsExpandable;
    }

    public void setExpandable(boolean isExpandable) {
        mIsExpandable = isExpandable;
    }
}
