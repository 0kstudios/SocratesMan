package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.Vector;
import com.zerokstudios.socratesman.gameobject.CollideEvent;
import com.zerokstudios.socratesman.gameobject.Entity;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Socrates extends Entity {
    public Socrates(Map aMap, Vector aPosition, Vector aVelocity, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aImage);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.SOCRATES;
    }

    @Override
    public void onCollide(CollideEvent event) {

    }
}
