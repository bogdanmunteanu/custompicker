package ro.bogdanmunteanu.custompicker.listeners;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Munteanu on 7/23/2017.
 *
 * Custom listener so it is possible to have both an internal listener for the picker as well as a
 * public one
 */

public class CompositeClickListener implements AdapterView.OnItemClickListener {

    private List<AdapterView.OnItemClickListener> registeredListeners = new ArrayList<AdapterView.OnItemClickListener>();

    public void registerListener (AdapterView.OnItemClickListener listener) {
        registeredListeners.add(listener);
    }

    public void unregisterListener(AdapterView.OnItemClickListener listener) {
        registeredListeners.remove(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for(AdapterView.OnItemClickListener listener:registeredListeners) {
            listener.onItemClick(parent, view, position, id);
        }
    }
}
