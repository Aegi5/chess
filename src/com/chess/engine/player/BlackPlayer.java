package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board,
                       Collection<Move> blackStandardLegalMove,
                       Collection<Move> whiteStandardLegalMove) {
        super(board, blackStandardLegalMove, whiteStandardLegalMove);
    }

    @Override
    public Collection<Piece> getActivePiece() {
        return this.board.getBlackPieces();
    }
}

