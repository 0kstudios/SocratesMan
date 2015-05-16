package com.zerokstudios.socratesman;

import java.util.Calendar;

/**
 * Created by Kevin on 5/12/2015.
 */
public class GameClock {
    private long millitime;

    public GameClock() {
    }

    public int getElapsed() {
        long current = Calendar.getInstance().getTimeInMillis();
        long elapsed = current - millitime;

        millitime = current;

        return (int) elapsed;
    }

    public int peekElapsed() {
        return (int) (Calendar.getInstance().getTimeInMillis() - millitime);
    }

    public void start() {
        millitime = Calendar.getInstance().getTimeInMillis();
    }
}
