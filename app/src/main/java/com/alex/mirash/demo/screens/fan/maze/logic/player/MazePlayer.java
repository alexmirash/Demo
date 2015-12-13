package com.alex.mirash.demo.screens.fan.maze.logic.player;

/**
 * @author Mirash
 */
public interface MazePlayer {
    void idle();

    void celebrateVictory();

    void fail();

    void rotateLeft();

    void rotateRight();

    void rotateUp();

    void rotateDown();
}
