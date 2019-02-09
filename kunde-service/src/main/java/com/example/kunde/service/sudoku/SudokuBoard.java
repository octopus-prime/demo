package com.example.kunde.service.sudoku;

class SudokuBoard {

    private final int[][] vals = new int[9][9];
    private final int[] rows = new int[9];
    private final int[] cols = new int[9];
    private final int[][] boxs = new int[3][3];

    SudokuBoard() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j)
                vals[i][j] = 0;
            rows[i] = 0;
            cols[i] = 0;
        }
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                boxs[i][j] = 0;
    }

    void set(final int r, final int c, final int v) {
        vals[r][c] = v;
        final int m = 1 << v;
        rows[r] |= m;
        cols[c] |= m;
        boxs[r / 3][c / 3] |= m;
    }

    void reset(final int r, final int c, final int v) {
        vals[r][c] = 0;
        final int m = 1 << v;
        rows[r] ^= m;
        cols[c] ^= m;
        boxs[r / 3][c / 3] ^= m;
    }

    boolean valid(final int r, final int c, final int v) {
        return ((rows[r] | cols[c] | boxs[r / 3][c / 3]) & (1 << v)) == 0;
    }

    boolean empty(final int r, final int c) {
        return vals[r][c] == 0;
    }

    int get(final int r, final int c) {
        return vals[r][c];
    }
}
