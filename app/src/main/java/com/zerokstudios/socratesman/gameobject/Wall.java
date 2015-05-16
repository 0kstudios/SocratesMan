package com.zerokstudios.socratesman.gameobject;

import android.graphics.Bitmap;

import com.zerokstudios.socratesman.Map;
import com.zerokstudios.socratesman.Vector;
import com.zerokstudios.socratesman.gameobject.CollideEvent;
import com.zerokstudios.socratesman.gameobject.StaticEntity;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Wall extends StaticEntity {
    public Wall(Map aMap, Vector aPosition, Bitmap aImage) {
        super(aMap, aPosition, aImage);
    }

    @Override
    public void onCollide(CollideEvent event) {

    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.WALL;
    }
}
