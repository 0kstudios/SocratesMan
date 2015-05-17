package com.zerokstudios.socratesman;

import java.util.Random;

/**
 * Created by Kevin on 5/16/2015.
 */
public class MapGenerator {
    public static int[][] generate() {
        Tetris ye = new Tetris();
        ye.generate();


        int[][] map = new int[27][29];
        for (int i = 13; i >= 0; i--) {
            for (int x = 0; x < 15; x++) {
                map[i + 13][x] = ye.board[i][x];
            }
        }

        for (int q = 13; q < 27; q++) {
            for (int w = 13; w >= 0; w--) {
                map[q][28 - w] = map[q][w];
            }
        }

        for (int z = 26; z >= 0; z--) {
            for (int c = 0; c < 29; c++) {
                map[26 - z][c] = map[z][c];
            }
        }

        for (int i = 0; i < map.length; i++)
            map[i][0] = 1;
        for (int m = 0; m < map.length; m++)
            map[m][map[0].length - 1] = 1;


        int[][] map2 = new int[29][31];
        int t = 1;
        int y = 27;
        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 29; j++) {
                map2[y][t] = map[26 - i][j];
                t++;
            }
            t = 1;
            y--;
        }

        for (int i = 0; i < map2.length; i++) {
            for (int j = 0; j < map2[0].length; j++) {
                if (map2[i][j] == 2)
                    map2[i][j] = 0;
            }
        }

        map2[15][15] = 2;
        map2[15][16] = 0;
        map2[15][17] = 0;
        map2[14][17] = 2;
        map2[13][17] = 0;
        map2[13][16] = 0;
        map2[13][15] = 2;
        map2[13][14] = 0;
        map2[13][13] = 0;
        map2[14][13] = 2;
        map2[14][14] = 5;
        map2[14][15] = 2;
        map2[14][16] = 5;
        map2[15][13] = 0;
        map2[15][14] = 0;
        map2[1][1] = 9;

        if (map2[16][15] != 1 && map2[14][18] != 1 && map2[12][15] != 1 && map2[14][12] != 1) {
            int x = 15;
            while (map2[14][x + 2] != 1) {
                map2[14][x + 2] = 2;
                map2[14][30 - x - 2] = 2;
                x++;
            }
        }

        return map2;
    }

    public static class Tetris {
        public int[][] board =
                {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

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

        //true if 1 or 2 has been found
        //false otherwise
        private boolean checkTop() {
            for (int x : board[0]) {
                if (x != 0)
                    return true;
            }
            return false;
        }

        //true if 1 was found
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

        //copies the dropped block starting from left corner
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

        //makes sure stuff is in bounds
        private boolean checkBounds(int row, int col) {
            if (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
                return true;
            } else return false;
        }

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

        private static class Shape {
            public static final int[][] T =
                    {{0, 0, 0, 1, 1, 1, 0, 0, 0},
                            {0, 0, 0, 1, 2, 1, 0, 0, 0},
                            {0, 0, 0, 1, 2, 1, 0, 0, 0},
                            {0, 0, 0, 1, 2, 1, 0, 0, 0},
                            {1, 1, 1, 1, 2, 1, 1, 1, 1},
                            {1, 2, 2, 2, 2, 2, 2, 2, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1, 1}};
            public static final int[][] L =
                    {{0, 0, 1, 1, 1},
                            {0, 0, 1, 2, 1},
                            {0, 0, 1, 2, 1},
                            {1, 1, 1, 2, 1},
                            {1, 2, 2, 2, 1},
                            {1, 1, 1, 1, 1}};
            public static final int[][] BOX =
                    {{1, 1, 1, 1, 1},
                            {1, 2, 2, 2, 1},
                            {1, 2, 2, 2, 1},
                            {1, 2, 2, 2, 1},
                            {1, 1, 1, 1, 1}};
            public static final int[][] UNDERT =
                    {{1, 1, 1, 1, 2, 1, 1, 1, 1},
                            {1, 2, 2, 2, 2, 2, 2, 2, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1, 1},
                            {0, 0, 0, 1, 1, 1, 0, 0, 0},
                            {0, 0, 0, 1, 2, 1, 0, 0, 0},
                            {0, 0, 0, 1, 2, 1, 0, 0, 0},
                            {0, 0, 0, 1, 1, 1, 0, 0, 0}
                    };
            public static final int[][] UPL =
                    {{1, 1, 1, 2, 1},
                            {1, 2, 2, 2, 1},
                            {1, 1, 1, 1, 1},
                            {0, 0, 1, 2, 1},
                            {0, 0, 1, 2, 1},
                            {0, 0, 1, 1, 1}
                    };
        }

    }


}
