package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

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

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + this.movedPiece.getPiecePosition();
        result = (prime * result) + this.hashCode();

        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof  Move)){
            return false;
        }
        final Move otherMove = (Move) other;
        return this.getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                this.getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public int getCurrentCoordinate(){
        return this.getMovedPiece().getPiecePosition();
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
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
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof Move)){
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(other) && getAttackedPiece().equals(((AttackMove) other).getAttackedPiece());

        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack(){
            return true;
        }

        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
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

        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.getCurrentPlayer().getActivePiece()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePiece()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static abstract class CastleMove extends Move{

        protected  final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleDestination;

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final  int castleRookStart,
                          final  int castleDestination){
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleDestination = castleDestination;
        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove(){
            return true;
        }

        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.getCurrentPlayer().getActivePiece()){
                if(!this.movedPiece.equals(piece) && !this.movedPiece.equals(castleRook)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePiece()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this)); //move king
            builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleDestination)); // move rook
            //TODO : look into the first move in normal pieces (use it for Rook in this context)
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }


    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate,
                                  final Rook castleRook,
                                  final  int castleRookStart,
                                  final  int castleDestination){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleDestination);

        }
        @Override
        public String toString(){
            return  "0-0";
        }
    }
    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final  int castleRookStart,
                                   final  int castleDestination){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleDestination);
        }

        @Override
        public String toString(){
            return  "0-0-0";
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

}
