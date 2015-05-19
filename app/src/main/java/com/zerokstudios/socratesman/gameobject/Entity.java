package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * base game object class
 */
public abstract class Entity implements Collidable {
    protected Map map;
    protected OI oi;
    protected Bitmap bitmap;
    private Vector position; // measured in pixels
    private Vector velocity; // measured in tileRadius / ms
    private boolean alive;

    /**
     * constructor
     *
     * @param aMap      pass in map
     * @param aPosition position
     * @param aVelocity velocity
     * @param aOi       pass in oi
     * @param aImage    pass in image
     */
    public Entity(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        if (aPosition == null) {
            aPosition = new Vector(0, 0);
        }
        if (aVelocity == null) {
            aVelocity = new Vector(0, 0);
        }
        oi = aOi;
        map = aMap;
        position = aPosition;
        velocity = aVelocity;
        alive = true;
        bitmap = aImage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void revive() {
        alive = true;
    }

    public void kill() {
        alive = false;
    }

    public boolean isDead() {
        return !alive;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector aVelocity) {
        velocity = aVelocity;
    }

    public Vector getPosition() {
        return position;
    }

    /**
     * determines next position based on current velocity
     *
     * @param time
     * @return next position
     */
    public Vector nextPosition(int time) {
        //System.out.println("velocity vector: " + getVelocity());
        //System.out.println("delta position vector: " + getVelocity().scale(map.getTileRadius() * time));
        return position.sum(getVelocity().scale(map.getTileRadius() * time / 1000.0));
    }

    /**
     * move to position
     *
     * @param aPosition
     */
    public void teleport(Vector aPosition) {
        position = aPosition;
    }

    /**
     * determines what happens during an update
     */
    public abstract void update();

    /**
     * every tick move object to its next position
     *
     * @param time
     */
    public void tick(int time) {
        position = nextPosition(time);
    }

    /**
     * draw object at its position on the canvas
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.X, position.Y, null);
    }

    /**
     * is colliding if the difference in positions is below the collision radius
     *
     * @param entity other collidable entity
     * @param time
     * @return is colliding
     */
    @Override
    public boolean isColliding(Entity entity, int time) {
        if (entity != null) {
            Vector difference = nextPosition(time).difference(entity.nextPosition(time)).abs();
            int collisionRadius = map.getTileDiameter();
            return difference.X < collisionRadius && difference.Y < collisionRadius;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(getType());
    }
}
