package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance; //black or white
    protected final boolean isFirstMove;

    Piece(final PieceType pieceType,
          final Alliance pieceAlliance,
          final int piecePosition){
        this.pieceType = pieceType;
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
        PAWN("P"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final String pieceName;

        PieceType(String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();

    }

    public PieceType getPieceType() {
        return pieceType;
    }

}
