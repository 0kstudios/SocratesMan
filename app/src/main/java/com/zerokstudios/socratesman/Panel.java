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
 *
 * custom view for draw
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap background;
    private AnimationThread animationThread;

    /**
     * default constructor
     * @param context pass in activity context
     * @param attributeSet pass in attribute set
     */
    public Panel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        animationThread = new AnimationThread(getHolder(), this);
    }

    /**
     * on draw, unused
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

    }

    /**
     * custom draw
     * @param canvas canvas of this surface
     */
    public void redraw(Canvas canvas) {
        Map map = MainActivity.GAME_CONTROLLER.getMap();

        clear(canvas);
        map.getPills().draw(canvas);
        map.getSocrates().draw(canvas);
        for (Ghost ghost : map.getGhosts()) {
            ghost.draw(canvas);
        }
    }

    /**
     * clear surface with background
     * @param canvas canvas of this surface
     */
    private void clear(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        canvas.drawBitmap(background, 0, 0, null);
    }

    /**
     * pause draw thread
     */
    public void pause() {
        animationThread.setRunning(false);
    }

    /**
     * restart draw thread
     */
    public void restart() {
        animationThread.setRunning(true);
        animationThread.run();
    }

    /**
     * join draw thread
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        animationThread.join();
    }

    /**
     * on surface create
     * @param holder surfaceholder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Vector panelDimensions = new Vector(getWidth(), getHeight());
        MainActivity.GAME_CONTROLLER.setMap(new Vector(5, 5), panelDimensions, getResources());

        background = Bitmap.createBitmap(panelDimensions.X, panelDimensions.Y, Bitmap.Config.ARGB_8888);
        Canvas backgroundCanvas = new Canvas(background);
        Map map = MainActivity.GAME_CONTROLLER.getMap();
        map.getWalls().draw(backgroundCanvas);

        animationThread.setRunning(true);
        animationThread.start();
        MainActivity.GAME_CONTROLLER.start();
    }

    /**
     * on the surface change dimension
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * on surface destroyed
     * @param holder surfaceholder
     */
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
