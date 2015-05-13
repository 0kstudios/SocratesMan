package com.zerokstudios.socratesman;

import android.graphics.Bitmap;

/**
 * Created by Kevin on 5/12/2015.
 */
public abstract class Entity implements Collidable{
    private Vector position;
    private Vector velocity;
    private boolean alive;

    private Map map;

    private Bitmap image;

    public Entity(Map aMap, Vector aPosition, Vector aVelocity, Bitmap aImage) {
        if (aPosition == null) {
            aPosition = new Vector(0, 0);
        }
        if (aVelocity == null) {
            aVelocity = new Vector(0, 0);
        }
        map = aMap;
        position = aPosition;
        velocity = aVelocity;
        alive = true;
        image = aImage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive() {
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

    public void teleport(Vector aPosition) {
        position = aPosition;
    }

    public void goTo(Vector aPosition) {
        //pathfinding here
    }

    public void tick() {

    }

    public void draw() {

    }

    @Override
    public boolean isColliding(Entity entity) {
        return position.difference(entity.getPosition()).toSquareScalar() < map.getSquareTileDiameter()+1;
    }
}
