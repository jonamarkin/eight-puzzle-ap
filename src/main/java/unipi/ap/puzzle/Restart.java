package unipi.ap.puzzle;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;



// The restart event
public class Restart {
    // Define the restart listeners
    private List<RestartListener> restartListeners = new ArrayList<>();

    //
    public void addRestartListener(RestartListener l) {
        restartListeners.add(l);
    }


    public void removeRestartListener(RestartListener l) {
        restartListeners.remove(l);
    }

    static class RestartEvent extends EventObject {
        // Define the payload
        public ArrayList<Integer> payload;

        // Define the constructor
        public RestartEvent(Object src, ArrayList<Integer> perm) {
            // Call the super constructor
            super(src);
            // Set the payload
            this.payload = perm;
        }
    }

    interface RestartListener extends EventListener {
        // Define the restart method
        void restart(RestartEvent e);
    }
}

