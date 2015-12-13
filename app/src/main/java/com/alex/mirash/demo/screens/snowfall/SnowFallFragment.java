package com.alex.mirash.demo.screens.snowfall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.base.BaseFragment;

/**
 * @author Mirash
 */
public class SnowFallFragment extends BaseFragment {
    private SnowFallController mSnowFallController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.snow_fall_fragment, null);
        mSnowFallController = new SnowFallController();
        mSnowFallController.init((SnowFallSurfaceView) contentView.findViewById(R.id.snow_fall_surface_view));
        return contentView;
    }

    @Override
    public void onDestroyView() {
        mSnowFallController.stop();
        super.onDestroyView();
    }
}
