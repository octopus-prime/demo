package com.example.kunde.service.sudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SudokuController {

    private final SudokuService sudokuService;

    @Autowired
    public SudokuController(final SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @PostMapping(value = "/sudoku", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> solve(@RequestBody final String data) {
        try {
            return sudokuService.solve(deserialize(data))
                    .map(board -> ResponseEntity.ok(serialize(board)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private SudokuBoard deserialize(final String data) {
        final SudokuBoard board = new SudokuBoard();
        final String[] lines = data.split(System.lineSeparator());
        if (lines.length != 9)
            throw new IllegalArgumentException("Must be 9 rows");
        for (int r = 0; r < 9; ++r) {
            if (lines[r].length() != 9)
                throw new IllegalArgumentException("Must be 9 cols at row " + (r + 1));
            for (int c = 0; c < 9; ++c) {
                if (!Character.isDigit(lines[r].charAt(c)))
                    throw new IllegalArgumentException("Must be a digit in row " + (r + 1) + " and col " + (c + 1));
                board.set(r, c, lines[r].charAt(c) - '0');
            }
        }
        return board;
    }

    private String serialize(final SudokuBoard board) {
        final StringBuilder builder = new StringBuilder(90);
        for (int r = 0; r < 9; ++r) {
            if (r > 0)
                builder.append('\n');
            for (int c = 0; c < 9; ++c)
                builder.append(board.get(r, c));
        }
        return builder.toString();
    }
}
