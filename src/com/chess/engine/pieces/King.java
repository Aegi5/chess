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

public class King extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES ={-9,-8,-7, -1, 1, 7, 8, 9};

    public King(int piecePosition, Alliance pieceAlliance) {
        super(pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        final List<Move> legalMoves=  new ArrayList<>();


        for(int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
            final int candidateDestinationCoordinate =  this.piecePosition + currentCandidateOffset;

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isHeighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }
                if (!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if(this.getPieceAlliance() != pieceAlliance){
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, candidateDestinationTile.getPiece()));
                    }

                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidatOffset == -9 || candidatOffset == -1 ||
                candidatOffset == 7 );
    }

    private static boolean isHeighthColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidatOffset == 9 || candidatOffset == 1 ||
                candidatOffset == 7
        );
    }



}
