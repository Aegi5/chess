package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance; //black or white
    protected final boolean isFirstMove;
    private final int cachedHashCode = computeHashCode();

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

    public abstract Piece movePiece(Move move);


    public enum PieceType{
        PAWN("P"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };



        private final String pieceName;

        PieceType(String pieceName){
            this.pieceName = pieceName;
        }

        public abstract boolean isKing();
        public abstract boolean isRook();

        @Override
        public String toString() {
            return this.pieceName;
        }


    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(final Object o) {
        //reference equality
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //can be casted as a Piece since we've passed the first check
        final Piece piece = (Piece) o;
        return piecePosition == piece.piecePosition && isFirstMove == piece.isFirstMove &&
                pieceType == piece.pieceType && pieceAlliance == piece.pieceAlliance;
    }

    @Override
    public int hashCode() {
        return  this.cachedHashCode;
    }

    private int computeHashCode() {
        //personnalized hashcoed
        int result = pieceType.hashCode();
        result = result * 31 + pieceAlliance.hashCode();
        result = result * 31 + piecePosition;
        result = result * 31 * (isFirstMove ? 1: 0);
        return result;
    }

}
