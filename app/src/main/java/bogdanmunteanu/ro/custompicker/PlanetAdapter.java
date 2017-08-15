package bogdanmunteanu.ro.custompicker;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ro.bogdanmunteanu.custompicker.interfaces.PickerActions;

/**
 * Created by Bogdan on 8/13/2017.
 */

public class PlanetAdapter extends ArrayAdapter<Planet> implements PickerActions {

    public PlanetAdapter(Context context, List<Planet> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Planet planet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView);
        // Populate the data into the template view using the data object
        tvName.setText(planet.getName());
        // Return the completed view to render on screen
        return convertView;
    }


    @Override
    public boolean isValid(int position) {
        return true;
    }

    @Override
    public int getValidPosition(int position) {
        return position;
    }

    @Override
    public String getResultforItem(int position) {
        return getItem(position).getName();
    }
}
