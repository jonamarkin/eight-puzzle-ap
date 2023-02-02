package unipi.ap.puzzle;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// The controller of the puzzle
public class EightController extends JLabel implements VetoableChangeListener {

    // Define the restart listeners
    private ArrayList<Restart.RestartListener> restartListeners = new ArrayList<>();
    // Define the tiles
    private ArrayList<EightTile> tiles = new ArrayList<>();
    // Define the current configuration
    private ArrayList<Integer> currentConfiguration;
    // Define the moves
    private HashMap<String, Direction> moves;


    //Define the rows and columns of the puzzle
    private static final int ROWS = 3;

    private static final int COLS = 3;





    public EightController() {


        this.setText("START");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        //this.setFont(new Font("Arial", Font.PLAIN, 15));
        this.setForeground(Color.WHITE);

        moves = new HashMap<String, Direction>();
        // Define the moves
        // left, right, up, down
        moves.put("left", new Direction(-1, 0));
        moves.put("right", new Direction(1, 0));
        moves.put("up", new Direction(0, -1));
        moves.put("down", new Direction(0, 1));
    }

    public void addTile(EightTile t) {

        // Add the tile to the tiles
        tiles.add(t);


        // Add the veto listener
        t.addVetoableChangeListener(this);

        // Add the property change listener
        this.addPropertyChangeListener(t);
        // Add the restart listener
        this.addEightRestartListener(t);
    }

    // Veto implementation
    public void vetoableChange(PropertyChangeEvent e) {
        // Check if the event is a veto move event
        if (e.getPropertyName() == "VETO_MOVE_EVENT" || e.getPropagationId() == this) {

            // Get the clicked tile and the hole tile
            EightTile clicked_tile = (EightTile) e.getSource();
            EightTile hole_tile = tiles.get(currentConfiguration.indexOf(e.getNewValue()));


            // Get the position of the clicked tile and the hole tile
            int c_pos = clicked_tile.getPosition();
            int h_pos = hole_tile.getPosition();

            // Check if the move is allowed
            HashMap<String, Direction> allowed_moves = new HashMap<>();
            // Add all the moves
            allowed_moves.putAll(moves);

            // Check if the tile is on the edge
            if (c_pos < COLS * 1)
                allowed_moves.remove("up");
            // Check if the tile is on the edge
            if (c_pos >= COLS * (ROWS - 1))
                allowed_moves.remove("down");

            // Check if the tile is on the edge
            if (c_pos % COLS == 0)
                allowed_moves.remove("left");
            if (c_pos % COLS == COLS - 1)
                allowed_moves.remove("right");

            // Check if the move is allowed
            for (Direction d : allowed_moves.values()) {
                int possible_move_p = c_pos + d.x + d.y * COLS;
                if (h_pos == possible_move_p) {

                    // Swap the tiles
                    Collections.swap(currentConfiguration, currentConfiguration.indexOf(e.getOldValue()),currentConfiguration.indexOf(e.getNewValue()));

                    this.firePropertyChange("LABEL_UPDATE_EVENT", e.getNewValue(), e.getOldValue());

                    this.setText("OK");

                    //if the move has been done by the player
                    if(e.getPropagationId() != this){
                        // Check if the player won
                        for(EightTile tile : tiles){
                            // If the tile is not the hole and it is not green -> player did not win
                            if(tile.label!=9 && tile.getBackground()!=Color.GREEN){ //If all tiles are green , 9 not included -> player won.
                                return;
                            }
                        }
                    }
                    // If the player won
                    this.setText("WIN");
                    return;
                }
            }
            // If the move is not allowed
            this.setText("KO");
        }
    }



    public void restart() {
        this.setText("RESTARTING");
        // Reset the cache


        currentConfiguration = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

          // Randomly swap tiles
        restartListeners.forEach(
                // For each listener
                (EightRestartListener) -> EightRestartListener.restart(new Restart.RestartEvent(this, currentConfiguration)));

        // Randomly swap tiles
        for (int z = 0; z < 100000; z++) {

            // Randomly select a tile
            int simulate = ThreadLocalRandom.current().nextInt(0, 9);

            // Randomly select a direction
            PropertyChangeEvent ev = new PropertyChangeEvent(tiles.get(simulate), "VETO_MODE_EVENT", simulate, 9);
            ev.setPropagationId(this);
            vetoableChange(ev);
        }


        this.setText("RESET DONE");
    }

    public void flip(java.awt.event.ActionEvent e) {
        this.setText("FLIPPED");

        // Swap the tiles
            Collections.swap(currentConfiguration, currentConfiguration.indexOf(1),currentConfiguration.indexOf(2));
            restartListeners.forEach(
                    (EightRestartListener) -> EightRestartListener.restart(new Restart.RestartEvent(this, currentConfiguration)));

    }

    public synchronized void addEightRestartListener(Restart.RestartListener l) {
        // Add the listener
        restartListeners.add(l);
    }

    public synchronized void removeEightRestartListener(Restart.RestartListener l) {
        // Remove the listener
        restartListeners.remove(l);
    }
}
