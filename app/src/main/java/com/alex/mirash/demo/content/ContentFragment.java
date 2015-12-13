package com.alex.mirash.demo.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.base.BaseFragment;
import com.alex.mirash.demo.content.views.ReaderLayout;


/**
 * @author Mirash
 */
public class ContentFragment extends BaseFragment {
    private ReaderLayout mReaderLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.reader_content, null);
        ReaderLayout readerLayout = (ReaderLayout) contentView.findViewById(R.id.reader_layout);
        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
