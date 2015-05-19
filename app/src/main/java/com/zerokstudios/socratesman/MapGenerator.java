package com.zerokstudios.socratesman;

import java.util.Random;

/**
 * Created by Sherman on 5/16/2015.
 * <p/>
 * map generator
 */
public class MapGenerator {

    public static int[][] generate() {
        Tetris ye = new Tetris();
        ye.generate();


        //copies onto new map
        int[][] map = new int[16][17];
        for (int i = 13; i >= 0; i--) {
            for (int x = 0; x <= 14; x++) {
                map[i + 1][x + 1] = ye.board[i][x];
            }
        }


        for (int i = 1; i < map.length - 1; i++) //puts 1 on border of box
            map[i][1] = 1;

        for (int i = 1; i < map.length - 1; i++)
            map[i][15] = 1;
        for (int i = 1; i < map[0].length - 1; i++)
            map[1][i] = 1;


        //removes all 2's and replaces with 0's
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2)
                    map[i][j] = 0;
            }
        }

        //sets some specified spots for important characters
        map[1][1] = 9; //pac man spot
        map[14][1] = 5; //ghost1
        map[14][15] = 5; //ghost2
        return map;
    }


    /**
     * This class handles generating pac man maps
     *
     * @author Sherman Luo Period 4
     */
    public static class Tetris {
        public int[][] board = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}}; // main board

        /**
         * Constructor has nothing
         */
        public Tetris() {
        }

        private double countPercent() {
            double count = 0;
            double max = 14 * 15;
            for (int i = 0; i < board.length; i++) {
                for (int k = 0; k < board[0].length; k++) {
                    if (board[i][k] == 0)
                        count++;
                }
            }
            return count / max;
        }

        /**
         * Check if 1 or 2 occupies top row
         *
         * @return true if found, false if not
         */
        private boolean checkTop() {
            for (int x : board[0]) {
                if (x != 0)
                    return true;
            }
            return false;
        }

        /**
         * Method returns true or false based on whether a block can keep falling or
         * it hits another block, or the floor
         *
         * @param position - x position
         * @param length   - length of block
         * @param height   - y position
         * @param z        - block type
         * @return - true if another block has not been touched
         */
        private boolean checkOne(int position, int length, int height, int z) {
            int i = height;
            int x = position;

            if (z == 1 || z == 2 || z == 3) {
                for (int j = 0; j < length; j++) {
                    if (checkBounds(i, x)) {
                        if (board[i][x] == 1 || board[i][x] == 2)
                            return true;
                    }
                    x++;
                }

            }

            if (z == 4) {
                for (int j = 0; j < 4; j++) {
                    if (checkBounds(i, x + 3)) {
                        if (board[i][x + 3] == 1 || board[i][x + 3] == 2)
                            return true;
                    }
                    x++;
                }
                x = position;
                for (int j = 0; j < 4; j++) {
                    if (checkBounds(i - 4, x)) {
                        if (board[i - 4][x] == 1 || board[i - 4][x] == 2)
                            return true;
                    }
                    x++;

                }
                x = position;
                for (int j = 0; j < 4; j++) {
                    if (checkBounds(i - 4, x + 6)) {
                        if (board[i - 4][x + 6] == 1 || board[i - 4][x + 6] == 2)
                            return true;
                    }
                    x++;

                }
            }

            if (z == 5) {
                for (int j = 0; j < 4; j++) {
                    if (checkBounds(i, x + 2)) {
                        if (board[i][x + 2] == 1 || board[i][x + 2] == 2)
                            return true;
                    }
                    x++;
                }
                x = position;
                for (int j = 0; j < 3; j++) {
                    if (checkBounds(i - 3, x)) {
                        if (board[i - 3][x] == 1 || board[i - 3][x] == 2)
                            return true;
                    }
                    x++;
                }
            }

            return false;
        }

        /**
         * copies the dropped block starting from left corner onto board
         *
         * @param row  - starting row
         * @param col  - starting col
         * @param swag - array to be copied onto board
         */
        public void copy(int row, int col, int[][] swag) {
            int x = col;
            for (int i = 0; i < swag.length; i++) {
                for (int j = 0; j < swag[0].length; j++) {

                    if (checkBounds(row, col) && swag[swag.length - i - 1][j] != 0) {
                        board[row][col] = swag[swag.length - i - 1][j];
                    }
                    col++;
                }
                col = x;
                row--;
            }

        }

        /**
         * makes sure stuff is in bounds
         *
         * @param row - row to be checked
         * @param col - col to be checked
         * @return - true whether in bounds or not
         */
        private boolean checkBounds(int row, int col) {
            if (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
                return true;
            } else
                return false;
        }

        /**
         * The generate process that alters the board variable to create a random
         * map
         *
         * @return - generated 2D Array
         */
        public int[][] generate() {
            int count = 0;
            while (countPercent() > .4) {
                if (count == 15)
                    break;
                Random rand = new Random();
                int x = rand.nextInt(5) + 1;
                int[][] ye = null;
                if (x == 1)
                    ye = Shape.T;
                if (x == 2)
                    ye = Shape.L;
                if (x == 3)
                    ye = Shape.BOX;
                if (x == 4)
                    ye = Shape.UNDERT;
                if (x == 5)
                    ye = Shape.UPL;
                int y = (rand.nextInt(15));
                int i = 0;
                while ((!checkOne(y, ye[0].length, i, x))) {
                    i++;
                }
                copy(i, y, ye);
                count++;

            }
            return board;
        }

        /**
         * This class has several static shapes to be used in tetris
         *
         * @author Sherman Luo
         */
        private static class Shape {
            public static final int[][] T = {{0, 0, 0, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0, 0}, {0, 0, 0, 1, 2, 1, 0, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0, 0}, {1, 1, 1, 1, 2, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}};
            public static final int[][] L = {{0, 0, 1, 1, 1}, {0, 0, 1, 2, 1},
                    {0, 0, 1, 2, 1}, {1, 1, 1, 2, 1}, {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1}};
            public static final int[][] BOX = {{1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 1}, {1, 2, 2, 2, 1}, {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1}};
            public static final int[][] UNDERT = {{1, 1, 1, 1, 2, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 2, 1, 0, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0, 0}, {0, 0, 0, 1, 1, 1, 0, 0, 0}};
            public static final int[][] UPL = {{1, 1, 1, 2, 1},
                    {1, 2, 2, 2, 1}, {1, 1, 1, 1, 1}, {0, 0, 1, 2, 1},
                    {0, 0, 1, 2, 1}, {0, 0, 1, 1, 1}};
        }

    }

}
