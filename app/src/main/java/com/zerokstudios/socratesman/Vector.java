package com.zerokstudios.socratesman;

/**
 * Created by Kevin on 5/12/2015.
 */
public class Vector {
    //exposed yet immutable to allow the creation of ordered pair tuple values
    public final int X;
    public final int Y;

    public Vector(int x, int y) {
        X = x;
        Y = y;
    }

    public Vector difference(Vector other) {
        return new Vector(X - other.X, Y - other.Y);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Vector && ((Vector) o).X == X && ((Vector) o).Y == Y;
    }

    public int toSquareScalar() {
        return X*X + Y*Y;
    }
}
