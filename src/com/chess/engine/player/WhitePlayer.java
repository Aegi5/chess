package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class WhitePlayer extends Player {
    public WhitePlayer(Board board,
                       final Collection<Move> whiteStandardLegalMove,
                       final Collection<Move> blackStandardLegalMove) {
        super(board, whiteStandardLegalMove, blackStandardLegalMove);
    }

    @Override
    public Collection<Piece> getActivePiece() {
        return this.board.getWhilePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
