package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move{
        public MajorMove(final Board board,
                  final Piece movedPiece,
                  final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.getCurrentPlayer().getActivePiece()){
                //TODO : hash code equals for pieces
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePiece()){
                builder.setPiece(piece);
            }
            //move the moved piece
            builder.setPiece(null);
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return  builder.build();
        }
    }
    public static final class AttackMove extends Move{
        final Piece attackedPiece;
        public AttackMove(final Board board,
                   final Piece piece,
                   final int destinationCoordinate,
                   final Piece attackedPiece){
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }
}
