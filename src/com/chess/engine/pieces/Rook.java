package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import javax.naming.ldap.LdapName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece{
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public Rook(final int piecePosition, final Alliance pieceAlliance){
        super(piecePosition, pieceAlliance);
    }


    @Override
    public Collection<Move> calculatedLegalMoves(Board board) {
        final List<Move>legalMoves = new ArrayList<>();

        for(int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate((candidateDestinationCoordinate))){
                    if(isFirstColumnExclusion(this.piecePosition, candidateCoordinateOffset) &&
                    isEighthColumnExclusion(this.piecePosition, candidateDestinationCoordinate)){
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
        return BoardUtils.FIRST_COLUMN[currentPosition] && (offsetCandidate == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int offsetCandidate){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (offsetCandidate == 1);
    }

    //same for rows ?



}
