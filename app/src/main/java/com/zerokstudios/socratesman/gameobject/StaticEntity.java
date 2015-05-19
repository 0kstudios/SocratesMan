package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * static entity class, subset of entity which does not move
 */
public abstract class StaticEntity extends Entity {

    /**
     * default constructor
     * @param aMap
     * @param aPosition
     * @param aOi
     * @param aImage
     */
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

}
