package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.Alliance;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance; //black or white
    protected final boolean isFirstMove;

    Piece(final int piecePosition, final Alliance pieceAlliance){
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO : more work here
        this.isFirstMove = false;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    //legal moves
    // return a collection of moves : we can return a set or leave it unspecified : for simplicity we return a list
    public abstract Collection<Move> calculatedLegalMoves(final Board board);

    public Alliance getPieceAlliance() { return this.pieceAlliance;}

}
