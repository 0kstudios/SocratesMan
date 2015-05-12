package com.zerokstudios.socratesman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback {

    private AnimationThread<Panel> animationThread;

    public Panel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        animationThread = new AnimationThread<Panel>(getHolder(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("draw called");
        Paint paint = new Paint();
        Bitmap droid = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(droid, 10, 10, null);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        animationThread.setRunning(true);
        animationThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        animationThread.setRunning(false);
        while (retry) {
            try {
                animationThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}
