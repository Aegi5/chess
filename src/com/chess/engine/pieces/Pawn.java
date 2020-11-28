package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES= {8, 16};
    private boolean isFirstMove;

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }


    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + this.getPieceAlliance().getDirection() * currentCandidateOffset;
            //skip non valid tile
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 || !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //TODO : for now use MajorMove, Pawn move
                //promotion
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }

            else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[this.piecePosition]) && this.getPieceAlliance().isBlack())
                    || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())  ){
                final int behindCandidateDestinationCoordinate = this.piecePosition + 8*this.getPieceAlliance().getDirection();
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    //TODO: add pawn-specific mvoe
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }


            }
            //pawn promotion upon reaching first or last row

        }
        return null;
    }
}
