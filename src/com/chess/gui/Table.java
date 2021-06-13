package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private static String defaultPieceImgPath = "art/simple/";

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    public Table(){

        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar); // somehow not visible ?
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {

        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {

        final JMenu fileMenu = new JMenu();
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Open up that pgn file !");
            };
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    //Visual component that represent the board

    private class BoardPanel extends JPanel{

        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board){
            removeAll();
            for (final TilePanel tilePanel: boardTiles){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
                    // cancel movement ?
                    if(isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if  (isLeftMouseButton(event)){
                        if(sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            // if clicked on empty tile, cancel the action
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            // get legal move with source you've selected and dest selected
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard,
                                    sourceTile.getTileCoordinate(),
                                    destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()){
                                chessBoard = transition.getTransitionBoard();
                                // add the move that was mad to the move log
                            }

                            // clear state
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }

                        //update GUI
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
            validate();
        }

        public void drawTile(final Board board){
            assignTileColor();
            assignTilePieceIcon(board);

            //TODO : tiles to highlights for legal moves

            validate();
            repaint();
        }

        public void assignTileColor(){
            if(BoardUtils.EIGHTH_RANK[this.tileId] ||
                    BoardUtils.SIXTH_RANK[this.tileId] ||
                    BoardUtils.FOURTH_RANK[this.tileId] ||
                    BoardUtils.SECOND_RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SEVENTH_RANK[this.tileId] ||
                    BoardUtils.FIFTH_RANK[this.tileId] ||
                    BoardUtils.THIRD_RANK[this.tileId] ||
                    BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ?  lightTileColor: darkTileColor);
            }
        }
        public void assignTilePieceIcon(final Board board){
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()){
                try{
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImgPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1) +
                                    board.getTile(this.tileId).getPiece().toString() + ".gif")); //building naming convention
                    add(new JLabel(new ImageIcon(image)));

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
