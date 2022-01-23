package lib.managers;

import lib.assets.MListener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {

    private List<MListener> listeners;

    public ListenerManager() {
        listeners = new ArrayList<MListener>();
    }

    public void addListener(MListener listener) {
        listeners.add(listener);
    }

    public List<MListener> getListeners() {
        return listeners;
    }

}
