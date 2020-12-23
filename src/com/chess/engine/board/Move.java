package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public static final Move NULL_MOVE = new NullMove();

    public Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getCurrentCoordinate(){
        return this.getMovedPiece().getPiecePosition();
    }


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
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return  builder.build();
    }



    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Not instanciable");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

    public static final class MajorMove extends Move{
        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

    }
    public static class AttackMove extends Move{
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

    public static final class PawnMove extends Move{
        public PawnMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class PawnAttackMove extends AttackMove{
        final Piece attackedPiece;

        public PawnAttackMove(final Board board,
                              final Piece piece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, piece, destinationCoordinate, attackedPiece);
            this.attackedPiece = attackedPiece;
        }
    }

    public static class PawnEnPassantAttackMove extends PawnAttackMove {
        final Piece attackedPiece;

        public PawnEnPassantAttackMove(Board board,
                                   Piece piece,
                                   int destinationCoordinate,
                                   Piece attackedPiece) {
            super(board, piece, destinationCoordinate, attackedPiece);
            this.attackedPiece = attackedPiece;
        }
    }

    public static final class  PawnJump extends Move{
        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class CastleMove extends Move{
        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

    }


    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }
    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class NullMove extends Move{
        public NullMove(){
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute the null move ! ");
        }
    }



        public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }
}
