package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public abstract class Tile {
    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
    protected int tileCoordinate;
    //protected : can only be accessed by its subclasses
    // final : once this memberfield is set inside the constructor, it can't be changed, can only be set once at construction time

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i<BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        //immutableMap class is part of 3'rd party library guava (from google)
        // not enough to return the empty tile map, someone can clear it or remove tile -> immutable
        return ImmutableMap.copyOf(emptyTileMap);
    }

    private Tile(int coordinate){
        this.tileCoordinate = coordinate;
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece): EMPTY_TILE_CACHE.get(tileCoordinate);
    }

    //polymorphic behavior : common abstract Tile -> empty/ocupied tile

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();


    // could have created those class in other files
    // declared them static final so to be changeable later ?
    // collection.unmodifiable could also be used
    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;
        
        private OccupiedTile(int coordinate, Piece piece){
            super(coordinate);
            this.pieceOnTile = piece;
        }

        @Override
        public String toString() {
            //Black pieces returned as lowercase, white as upper case
            return this.getPiece().getPieceAlliance().isBlack() ? this.getPiece().toString().toLowerCase() : this.getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
