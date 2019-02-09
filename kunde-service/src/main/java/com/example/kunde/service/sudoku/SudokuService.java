package com.example.kunde.service.sudoku;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SudokuService {

    private static class Field {
        int r;
        int c;
    }

    Optional<SudokuBoard> solve(final SudokuBoard board) {
        return search(board) ? Optional.of(board) : Optional.empty();
    }

    private static boolean search(final SudokuBoard board) {
        return empty(board).map(field -> {
            for (int v = 1; v <= 9; ++v)
                if (board.valid(field.r, field.c, v)) {
                    board.set(field.r, field.c, v);
                    if (search(board))
                        return true;
                    board.reset(field.r, field.c, v);
                }
            return false;
        }).orElse(true);
    }

    private static Optional<Field> empty(final SudokuBoard board) {
        final Field field = new Field();
        for (field.r = 0; field.r < 9; ++field.r) {
            for (field.c = 0; field.c < 9; ++field.c)
                if (board.empty(field.r, field.c))
                    return Optional.of(field);
        }
        return Optional.empty();
    }
}
