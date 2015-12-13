package com.alex.mirash.demo.base;

import android.app.Activity;
import android.app.Fragment;
import android.view.ViewGroup;

import com.alex.mirash.demo.MainActivity;

/**
 * @author Mirash
 */
public abstract class BaseFragment extends Fragment {
    protected MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    public void handleBackPressedEvent() {
    }

}
