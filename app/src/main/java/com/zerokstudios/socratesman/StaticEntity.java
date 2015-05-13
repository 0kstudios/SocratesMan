package com.zerokstudios.socratesman;

import android.graphics.Bitmap;

/**
 * Created by Kevin on 5/12/2015.
 */
public abstract class StaticEntity extends Entity {

    public StaticEntity(Map aMap, Vector aPosition, Bitmap aImage) {
        super(aMap, aPosition, null, aImage);
    }

    @Override
    public void setVelocity(Vector aVelocity) {
        //statics do not move
    }

    @Override
    public void teleport(Vector aPosition) {
        //statics do not move
    }

    @Override
    public void goTo(Vector position) {
        //statics do not move
    }
}
