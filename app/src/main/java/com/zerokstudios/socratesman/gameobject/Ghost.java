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
public class Ghost extends Entity {
    public Ghost(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aOi, aImage);
    }

    @Override
    public void onCollide(CollideEvent event) {
        switch (event.TYPE) {
            case WALL:
                setVelocity(new Vector(0, 0));
                break;
            case SOCRATES:
                // set position back to original
                break;
        }
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.GHOST;
    }

    @Override
    public void update() {

    }
}
