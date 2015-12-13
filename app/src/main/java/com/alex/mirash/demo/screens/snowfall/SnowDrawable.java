package com.alex.mirash.demo.screens.snowfall;

import android.graphics.Canvas;

/**
 * @author Mirash
 */
public interface SnowDrawable {
    void draw(Canvas canvas);

    boolean updateState();
}
