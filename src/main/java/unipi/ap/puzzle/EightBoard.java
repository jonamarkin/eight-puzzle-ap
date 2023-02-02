package unipi.ap.puzzle;

import javax.swing.*;
import java.awt.*;

public class EightBoard extends JFrame{
    private static final int ROWS = 3;
    private static final int COLS = 3;

    private EightController eightController;
    private JButton restartButton;
    private JButton flipButton;

    public EightBoard() {
        initComponents();
        //Create the controller
        eightController.restart();
    }

    private void initComponents() {
        //Create the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set the layout
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        //Set the border
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.LIGHT_GRAY));
        //Set the title
        this.setTitle("8 Puzzle Game");


        ButtonPanel buttonPanel = new ButtonPanel();
        PuzzleGrid board = new PuzzleGrid();

        this.add(board);
        this.add(buttonPanel);

        this.pack();
    }

    class PuzzleGrid extends JPanel{

        public PuzzleGrid() {
            this.setPreferredSize(new Dimension(200,200));
            this.setBackground(Color.DARK_GRAY);


            this.setLayout(new GridLayout(ROWS,COLS,0,0));

            //Create the tiles
            for (int row = 0; row < ROWS; row++) {
                //Create the row
                for (int col = 0; col < COLS; col++) {

                    //Create the tile
                    EightTile tile = new EightTile();
                    //Set the position
                    tile.setPosition((row*COLS)+col);
                    //Set the row and column
                    //Add the tile to the controller
                    eightController.addTile(tile);

                    //Add the tile to the board
                    this.add(tile);
                }
            }

        }
    }

    //Defining the control panel
    class ButtonPanel extends JPanel {
        public ButtonPanel() {


            this.setPreferredSize(new Dimension(100,30));
            this.setBackground(Color.GRAY);
            //this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY));

            //Set the layout
            this.setLayout(new GridLayout( 1, 3, 0, 0));

            //Create the buttons
            eightController = new EightController();
            this.add(eightController);

            //Create the restart button
            restartButton = new JButton("Restart");
            restartButton.setBackground(Color.ORANGE);
            //Add the action listener
            restartButton.addActionListener(e -> eightController.restart());
            this.add(restartButton);

            //Create the flip button
            flipButton = new JButton("Flip");
            flipButton.setBackground(Color.CYAN);

            //Add the action listener
            flipButton.addActionListener(e -> eightController.flip(e));
            this.add(flipButton);

        }
    }


    public static void main(String[] args) {
        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> new EightBoard().setVisible(true));
    }
}
