package com.alex.mirash.demo.screens.rain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.base.BaseFragment;

/**
 * @author Mirash
 */
public class RainFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scrollView = new ScrollView(container.getContext());
        LinearLayout rootLayout = new LinearLayout(container.getContext());
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        addRainView(rootLayout, R.drawable.ripple1);
        scrollView.addView(rootLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return scrollView;
    }

    private void addRainView(ViewGroup rootView, int id) {
        RainView rainView = new RainView(rootView.getContext());
        rootView.addView(rainView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
        final RippleEffectController rippleEffectController = new RippleEffectController();
        rippleEffectController.init(rainView, id);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                rippleEffectController.run();
            }
        });
    }
}
