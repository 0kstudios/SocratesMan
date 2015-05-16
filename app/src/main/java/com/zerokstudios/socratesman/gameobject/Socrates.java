package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;
import com.zerokstudios.socratesman.gameobject.CollideEvent;
import com.zerokstudios.socratesman.gameobject.Entity;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Socrates extends Entity {
    public Socrates(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aOi, aImage);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.SOCRATES;
    }

    @Override
    public void onCollide(CollideEvent event) {

    }

    @Override
    public void update() {
        setVelocity(new Vector((oi.get(OI.Control.LEFT) ? -1 : 0) + (oi.get(OI.Control.RIGHT) ? 1 : 0), (oi.get(OI.Control.UP) ? -1 : 0) + (oi.get(OI.Control.DOWN) ? 1 : 0)));
    }
}
