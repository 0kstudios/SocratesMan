package com.zerokstudios.socratesman;

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
 */
public class GameController {
    private Map map;
    private GameThread gameThread;
    private OI oi;

    public GameController() {
        gameThread = new GameThread(this);
        oi = new OI();
    }

    public boolean setMap(Vector gridDimensions, Vector panelDimensions) {
        try {
            map = new Map(gridDimensions, panelDimensions, oi); // make sure to get map dimension options from user
            return true;
        } catch (SocratesNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setOi(OI aOi) {
        oi = aOi;
    }

    public OI getOi() {
        return oi;
    }

    public Map getMap() {
        return map;
    }

    public void start() {
        gameThread.setRunning(true);
        gameThread.start();
    }

    public void pause() {
        gameThread.setRunning(false);
    }

    public void join() throws InterruptedException {
        gameThread.join();
    }

    public void tick(int time) {
        Socrates socrates = map.getSocrates();
        if (socrates.isDead()) {
            endGame();
        } else if (oi.get(OI.Control.PAUSE)) {
            pause();
        } else {
            EntityGrid<Wall> walls = map.getWalls();
            ArrayList<Ghost> ghosts = map.getGhosts();
            EntityGrid<Pill> pills = map.getPills();

            socrates.update();
            oi.clear();

            if (walls.isColliding(socrates, time)) {
                socrates.onCollide(new CollideEvent(walls));
                walls.onCollide(new CollideEvent(socrates));
            }
            for (Ghost ghost : ghosts) {
                if (socrates.isColliding(ghost, time)) {
                    socrates.onCollide(new CollideEvent(ghost));
                    ghost.onCollide(new CollideEvent(socrates));
                }
                if (walls.isColliding(ghost, time)) {
                    ghost.onCollide(new CollideEvent(walls));
                    walls.onCollide(new CollideEvent(ghost));
                }
            }
            if (pills.isColliding(socrates, time)) {
                socrates.onCollide(new CollideEvent(pills));
                pills.onCollide(new CollideEvent(socrates));
            }

            socrates.tick(time);
            for (Ghost ghost : ghosts) {
                ghost.tick(time);
            }
        }
    }

    public void endGame() {

    }
}
