package bogdanmunteanu.ro.custompicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ro.bogdanmunteanu.custompicker.CustomPicker;
import ro.bogdanmunteanu.custompicker.interfaces.PickerViewActions;



public class MainActivity extends AppCompatActivity implements PickerViewActions {

    CustomPicker<Planet> picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picker = (CustomPicker<Planet>) findViewById(R.id.customPicker);
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet("Earth"));
        planets.add(new Planet("Jupiter"));
        planets.add(new Planet("Mars"));
        planets.add(new Planet("Saturn"));
        planets.add(new Planet("Pluto"));
        PlanetAdapter planetAdapter = new PlanetAdapter(this.getApplicationContext(),planets);
        picker.setAdapter(planetAdapter);
        picker.setHint("Test");
    }

    @Override
    public void onPickerExpanded(CustomPicker picker) {

    }
}
