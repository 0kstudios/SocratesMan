package com.zerokstudios.socratesman;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kevin on 5/12/2015.
 * <p>
 * UI thread for the draw calls
 */
public class AnimationThread extends Thread {
    private static final int TICK_TIME = 1000 / 30; //framerate
    private final SurfaceHolder surfaceHolder;
    private final Panel surfaceView;
    private boolean run;

    /**
     * constructor
     *
     * @param aSurfaceHolder manipulates the view
     * @param aSurfaceView   drawing surface itself
     */
    public AnimationThread(SurfaceHolder aSurfaceHolder, Panel aSurfaceView) {
        surfaceHolder = aSurfaceHolder;
        surfaceView = aSurfaceView;
        run = false;
    }

    /**
     * set thread running
     * @param b run
     */
    public void setRunning(boolean b) {
        run = b;
    }

    /**
     * main loop
     */
    @Override
    public void run() {
        Canvas canvas = null;
        GameClock gameClock = new GameClock(); //gameclock to keep track of fps
        gameClock.start();
        while (run) {
            try {
                canvas = surfaceHolder.lockCanvas(null); //lock canvas pixels to prevent flickering
                synchronized (surfaceHolder) { //prevent accessing the canvas at the same time
                    surfaceView.redraw(canvas); //draw call
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas); //update the pixels on surface
                }
            }
            //System.out.println("Animation: " + gameClock.peekElapsed());
            int waitTime = TICK_TIME - gameClock.getElapsed(); // check for ms between frames
            //System.out.println("Animation Wait: " + waitTime);
            try {
                sleep((waitTime > 0) ? waitTime : 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
