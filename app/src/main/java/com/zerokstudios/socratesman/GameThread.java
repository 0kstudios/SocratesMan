package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/15/2015.
 */
public class GameThread extends Thread {
    private static final int TICK_TIME = 1000 / 30;
    private final GameController gameController;
    private boolean run;

    public GameThread(GameController aGameController) {
        run = false;
        gameController = aGameController;
    }

    public void setRunning(boolean b) {
        run = b;
    }

    @Override
    public void run() {
        GameClock gameClock = new GameClock();
        gameClock.start();
        while (run) {
            synchronized (gameController) {
                gameController.tick(gameClock.peekElapsed());
            }
            //System.out.println("Game: " + gameClock.peekElapsed());
            int waitTime = TICK_TIME - gameClock.getElapsed();
            //System.out.println("Game Wait: " + waitTime);
            try {
                sleep((waitTime > 0) ? waitTime : 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
