package com.alex.mirash.demo.menu.item.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.utils.EffectUtils;


/**
 * @author Mirash
 */
public class MenuItemExpand extends MenuItem {
    private ExpandButton mExpandButton;
    private ViewGroup mSubItemsContainer;

    private boolean mIsExpanded;
    private ISizeDefined mExpandableContent;

    public MenuItemExpand(Context context) {
        super(context);
    }

    @Override
    protected void initElements() {
        mExpandButton = (ExpandButton) findViewById(R.id.menu_item_expand_button);
        mSubItemsContainer = (ViewGroup) findViewById(R.id.menu_inner_items_container);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.navigation_menu_expand_item;
    }

    public void setExpandableContent(ISizeDefined expandableContent) {
        mExpandableContent = expandableContent;
        mSubItemsContainer.addView((View) expandableContent);
    }

    public void changeExpandState() {
        mIsExpanded = !mIsExpanded;
        mExpandButton.setExpandedState(mIsExpanded);
        if (mIsExpanded) {
            EffectUtils.expand(mSubItemsContainer, mExpandableContent.getDefinedHeight(getResources()));
        } else {
            EffectUtils.collapse(mSubItemsContainer);
        }
    }

    public void setExpandButtonVisibility(int visibility) {
        mExpandButton.setVisibility(visibility);
    }

    @Override
    public int getDefinedHeight(Resources res) {
        return mIsExpanded ?
                res.getDimensionPixelSize(R.dimen.navigation_menu_expand_item_height) + mExpandableContent.getDefinedHeight(res) :
                res.getDimensionPixelSize(R.dimen.navigation_menu_expand_item_height);
    }
}
