package com.chess.engine.pieces;

import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class Knight extends Piece{

    private static final int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Knight(final Alliance alliance,
                  int piecePosition){
        super(PieceType.KNIGHT, alliance, piecePosition);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }


    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isHeighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

                //no need to do the same for row since indexes will be out of boundaries

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if(this.getPieceAlliance() != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, candidateDestinationTile.getPiece()));
                    }

                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //columns exclusion
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidatOffset == -17 || candidatOffset == -10 ||
                candidatOffset == 6 || candidatOffset == 15);
    }
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidatOffset == -10 ||  candidatOffset == 6);
    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidatOffset == -6 ||  candidatOffset == 10);
    }

    private static boolean isHeighthColumnExclusion(final int currentPosition, final int candidatOffset){
        //pretend it exists
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidatOffset == -15 || candidatOffset == -6 ||
                candidatOffset == 10 || candidatOffset == 17);
    }
    //row exclusion

}
