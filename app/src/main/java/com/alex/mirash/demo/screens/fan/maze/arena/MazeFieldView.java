package com.alex.mirash.demo.screens.fan.maze.arena;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.alex.mirash.demo.screens.fan.maze.logic.model.cells.IMazeCell;

/**
 * @author Mirash
 */
public class MazeFieldView extends View {
    private IMazeCell[][] mMazeCells;
    private int mRows;
    private int mColumns;
    private float mCellWidth;
    private float mCellHeight;
    private Paint mCellPaint;

    public MazeFieldView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMazeCells != null) {
            Log.d("MAZE", getMeasuredWidth() + "; " + getMeasuredHeight());
            mCellWidth = (float) getMeasuredWidth() / mColumns;
            mCellHeight = (float) getMeasuredHeight() / mRows;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("MAZE", "onDraw");
        if (mMazeCells != null) {
            for (int i = 0; i < mRows; i++) {
                float top = i * mCellHeight;
                float bottom = top + mCellHeight;
                for (int j = 0; j < mColumns; j++) {
                    float left = j * mCellWidth;
                    float right = left + mCellWidth;
                    mCellPaint.setColor(mMazeCells[i][j].getBackgroundColor());
                    canvas.drawRect(left, top, right, bottom, mCellPaint);
                }
            }
        }
    }

    public void setData(IMazeCell[][] mazeCells) {
        mMazeCells = mazeCells;
        mCellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRows = mazeCells.length;
        mColumns = mazeCells[0].length;
    }
}
