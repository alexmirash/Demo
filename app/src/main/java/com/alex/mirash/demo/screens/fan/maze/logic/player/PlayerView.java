package com.alex.mirash.demo.screens.fan.maze.logic.player;

import android.content.Context;
import android.widget.FrameLayout;

import com.alex.mirash.demo.R;

/**
 * @author Mirash
 */
public class PlayerView extends FrameLayout implements MazePlayer {
    private int mCurrentStateResource;

    public PlayerView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.ic_launcher);
    }

    @Override
    public void idle() {
    }

    @Override
    public void celebrateVictory() {

    }

    @Override
    public void fail() {

    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }

    @Override
    public void rotateUp() {

    }

    @Override
    public void rotateDown() {

    }
}
