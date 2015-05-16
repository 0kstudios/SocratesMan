package com.zerokstudios.socratesman;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kevin on 5/12/2015.
 */
public class AnimationThread<SV extends SurfaceView & SurfaceHolder.Callback> extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final SV surfaceView;
    private boolean run;

    private static final long TICK_TIME = 1000/60;

    public AnimationThread(SurfaceHolder aSurfaceHolder, SV aSurfaceView) {
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
        while (run) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    surfaceView.draw(canvas); // originally this was onDraw(). hopefully this calls onDraw.
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                sleep(TICK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
