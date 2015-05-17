package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
        switch (event.TYPE) {
            case WALL:
                setVelocity(new Vector(0, 0));
                break;
            case GHOST:
                setVelocity(new Vector(0, 0));
                kill();
                //set position back to original
                break;
        }
    }

    private final Vector speed = new Vector(1,1);

    @Override
    public void update() {
        boolean up = oi.get(OI.Control.UP);
        boolean down = oi.get(OI.Control.DOWN);
        boolean left = oi.get(OI.Control.LEFT);
        boolean right = oi.get(OI.Control.RIGHT);

        int x = getVelocity().X;
        int y = getVelocity().Y;

        if (up) {
            y = -speed.Y;
        } else if (down) {
            y = speed.Y;
        }

        if (left) {
            x = -speed.X;
        } else if (right) {
            x = speed.X;
        }

        //System.out.println("X : " + x + " Y: " + y + " | " + up + " " + down + " " + left + " " + right);
        setVelocity(new Vector(x, y));
    }
}
