package com.chess.engine.board;

//utility class where to put useful const & static methods
public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final int NUM_TILES  = 64;
    public static final int NUM_TILES_PER_ROW = 8;


    private static boolean[] initColumn(int colNumber) {
        final boolean[] column = new boolean[64];
        do{
            column[colNumber] = true;
            colNumber+=NUM_TILES_PER_ROW;
        }while (colNumber<64);
        return  column;
    }

    private BoardUtils(){
        throw new RuntimeException("You cannot instanciate");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

}
