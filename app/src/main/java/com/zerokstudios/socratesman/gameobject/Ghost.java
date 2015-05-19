package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

import java.util.ArrayList;

/**
 * Created by Deven on 5/12/2015.
 */
public class Ghost extends Entity {
    private Vector target;

    public Ghost(Map aMap, Vector aPosition, Vector aVelocity, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aVelocity, aOi, aImage);

        target = null;
    }

    public static boolean allSurroundingHeatsTheSame(ArrayList<Cell> cellList) {
        if (cellList.size() == 1) {
            return true;
        } else {
            int firstHeat = cellList.get(0).getHeat();
            int secondHeat = cellList.get(1).getHeat();

            if (firstHeat == secondHeat) {
                cellList.remove(0);
                allSurroundingHeatsTheSame(cellList);
            }
            return true;
        }
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
        if (target == null) {
            int maxHeat = Integer.MIN_VALUE;
            ArrayList<Cell> surroundings = map.getCells().getSurroundings(getPosition());
            if (!allSurroundingHeatsTheSame(surroundings)) {
                for (Cell c : surroundings) {
                    if (c.getHeat() > maxHeat) {
                        maxHeat = c.getHeat();
                        target = c.getPosition();
                    }
                }
            } else {
                double minDistance = Integer.MAX_VALUE;
                for (Cell c : surroundings) {
                    double dist = Math.sqrt(Math.pow(map.getSocrates().getPosition().Y - c.getPosition().Y, 2) + Math.pow(map.getSocrates().getPosition().X - c.getPosition().X, 2));
                    if (dist < minDistance) {
                        minDistance = dist;
                        target = c.getPosition();
                    }
                }
            }
        }

        goTo(target);
    }

    public void goTo(Vector targetPosition) {
        Vector delta = targetPosition.difference(getPosition());

        setVelocity(new Vector(((delta.X > 0) ? 1 : -1), (delta.Y > 0) ? 1 : -1));
    }
}
