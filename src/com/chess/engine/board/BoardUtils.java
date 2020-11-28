package com.chess.engine.board;

//utility class where to put useful const & static methods
public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = initRow(0);
    public static final boolean[] SEVENTH_ROW = initRow(7);


    public static final int NUM_TILES  = 64;
    public static final int NUM_TILES_PER_ROW = 8;
    //NUM_TILES_PER_COLUMN is the same;

    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[64];
        for(int i = 0; i<NUM_TILES_PER_ROW; i++){
            row[rowNumber*8 + i] = true;
        }
        return row;
    }

    //default value in boolean[] are false
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
