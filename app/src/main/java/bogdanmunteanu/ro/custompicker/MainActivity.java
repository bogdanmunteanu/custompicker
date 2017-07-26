package bogdanmunteanu.ro.custompicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import ro.bogdanmunteanu.custompicker.CustomPicker;
import ro.bogdanmunteanu.custompicker.interfaces.PickerViewActions;



public class MainActivity extends AppCompatActivity implements PickerViewActions {

    CustomPicker<String> picker;
    CustomPicker<String> picker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picker = (CustomPicker<String>) findViewById(R.id.customPicker);
        String[] strings= new String[]{"test","test","test","test","test","test"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, strings);
        picker.setAdapter(adapter);
        picker.setHint("Test");

        picker2 = (CustomPicker<String>) findViewById(R.id.customPicker);
        String[] strings2= new String[]{"test","test","test","test","test","test"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, strings2);
        picker2.setAdapter(adapter2);
        picker2.setHint("Test");

    }

    @Override
    public void onPickerExpanded(CustomPicker picker) {

    }
}
