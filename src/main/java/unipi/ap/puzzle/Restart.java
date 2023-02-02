package unipi.ap.puzzle;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;



public class Restart {
    private List<RestartListener> restartListeners = new ArrayList<>();

    public void addRestartListener(RestartListener l) {
        restartListeners.add(l);
    }

    public void removeRestartListener(RestartListener l) {
        restartListeners.remove(l);
    }

    static class RestartEvent extends EventObject {
        public ArrayList<Integer> payload;

        public RestartEvent(Object src, ArrayList<Integer> perm) {
            super(src);
            this.payload = perm;
        }
    }

    interface RestartListener extends EventListener {
        void restart(RestartEvent e);
    }
}

