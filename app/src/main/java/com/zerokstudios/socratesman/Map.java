package com.zerokstudios.socratesman;

import com.zerokstudios.socratesman.gameobject.EntityGrid;
import com.zerokstudios.socratesman.gameobject.Ghost;
import com.zerokstudios.socratesman.gameobject.Pill;
import com.zerokstudios.socratesman.gameobject.Socrates;
import com.zerokstudios.socratesman.gameobject.SocratesNotFoundException;
import com.zerokstudios.socratesman.gameobject.Wall;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Map {
    public static char[][] k = { // this is only temporary until we get a working map generator
            {'#', '#', '#', '#', '#'},
            {'#', '@', '.', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#'}
    };
    private int tileRadius;
    private Vector gridDimensions;
    private Vector pixelDimensions;
    private EntityGrid<Wall> walls;
    private EntityGrid<Pill> pills;
    private ArrayList<Ghost> ghosts;
    private Socrates socrates;

    public Map(Vector aGridDimensions, Vector aPixelDimensions, OI oi) throws SocratesNotFoundException {
        gridDimensions = aGridDimensions;
        setPixelDimensions(aPixelDimensions);

        ArrayList<Wall> wallList = new ArrayList<Wall>();
        ArrayList<Pill> pillList = new ArrayList<Pill>();
        ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
        Socrates player = null;
        int i = 0, j = 0;
        for (char[] chA : generateMap(aGridDimensions)) {
            for (char ch : chA) {
                switch (ch) {
                    case Dictionary.WALL:
                        wallList.add(new Wall(this, new Vector(i, j), oi, null));
                        break;
                    case Dictionary.GHOST:
                        ghostList.add(new Ghost(this, new Vector(i, j), new Vector(0, 0), oi, null));
                        break;
                    case Dictionary.SOCRATES:
                        if (player != null) {
                            throw new SocratesNotFoundException("Double Socrates, cannot identify correct Socrates");
                        }
                        player = new Socrates(this, new Vector(i, j), new Vector(0, 0), oi, null);
                        break;
                    case Dictionary.PILL:
                    default:
                        pillList.add(new Pill(this, new Vector(i, j), oi, null));
                        break;
                }
                j++;
            }
            i++;
        }


        if (player == null) {
            throw new SocratesNotFoundException("Socrates does not exist");
        }
        walls = new EntityGrid<Wall>(this, wallList);
        pills = new EntityGrid<>(this, pillList);
        ghosts = ghostList;
        socrates = player;
    }

    public static char[][] generateMap(Vector gridDimensions) {
        return k;
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

    private void setPixelDimensions(Vector aPixelDimensions) {
        int width = aPixelDimensions.X / gridDimensions.X;
        int height = aPixelDimensions.Y / gridDimensions.Y;

        if (width > height) {
            tileRadius = height / 2;
        } else {
            tileRadius = width / 2;
        }

        pixelDimensions = new Vector(gridDimensions.X * tileRadius * 2, gridDimensions.Y * tileRadius * 2);
    }

    public EntityGrid<Wall> getWalls() {
        return walls;
    }

    public EntityGrid<Pill> getPills() {
        return pills;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public Socrates getSocrates() {
        return socrates;
    }

    private static class Dictionary {
        public static final char WALL = '#';
        public static final char PILL = '.';
        public static final char GHOST = '&';
        public static final char SOCRATES = '@';
    }
}
