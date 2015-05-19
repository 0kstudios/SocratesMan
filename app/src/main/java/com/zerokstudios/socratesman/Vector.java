package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/12/2015.
 * <p>
 * tuple value
 */
public class Vector {
    //exposed yet immutable to allow easy access to ordered pair tuple values
    public final int X;
    public final int Y;

    /**
     * constructor
     *
     * @param x
     * @param y
     */
    public Vector(int x, int y) {
        X = x;
        Y = y;
    }

    /**
     * @param other
     * @return difference vector
     */
    public Vector difference(Vector other) {
        return new Vector(X - other.X, Y - other.Y);
    }

    /**
     * @param other
     * @return sum vector
     */
    public Vector sum(Vector other) {
        return new Vector(X + other.X, Y + other.Y);
    }

    /**
     * @param factor
     * @return scaled vector by magnitude
     */
    public Vector scale(double factor) {
        return new Vector((int) (X * factor), (int) (Y * factor));
    }

    /**
     * @return absolute value of vector components
     */
    public Vector abs() {
        return new Vector(Math.abs(X), Math.abs(Y));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Vector && ((Vector) o).X == X && ((Vector) o).Y == Y;
    }

    /**
     * @return magnitude of vector
     */
    public int toSquareScalar() {
        return X * X + Y * Y;
    }

    @Override
    public String toString() {
        return "<" + X + "," + Y + ">";
    }
}
