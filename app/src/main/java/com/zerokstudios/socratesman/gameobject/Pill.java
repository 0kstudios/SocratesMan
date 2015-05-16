package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.OI;
import com.zerokstudios.socratesman.Vector;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Pill extends StaticEntity {
    public Pill(Map aMap, Vector aPosition, OI aOi, Bitmap aImage) {
        super(aMap, aPosition, aOi, aImage);
    }

    @Override
    public void onCollide(CollideEvent event) {

    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.PILL;
    }

    @Override
    public void update() {

    }
}