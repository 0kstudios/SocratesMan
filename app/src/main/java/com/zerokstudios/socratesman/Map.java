package com.zerokstudios.socratesman;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    public static int[][] k = { // this is only temporary until we can optimize draw for larger maps
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 3, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int tileRadius;
    private Vector gridDimensions;
    private Vector pixelDimensions;
    private Images images;
    private EntityGrid<Wall> walls;
    private EntityGrid<Pill> pills;
    private ArrayList<Ghost> ghosts;
    private Socrates socrates;
    private int score;
    private int maxScore;

    public Map(Vector aGridDimensions, Vector aPixelDimensions, OI oi, Resources resources) throws SocratesNotFoundException {
        score = 0;
        maxScore = 0;

        gridDimensions = new Vector(11, 11);
        setPixelDimensions(aPixelDimensions);

        ArrayList<Wall> wallList = new ArrayList<Wall>();
        ArrayList<Pill> pillList = new ArrayList<Pill>();
        ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
        Socrates player = null;

        images = new Images();
        images.setSocrates(BitmapFactory.decodeResource(resources, R.drawable.socrates));
        images.setGhost(BitmapFactory.decodeResource(resources, R.drawable.ghost));
        images.setWall(BitmapFactory.decodeResource(resources, R.drawable.wall));
        images.setPill(BitmapFactory.decodeResource(resources, R.drawable.pill));
        images.resizeToTile(getTileRadius() * 2);

        int i = 0, j;
        for (int[] chA : k){//MapGenerator.generate()) {
            j = 0;
            for (int ch : chA) {
                switch (ch) {
                    case Dictionary.WALL:
                        wallList.add(new Wall(this, new Vector(j, i).scale(getTileDiameter()), oi, images.getWall()));
                        break;
                    case Dictionary.GHOST:
                        ghostList.add(new Ghost(this, new Vector(j, i).scale(getTileDiameter()), new Vector(0, 0), oi, images.getGhost()));
                        break;
                    case Dictionary.SOCRATES:
                        if (player != null) {
                            throw new SocratesNotFoundException("Double Socrates, cannot identify correct Socrates");
                        }
                        player = new Socrates(this, new Vector(j, i).scale(getTileDiameter()), new Vector(0, 0), oi, images.getSocrates());
                        break;
                    case Dictionary.PILL:
                    case Dictionary.GATE:
                    default:
                        pillList.add(new Pill(this, new Vector(j, i).scale(getTileDiameter()), oi, images.getPill()));
                        maxScore++;
                        break;
                }
                j++;
            }
            i++;
        }

        if (player == null) {
            throw new SocratesNotFoundException("Socrates does not exist");
        }
        walls = new EntityGrid<Wall>(this, wallList, new Wall(null, null, null, null));
        pills = new EntityGrid<Pill>(this, pillList, new Pill(null, null, null, null));
        ghosts = ghostList;
        socrates = player;
    }

    public int getTileRadius() {
        return tileRadius;
    }

    public Vector getTileRadiusVector() {
        return new Vector(getTileRadius(), getTileRadius());
    }

    public int getTileDiameter() {
        return tileRadius * 2;
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

    public int getScore() {
        return score;
    }

    public void incrementScore(int s) {
        score += s;
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

    private static class Images {
        private Bitmap socrates, ghost, wall, pill;

        public Bitmap getSocrates() {
            return socrates;
        }

        public void setSocrates(Bitmap aSocrates) {
            socrates = aSocrates;
        }

        public Bitmap getGhost() {
            return ghost;
        }

        public void setGhost(Bitmap aGhost) {
            ghost = aGhost;
        }

        public Bitmap getWall() {
            return wall;
        }

        public void setWall(Bitmap aWall) {
            wall = aWall;
        }

        public Bitmap getPill() {
            return pill;
        }

        public void setPill(Bitmap aPill) {
            pill = aPill;
        }

        public void resizeToTile(int size) {
            socrates = resizeBitmapToTile(socrates, size);
            ghost = resizeBitmapToTile(ghost, size);
            wall = resizeBitmapToTile(wall, size);
            pill = resizeBitmapToTile(pill, size);
        }

        private Bitmap resizeBitmapToTile(Bitmap bitmap, int size) {
            return Bitmap.createScaledBitmap(bitmap, size, size, true);
        }
    }

    private static class Dictionary {
        public static final int WALL = 1;
        public static final int PILL = 0;
        public static final int GATE = 4;
        public static final int GHOST = 2;
        public static final int SOCRATES = 3;
    }
}
