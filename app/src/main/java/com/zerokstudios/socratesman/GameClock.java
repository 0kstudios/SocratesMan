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

    public void start() {
        millitime = Calendar.getInstance().getTimeInMillis();
    }
}
