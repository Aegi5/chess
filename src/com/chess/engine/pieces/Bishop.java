package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance pieceAlliance,
                  int piecePosition){
        super(PieceType.BISHOP, pieceAlliance, piecePosition);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        final List<Move>legalMoves = new ArrayList<>();

        for(int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate((candidateDestinationCoordinate))){
                    if(isEighthColumnExclusion(this.piecePosition, candidateCoordinateOffset) &&
                    isEighthColumnExclusion(this.piecePosition, candidateCoordinateOffset)){
                        continue;
                    }

                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if(this.getPieceAlliance() != pieceAlliance){
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, candidateDestinationTile.getPiece()));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    //column exclusions
    private static boolean isFirstColumnExclusion(final int currentPosition, final int offsetCandidate){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (offsetCandidate == -9 || offsetCandidate == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int offsetCandidate){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (offsetCandidate == -7 || offsetCandidate == 9);
    }
    //no need to do the same for row since indexes will be out of boundaries


    @Override
    public Bishop movePiece(Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }


}
