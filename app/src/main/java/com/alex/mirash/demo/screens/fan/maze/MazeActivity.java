package com.alex.mirash.demo.screens.fan.maze;

import android.os.Bundle;
import android.view.MotionEvent;

import com.alex.mirash.demo.R;
import com.alex.mirash.demo.base.BaseActivity;
import com.alex.mirash.demo.screens.fan.maze.arena.MazeArena;
import com.alex.mirash.demo.screens.fan.maze.logic.model.MazeModel;
import com.alex.mirash.demo.utils.MirashUtils;

/**
 * @author Mirash
 */
public class MazeActivity extends BaseActivity {
    private MazeArena mMazeArena;
    private MazeModel mMazeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        mMazeArena = (MazeArena) findViewById(R.id.maze_arena);
        MirashUtils.hideStatusBar(this);
        mMazeModel = new MazeModel(48, 27);
        mMazeModel.generateRandomeELements();
        mMazeArena.initialize(mMazeModel.getCells());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
