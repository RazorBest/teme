package node;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ObservableNode extends Node {

    private final Observable observable = null;

    private final List<Observer> observers;
    private boolean hasChanged = true;

    public ObservableNode(final long id) {
        super(id);

        observers = new ArrayList<Observer>();
    }

    /**
     * Adds an observer to this observable.
     *
     * @param o
     */
    public final void addObserver(final Observer o) {
        observers.add(o);
    }

    /**
     * Deletes an observer from this observable
     *
     * @param o
     */
    public final void deleteObserver(final Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies all the observer if this observable has been marked as changed
     * through the setChanged() method.
     */
    public final void notifyObservers() {
        if (!hasChanged) {
            return;
        }
        for (final Observer o : observers) {
            o.update(observable, this);
        }
        hasChanged = false;
    }

    /**
     * Marks the observable as changed. Next time notifyObservers() is called, the
     * observers will be notified.
     */
    protected void setChanged() {
        hasChanged = true;
    }

}
