package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Deven on 5/12/2015.
 * <p/>
 * ghost class
 */
public class Ghost extends Entity {
    private static int speed = 4;
    private Vector target;
    private Random random;

    /**
     * default constructor
     *
     * @param aMap
     * @param aPosition
     * @param aVelocity
     * @param aOi
     * @param aImage
     */
    public Ghost(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aOi, aImage);

        target = null;
        random = new Random();
    }

    /**
     * checks to see if surrounding heat values are equal
     *
     * @param cellList
     * @return is equal
     */
    public static boolean allSurroundingHeatsTheSame(ArrayList<Cell> cellList) {
        if (cellList.size() < 2) {
            return true;
        } else {
            int firstHeat = cellList.get(0).getHeat();
            int secondHeat = cellList.get(1).getHeat();

            if (firstHeat == secondHeat) {
                cellList.remove(0);
                allSurroundingHeatsTheSame(cellList);
            }
            return false;
        }
    }

    /**
     * if wall, set velocity 0
     *
     * @param event
     */
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

    /**
     * determine which square to move to and set velocity accordingly
     */
    @Override
    public void update() {
        //System.out.println("Ghost position: " + getPosition());
        if (target == null) {
            int maxHeat = Integer.MIN_VALUE;
            ArrayList<Cell> surroundings = map.getCells().getSurroundings(getPosition());
            for (Cell c : surroundings) {
                if (c.getHeat() > maxHeat) {
                    maxHeat = c.getHeat();
                    target = c.getPosition();
                }
            }
        } else if (getPosition().difference(target).toSquareScalar() < map.getSquareTileDiameter()) {
            target = null;
        }
        goTo(target);
    }

    /**
     * set velocity based on current target position
     *
     * @param targetPosition
     */
    public void goTo(Vector targetPosition) {
        if (targetPosition != null) {
            Vector delta = targetPosition.difference(getPosition());
            int x = getVelocity().X;
            int y = getVelocity().Y;
            if (delta.X > map.getTileRadius()) {
                x = speed;
            } else if (delta.X < -map.getTileRadius()) {
                x = -speed;
            }
            if (delta.Y > map.getTileRadius()) {
                y = speed;
            } else if (delta.Y < -map.getTileRadius()) {
                y = -speed;
            }
            setVelocity(new Vector(x, y));
            //System.out.println(getPosition() + " " + getVelocity() + " " + delta);
        }
    }
}
