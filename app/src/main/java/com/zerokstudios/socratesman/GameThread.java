package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/15/2015.
 *
 * thread that controls tickrate
 */
public class GameThread extends Thread {
    private static final int TICK_TIME = 1000 / 30; //tickrate
    private final GameController gameController;
    private boolean run;

    /**
     * constructor
     * @param aGameController pass in game controller
     */
    public GameThread(GameController aGameController) {
        run = false;
        gameController = aGameController;
    }

    /**
     * set thread running
     * @param b running
     */
    public void setRunning(boolean b) {
        run = b;
    }

    /**
     * thread loop
     */
    @Override
    public void run() {
        GameClock gameClock = new GameClock(); //gameclock for tickrate
        gameClock.start();
        while (run) {
            synchronized (gameController) { //prevent multiple concurrent ticks
                gameController.tick(gameClock.peekElapsed()); //tick call
            }
            //System.out.println("Game: " + gameClock.peekElapsed());
            int waitTime = TICK_TIME - gameClock.getElapsed(); //check for ms between ticks
            //System.out.println("Game Wait: " + waitTime);
            try {
                sleep((waitTime > 0) ? waitTime : 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
