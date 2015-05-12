package com.zerokstudios.socratesman;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kevin on 5/12/2015.
 */
public class AnimationThread<SV extends SurfaceView & SurfaceHolder.Callback> extends Thread {
    private SurfaceHolder surfaceHolder;
    private SV surfaceView;
    private boolean run;

    public static final long TICK_TIME = 1000/60;

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
        Canvas canvas;
        while (run) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    System.out.println("draw call");
                    surfaceView.draw(canvas); // originally this was onDraw(). hopefully this calls onDraw.
                    sleep(TICK_TIME);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
