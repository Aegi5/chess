package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final  Collection<Move> opponentMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        //in regards to all possible enemy attack, determine whether the player's in check
        this.isInCheck = !Player.calculateAttacksOnKing(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public static Collection<Move> calculateAttacksOnKing(int kingPosition, Collection<Move> moves){
        final List<Move> attackMove = new ArrayList<>();
        for(final Move move : moves){
            if(kingPosition == move.getDestinationCoordinate()){
                attackMove.add(move);
            }
        }
        return ImmutableList.copyOf(attackMove);

    }

    private King establishKing() {
        for(final Piece piece: getActivePiece()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here ! Invalid board !\n");
    }

    public King getPlayerKing(){
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public abstract Collection<Piece> getActivePiece();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    public boolean isInCheck() {
        return isInCheck;
    }

    public boolean isInCheckmate(){
        return isInCheck & !hasEscapeMoves();
    }


    //TODO : implements those methods
    public boolean isInStalemate(){
        return !isInCheck & !hasEscapeMoves();
    }

    public boolean isCastled(){
        return false;
    }


    public boolean isMoveLegal(Move move){
        return this.legalMoves.contains(move);
    }


    protected boolean hasEscapeMoves(){
        //make all moves in a temporary board, then we look at the result
        // if the player's not in check after a move it's added to a list (and later if the latter isn't empty the the game goes on)
        // otherwise it's GG !
        for(final Move move: legalMoves){
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public MoveTransition makeMove(final Move move){
        if(isMoveLegal(move)){
            //no new board to transitition to since the move is illegal
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttack = Player.calculateAttacksOnKing(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.getCurrentPlayer().getLegalMoves());
        if(!kingAttack.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }
}
