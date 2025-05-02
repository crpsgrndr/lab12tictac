package game;

import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final String currentPlayer;
    private final String winner;
    private final boolean draw; 

    private GameState(Cell[] cells, String currentPlayer, String winner, boolean draw) {
        this.cells = cells;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.draw = draw;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        String current = game.getCurrentPlayer() == Player.PLAYER0 ? "X" : "O";
    
        Player winnerPlayer = game.getWinner();
        String win = (winnerPlayer == null) ? null :
                     (winnerPlayer == Player.PLAYER0) ? "X" : "O";
    
        boolean draw = game.isDraw();
        System.out.println("DRAW STATUS: " + draw);
    
        return new GameState(cells, current, win, draw);
    }
    

    public Cell[] getCells() {
        return this.cells;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public String getWinner() {
        return this.winner;
    }

    public boolean getDraw() {
        return this.draw;
    }

    @Override
        public String toString() {
            return """
                {
                "cells": %s,
                "currentPlayer": "%s",
                "winner": %s,
                "draw": %b
                }
                """.formatted(Arrays.toString(this.cells), this.currentPlayer,
                            this.winner == null ? null : "\"" + this.winner + "\"",
                            this.draw);
        }


    private static Cell[] getCells(Game game) {
        Cell[] cells = new Cell[9];
        Board board = game.getBoard();
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                String text = "";
                boolean playable = false;
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0)
                    text = "X";
                else if (player == Player.PLAYER1)
                    text = "O";
                else if (player == null) {
                    playable = true;
                }
                cells[3 * y + x] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }
}