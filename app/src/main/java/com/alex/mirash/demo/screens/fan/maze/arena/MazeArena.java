package com.alex.mirash.demo.screens.fan.maze.arena;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alex.mirash.demo.screens.fan.maze.logic.model.cells.IMazeCell;
import com.alex.mirash.demo.screens.fan.maze.logic.player.PlayerView;

/**
 * @author Mirash
 */
public class MazeArena extends FrameLayout {
    private MazeFieldView mMazeField;
    private PlayerView mPlayer;

    public MazeArena(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    private void initLayout(Context context) {
        mMazeField = new MazeFieldView(context);
        addView(mMazeField, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void initialize(IMazeCell[][] mazeCells) {
        mMazeField.setData(mazeCells);
    }
}
