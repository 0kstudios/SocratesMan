package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/15/2015.
 */
public class GameThread extends Thread {
    private static final long TICK_TIME = 1000 / 60;
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
                gameController.tick(gameClock.getElapsed());
            }
            try {
                sleep(TICK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
