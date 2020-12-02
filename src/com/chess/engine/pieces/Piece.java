package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance; //black or white
    protected final boolean isFirstMove;

    Piece(final Alliance pieceAlliance, final int piecePosition){
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO : more work here
        this.isFirstMove = false;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public int getPiecePosition() {
        return piecePosition;
    }

    //legal moves
    // return a collection of moves : we can return a set or leave it unspecified : for simplicity we return a list
    public abstract Collection<Move> calculatedLegalMoves(final Board board);

    public Alliance getPieceAlliance() { return this.pieceAlliance;}

    public enum PieceType{
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;

        PieceType(String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }



}
