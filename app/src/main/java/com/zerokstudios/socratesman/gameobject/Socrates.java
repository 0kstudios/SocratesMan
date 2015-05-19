package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * socrates class
 */
public class Socrates extends Entity {
    private static final int speed = 5;

    /**
     * default constructor
     *
     * @param aMap
     * @param aPosition
     * @param aVelocity
     * @param aOi
     * @param aImage
     */
    public Socrates(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aOi, aImage);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.SOCRATES;
    }

//    @Override
//    public void draw(Canvas canvas) {
//        Vector drawLocation = getPosition().sum(map.getTileRadiusVector().scale(-1));
//        canvas.drawBitmap(bitmap, drawLocation.X, drawLocation.Y, null);
//    }

    /**
     * on collide with wall, set velocity 0
     * on collide with ghost, kill
     *
     * @param event
     */
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

    /**
     * set velocity based on current oi
     */
    @Override
    public void update() {
        boolean up = oi.get(OI.Control.UP);
        boolean down = oi.get(OI.Control.DOWN);
        boolean left = oi.get(OI.Control.LEFT);
        boolean right = oi.get(OI.Control.RIGHT);

        int x = getVelocity().X;
        int y = getVelocity().Y;

        if (up) {
            y = -speed;
        } else if (down) {
            y = speed;
        }

        if (left) {
            x = -speed;
        } else if (right) {
            x = speed;
        }

        //System.out.println("X : " + x + " Y: " + y + " | " + up + " " + down + " " + left + " " + right);
        setVelocity(new Vector(x, y));
    }
}
