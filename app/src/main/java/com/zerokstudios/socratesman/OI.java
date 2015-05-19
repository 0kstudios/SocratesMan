package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/15/2015.
 *
 * oi for interfacing with backend
 */
public class OI {
    /**
     * enumeration of ids
     */
    public enum Control {
        UP, DOWN, LEFT, RIGHT, PAUSE
    }

    private boolean up, down, left, right, pause;

    /**
     * default constructor
     */
    public OI() {
        up = false;
        down = false;
        left = false;
        right = false;
        pause = false;
    }

    /**
     * get a control
     * @param c id
     * @return if pressed
     */
    public boolean get(Control c) {
        boolean b = false;
        switch (c) {
            case UP:
                b = up;
                break;
            case DOWN:
                b = down;
                break;
            case LEFT:
                b = left;
                break;
            case RIGHT:
                b = right;
                break;
            case PAUSE:
                b = pause;
                break;
        }
        return b;
    }

    /**
     * set a control
     * @param c id
     * @param b value
     */
    public void set(Control c, boolean b) {
        switch (c) {
            case UP:
                up = b;
                break;
            case DOWN:
                down = b;
                break;
            case LEFT:
                left = b;
                break;
            case RIGHT:
                right = b;
                break;
            case PAUSE:
                pause = b;
                break;
        }
    }

    /**
     * clear the controls
     */
    public void clear() {
        up = false;
        down = false;
        left = false;
        right = false;
        pause = false;
    }
}
