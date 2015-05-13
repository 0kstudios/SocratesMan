package com.zerokstudios.socratesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Map {
    private int tileRadius;
    private Vector gridDimensions;
    private Vector pixelDimensions;

    private EntityGrid<Wall> walls;
    private EntityGrid<Pill> pills;

    private ArrayList<Ghost> ghosts;

    private Socrates socrates;

    public Map(String file, Vector aPixelDimensions) throws SocratesNotFoundException {
        ArrayList<Wall> wallList = new ArrayList<Wall>();
        ArrayList<Pill> pillList = new ArrayList<Pill>();
        ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
        Socrates player = null;
        int i = 0;
        int j = 0;
        try {
            Scanner in = new Scanner(new File(file));
            i = 0;
            while (in.hasNext()) {
                String line = in.nextLine();
                j = 0;
                for (char ch : line.toCharArray()) {
                    switch (ch) {
                        case Dictionary.WALL:
                            wallList.add(new Wall(this, new Vector(i, j), null));
                            break;
                        case Dictionary.GHOST:
                            ghostList.add(new Ghost(this, new Vector(i, j), new Vector(0, 0), null));
                            break;
                        case Dictionary.SOCRATES:
                            if (player != null) {
                                throw new SocratesNotFoundException("Double Socrates, cannot identify correct Socrates");
                            }
                            player = new Socrates(this, new Vector(i, j), new Vector(0, 0), null);
                            break;
                        case Dictionary.PILL:
                        default:
                            pillList.add(new Pill(this, new Vector(i, j), null));
                            break;
                    }
                    j++;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gridDimensions = new Vector(i, j);

        int width = aPixelDimensions.X / i;
        int height = aPixelDimensions.Y / j;
        if (width > height) {
            tileRadius = height/2;
        } else {
            tileRadius = width/2;
        }

        pixelDimensions = new Vector(gridDimensions.X * tileRadius * 2, gridDimensions.Y * tileRadius * 2);

        if (player == null) {
            throw new SocratesNotFoundException("Socrates does not exist");
        }
        walls = new EntityGrid<Wall>(this, wallList);
        pills = new EntityGrid<>(this, pillList);
        ghosts = ghostList;
        socrates = player;
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

    private static class Dictionary {
        public static final char WALL = '#';
        public static final char PILL = '.';
        public static final char GHOST = '&';
        public static final char SOCRATES = '@';
    }
}
