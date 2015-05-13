package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Map {
    private int tileRadius;
    private Vector gridDimensions;
    private Vector pixelDimensions;

    public Map() {

    }

    public int getTileRadius() {
        return tileRadius;
    }

    public int getSquareTileDiameter() {
        return tileRadius * tileRadius * 4;
    }

    public Vector getGridDimensions() {
        return gridDimensions;
    }

    public Vector getPixelDimensions() {
        return pixelDimensions;
    }
}
