package com.zerokstudios.socratesman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zerokstudios.socratesman.gameobject.Ghost;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap background;
    private Bitmap foreground;
    private Canvas foregroundCanvas;
    private Vector panelDimensions;
    private AnimationThread<Panel> animationThread;

    public Panel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        animationThread = new AnimationThread<Panel>(getHolder(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Map map = MainActivity.GAME_CONTROLLER.getMap();

        clear(canvas);
        map.getPills().draw(canvas);
        map.getSocrates().draw(canvas);
        for (Ghost ghost : map.getGhosts()) {
            ghost.draw(canvas);
        }

        invalidate();
    }

    private void clear(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        canvas.drawBitmap(background, 0, 0, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        panelDimensions = new Vector(getWidth(), getHeight());
        MainActivity.GAME_CONTROLLER.setMap(new Vector(5, 5), panelDimensions, getResources());

        foreground = Bitmap.createBitmap(panelDimensions.X, panelDimensions.Y, Bitmap.Config.ARGB_8888);
        foregroundCanvas = new Canvas(foreground);

        background = Bitmap.createBitmap(panelDimensions.X, panelDimensions.Y, Bitmap.Config.ARGB_8888);
        Canvas backgroundCanvas = new Canvas(background);
        Map map = MainActivity.GAME_CONTROLLER.getMap();
        map.getWalls().draw(backgroundCanvas);

        animationThread.setRunning(true);
        animationThread.start();
        MainActivity.GAME_CONTROLLER.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        animationThread.setRunning(false);
        MainActivity.GAME_CONTROLLER.pause();
        while (retry) {
            try {
                animationThread.join();
                MainActivity.GAME_CONTROLLER.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}
