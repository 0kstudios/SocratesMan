package com.zerokstudios.socratesman;

import android.graphics.Bitmap;

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
}
