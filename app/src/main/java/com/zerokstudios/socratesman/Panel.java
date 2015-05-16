package com.zerokstudios.socratesman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Bitmap droid;
    private Vector panelDimensions;
    private AnimationThread<Panel> animationThread;

    public Panel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        animationThread = new AnimationThread<Panel>(getHolder(), this);

        initDrawables();
    }

    private void initDrawables() {
        droid = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Map map = MainActivity.GAME_CONTROLLER.getMap();
        clear(canvas);
        canvas.drawBitmap(droid, 10, 10, null);
        map.getWalls().draw();
        map.getSocrates().draw();
        for (Ghost ghost : map.getGhosts()) {
            ghost.draw();
        }
    }

    private void clear(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        panelDimensions = new Vector(getWidth(), getHeight());
        MainActivity.GAME_CONTROLLER.setMap(new Vector(5, 5), panelDimensions);
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
