package unipi.ap.puzzle;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

//This class represents a tile of the puzzle
public class EightTile  extends JButton implements PropertyChangeListener, Restart.RestartListener {

    int position;
    boolean initPosition;

    int label;
    public EightTile(){
        super();
        this.setBorder( new LineBorder(Color.DARK_GRAY) );

        this.addActionListener(e -> {
            //When the button is clicked, fire a VETO_MOVE_EVENT
            try {
                //The propagation id is the tile itself
                super.fireVetoableChange("VETO_MOVE_EVENT",this.label,9); } catch (PropertyVetoException ev){}});
        initPosition = false;
    }

    public int getPosition(){
        //Return the position of the tile
        return this.position;
    }

    public void setPosition(int p){
        //Set the position of the tile
        if (!initPosition){
            //If the position is not set, set it
            this.position = p;
            initPosition =true;
        }
    }

    public String getLabel(){
        //Return the label of the tile
        return Integer.toString(label);
    }

    public void setLabel(int lab){
        //Set the label of the tile
        this.label = lab;
        this.setText(this.getLabel());
        if(label==9){
            //If the label is 9, the tile is the hole
            this.setText("");
            this.setEnabled(false);
            this.setBackground(Color.GRAY);
        }
        else{
            //If the label is not 9, the tile is not the hole
            if(position==label-1) this.setBackground(Color.GREEN);
            else this.setBackground(Color.YELLOW);
            this.setEnabled(true);
        }
    }

    @Override
    public void restart(Restart.RestartEvent e) {
        //Set the label of the tile to the value of the new configuration
        this.setLabel(e.payload.get(position));
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        //If the event is a label update event and the label of the tile is equal to the new value of the event
        if(e.getPropertyName()=="LABEL_UPDATE_EVENT"){

            //If the event is a label update event and the label of the tile is equal to the new value of the event
            if((Integer)e.getNewValue()==this.label){
                //Set the label of the tile to the old value of the event
                setLabel((Integer)e.getOldValue());
            }
            //If the event is a label update event and the label of the tile is equal to the old value of the event
            else if((Integer)e.getOldValue()==this.label){
                setLabel((Integer)e.getNewValue());
            }

        }
    }
}
