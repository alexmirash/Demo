package com.alex.mirash.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.alex.mirash.demo.base.BaseActivity;
import com.alex.mirash.demo.base.ParallaxView;
import com.alex.mirash.demo.menu.NavigationMenuFragment;
import com.alex.mirash.demo.menu.item.strategy.MenuNavigable;
import com.alex.mirash.demo.utils.LogUtils;
import com.alex.mirash.demo.utils.MirashUtils;

/**
 * @author Mirash
 */
public class MainActivity extends BaseActivity implements NavigationMenuFragment.NavigationMenuListener {
    private static final int MENU_HEIGHT = MirashApplication.getAppContext().getResources().getDimensionPixelSize(R.dimen.navigation_drawer_width);

    private NavigationMenuFragment mNavigationMenuFragment;
    private ParallaxView mParallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MirashUtils.hideActionBarTitle(this);
//        MirashUtils.hideStatusBar(this);
        setContentView(R.layout.content_activity);
        mParallaxView = (ParallaxView) findViewById(R.id.parallax_content);
        mNavigationMenuFragment = (NavigationMenuFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationMenuFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
//                setCustomAnimations(R.anim.slide_down_top, R.anim.slide_top_down).
                replace(R.id.settings_container, fragment).
                commit();
    }

    /*~~~~~~~~~~~~~~~~ navigation menu callbacks ~~~~~~~~~~~~~~~~*/
    @Override
    public void onNavigationDrawerItemSelected(MenuNavigable menuNavigable) {
        menuNavigable.handleItemClick(this);
/*        if (position == 2) {
            LoneUtils.showStatusBar(this);
        } else {
            LoneUtils.hideStatusBar(this);
        }*/
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mParallaxView.setOffset(MENU_HEIGHT * slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        LogUtils.log("onDrawerOpened");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        LogUtils.log("onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        LogUtils.log("onDrawerStateChanged " + newState);
    }
}
