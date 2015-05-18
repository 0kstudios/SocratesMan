package com.zerokstudios.socratesman;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kevin on 5/12/2015.
 */
public class AnimationThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final Panel surfaceView;
    private boolean run;

    private static final int TICK_TIME = 1000/30;

    public AnimationThread(SurfaceHolder aSurfaceHolder, Panel aSurfaceView) {
        surfaceHolder = aSurfaceHolder;
        surfaceView = aSurfaceView;
        run = false;
    }

    public void setRunning(boolean b) {
        run = b;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        GameClock gameClock = new GameClock();
        gameClock.start();
        while (run) {
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    surfaceView.redraw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            //System.out.println("Animation: " + gameClock.peekElapsed());
            int waitTime = TICK_TIME - gameClock.getElapsed();
            //System.out.println("Animation Wait: " + waitTime);
            try {
                sleep((waitTime > 0) ? waitTime : 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
