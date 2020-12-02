package com.chess.engine;

import com.chess.engine.board.Board;

//driver class, "main"
public class JChess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
