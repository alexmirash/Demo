package com.alex.mirash.demo.screens.fan.maze.logic.model.cells;

import com.alex.mirash.demo.MirashApplication;

/**
 * @author Mirash
 */
public enum MazeCell implements IMazeCell {
    EMPTY,
    WALL(android.R.color.black) {
        @Override
        public boolean isPassable() {
            return false;
        }
    },
    FINISH(android.R.color.holo_blue_dark);

    MazeCell() {
    }

    MazeCell(int colorId) {
        mBackgroundColor = MirashApplication.getInstance().getResources().getColor(colorId);
    }

    private int mBackgroundColor;

    @Override
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    public static IMazeCell getRandomCell() {
        return Math.random() > 0.66 ? WALL : EMPTY;
    }
}
