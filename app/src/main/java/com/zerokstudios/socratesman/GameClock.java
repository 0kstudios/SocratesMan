package com.zerokstudios.socratesman;

import java.util.Calendar;

/**
 * Created by Kevin on 5/12/2015.
 * <p>
 * game clock for threading
 */
public class GameClock {
    private long millitime;

    /**
     * default constructor
     */
    public GameClock() {
    }

    /**
     * @return elapsed time since last get
     */
    public int getElapsed() {
        long current = Calendar.getInstance().getTimeInMillis();
        long elapsed = current - millitime;

        millitime = current;

        return (int) elapsed;
    }

    /**
     * peek at the elapsed time without breaking the time
     *
     * @return elapsed time since last get
     */
    public int peekElapsed() {
        return (int) (Calendar.getInstance().getTimeInMillis() - millitime);
    }

    /**
     * start the clock
     */
    public void start() {
        millitime = Calendar.getInstance().getTimeInMillis();
    }
}
