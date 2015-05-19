package com.zerokstudios.socratesman;

import android.content.res.Resources;

import com.zerokstudios.socratesman.gameobject.Cell;
import com.zerokstudios.socratesman.gameobject.CollideEvent;
import com.zerokstudios.socratesman.gameobject.EntityGrid;
import com.zerokstudios.socratesman.gameobject.Ghost;
import com.zerokstudios.socratesman.gameobject.Pill;
import com.zerokstudios.socratesman.gameobject.Socrates;
import com.zerokstudios.socratesman.gameobject.SocratesNotFoundException;
import com.zerokstudios.socratesman.gameobject.Wall;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/15/2015.
 *
 * overarching controller of the game, delegates events to game objects
 */
public class GameController {
    private Map map;
    private GameThread gameThread;
    private OI oi;

    /**
     * constructor, create gamethread and oi
     */
    public GameController() {
        gameThread = new GameThread(this);
        oi = new OI();
    }

    /**
     * set the map
     * @param gridDimensions grid dimensions
     * @param panelDimensions dimensions of surface in pixels
     * @param resources pass in activity resources
     * @return true if set, false if not
     */
    public boolean setMap(Vector gridDimensions, Vector panelDimensions, Resources resources) {
        try {
            map = new Map(gridDimensions, panelDimensions, oi, resources); // make sure to get map dimension options from user
            return true;
        } catch (SocratesNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public OI getOi() {
        return oi;
    }

    public void setOi(OI aOi) {
        oi = aOi;
    }

    public Map getMap() {
        return map;
    }

    /**
     * start thread (only can call once)
     */
    public void start() {
        gameThread.setRunning(true);
        gameThread.start();
    }

    /**
     * restart thread
     */
    public void restart() {
        gameThread.setRunning(true);
        gameThread.run();
    }

    /**
     * pause thread
     */
    public void pause() {
        gameThread.setRunning(false);
    }

    /**
     * join the thread
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        gameThread.join();
    }

    /**
     * game loop
     * @param time time elapsed since last tick
     */
    public void tick(int time) {
        Socrates socrates = map.getSocrates();
        System.out.println(socrates.getPosition());
        if (socrates.isDead()) {
            loseGame();
        } else if (map.isScoreWin()) {
            winGame();
        } else if (oi.get(OI.Control.PAUSE)) {
            pause();
        } else {
            EntityGrid<Wall> walls = map.getWalls();
            ArrayList<Ghost> ghosts = map.getGhosts();
            EntityGrid<Pill> pills = map.getPills();
            EntityGrid<Cell> cells = map.getCells();

            //update object properties
            socrates.update();
            for (Ghost ghost : ghosts) {
                ghost.update();
            }
            oi.clear();

            //check object collisions
            if (walls.isColliding(socrates, time)) {
                socrates.onCollide(new CollideEvent(walls, time));
                walls.onCollide(new CollideEvent(socrates, time));
            }
            for (Ghost ghost : ghosts) {
                if (socrates.isColliding(ghost, time)) {
                    socrates.onCollide(new CollideEvent(ghost, time));
                    ghost.onCollide(new CollideEvent(socrates, time));
                }
                if (walls.isColliding(ghost, time)) {
                    ghost.onCollide(new CollideEvent(walls, time));
                    walls.onCollide(new CollideEvent(ghost, time));
                }
            }
            if (pills.isColliding(socrates, time)) {
                socrates.onCollide(new CollideEvent(pills, time));
                pills.onCollide(new CollideEvent(socrates, time));
            }
            if (cells.isColliding(socrates, time)) {
                socrates.onCollide(new CollideEvent(cells, time));
                cells.onCollide(new CollideEvent(socrates, time));
            }

            //object tick
            socrates.tick(time);
            for (Ghost ghost : ghosts) {
                ghost.tick(time);
            }
            cells.tick(time);
        }
    }

    /**
     * terminate loop
     */
    public void endGame() {
        System.out.println("End Game");
        pause();
    }

    /**
     * call upon lose
     */
    public void loseGame() {
        System.out.println("Lose Game");
        endGame();
    }

    /**
     * call upon win
     */
    public void winGame() {
        System.out.println("Win Game");
        endGame();
    }
}
