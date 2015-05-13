package com.zerokstudios.socratesman;

import android.graphics.Bitmap;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Socrates extends Entity {
    public Socrates(Map aMap, Vector aPosition, Vector aVelocity, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aImage);
    }

    @Override
    public void onCollide(CollideEvent event) {

    }
}
