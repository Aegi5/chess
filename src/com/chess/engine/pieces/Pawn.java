package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATEs= {8};

    Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }


    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(int currentCandidateOffset: CANDIDATE_MOVE_VECTOR_COORDINATEs){
            currentCandidateOffset *= this.getPieceAlliance().getDirection();
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            //skip non valid tile
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 || !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //TO DO : for now use MajorMove, Pawn move
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
            //pawn promotion upon reaching first or last row

        }
        return null;
    }
}
