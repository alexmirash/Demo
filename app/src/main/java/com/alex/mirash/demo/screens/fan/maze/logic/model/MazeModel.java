package com.alex.mirash.demo.screens.fan.maze.logic.model;

import android.graphics.Point;

import com.alex.mirash.demo.screens.fan.maze.logic.model.cells.IMazeCell;
import com.alex.mirash.demo.screens.fan.maze.logic.model.cells.MazeCell;

/**
 * @author Mirash
 */
public class MazeModel {
    private IMazeCell[][] mMazeCells;
    private Point mFinishPoint;
    private Point mPlayerPoint;
    private int mRows;
    private int mColumns;

    public MazeModel(int rowsCount, int columnsCount) {
        mRows = rowsCount;
        mColumns = columnsCount;
        mMazeCells = new IMazeCell[mRows][mColumns];
    }

    public void generateRandomeELements() {
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                mMazeCells[i][j] = MazeCell.getRandomCell();
            }
        }
        mFinishPoint = new Point(mRows - 1, mColumns - 1);
        mPlayerPoint = new Point(0, 0);
        mMazeCells[mFinishPoint.x][mFinishPoint.y] = MazeCell.FINISH;
    }

    public IMazeCell[][] getCells() {
        return mMazeCells;
    }
}
