package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;
import com.zerokstudios.socratesman.gameobject.Entity;

/**
 * Created by Kevin on 5/12/2015.
 */
public abstract class StaticEntity extends Entity {

    public StaticEntity(Map aMap, Vector aPosition, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, null, aOi, aImage);
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