package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;


/**
 * Created by Deven on 5/17/15.
 */
public class Cell extends StaticEntity {

    private final int MAXHEATVALUE = 32768;

    private int heat;

    /**
     * default constructor
     *
     * @param aMap
     * @param aPosition
     * @param aOi
     * @param aImage
     */
    public Cell(Map aMap, Vector aPosition, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aOi, aImage);
        heat = MAXHEATVALUE;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int newHeat) {
        heat = newHeat;
    }

    /**
     * decrease the heat towards 0
     */
    public void decrementHeat() {
        if (heat > 0) {
            heat--;
        }
    }

    /**
     * set heat value to max if hit by socrates
     *
     * @param event
     */
    @Override
    public void onCollide(CollideEvent event) {
        switch (event.TYPE) {
            case SOCRATES:
                setHeat(MAXHEATVALUE);
                break;
        }
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.CELL;
    }

    @Override
    public void update() {

    }

    /**
     * decrement heat
     *
     * @param time
     */
    @Override
    public void tick(int time) {
        super.tick(time);
        decrementHeat();
    }
}
