package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                    final Collection<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()){
            //whites king side castle
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);

                if(rookTile.isTileOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook()){
                    if(Player.calculateAttacksOnTile(61, opponentLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegalMoves).isEmpty()){
                        kingCastles.add(new KingSideCastleMove(this.board,
                                                                    this.getPlayerKing(),
                                                62,
                                                                    (Rook) rookTile.getPiece(),
                                                                    rookTile.getTilePosition(),
                                                    61));
                    }

                }
            }

            //whites queen side castle
            if (!this.board.getTile(59).isTileOccupied() &&
                    !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                Player.calculateAttacksOnTile(58, opponentLegalMoves).isEmpty() &&
                Player.calculateAttacksOnTile(59, opponentLegalMoves).isEmpty() &&
                rookTile.getPiece().getPieceType().isRook())
                kingCastles.add(new QueenSideCastleMove(this.board,
                                                        this.getPlayerKing(),
                                                        56,
                                                        (Rook) rookTile.getPiece(),
                                                        rookTile.getTilePosition(),
                                                        61));
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
