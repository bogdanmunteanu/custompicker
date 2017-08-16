# CustomPicker

A fully customizible spinner for picking results with display in a TextInputLayout

## Gimme that Gradle sweetness, pls?
```
compile 'ro.bogdanmunteanu:custompicker:0.1.9'
```
Maven:
```
<dependency>
  <groupId>ro.bogdanmunteanu</groupId>
  <artifactId>custompicker</artifactId>
  <version>0.1.9</version>
  <type>pom</type>
</dependency>
```
Ivy:
```
<dependency org='ro.bogdanmunteanu' name='custompicker' rev='0.1.9'>
  <artifact name='custompicker' ext='pom' ></artifact>
</dependency>
```
Latest version is 0.1.9

### Does it work on my Grandpa Gary's HTC Dream?

Nope. The minSDK version is API level 11 (Honeycomb).

### How do I use it?

Just put the component into your layout and start customizing it.
```
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ro.bogdanmunteanu.custompicker.CustomPicker
        android:id="@+id/customPicker"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        picker:resultsListHeight="200dp"
        picker:resultsViewTextColor="@color/colorPrimaryDark"
        picker:spinnerBackground="@color/colorPrimaryDark">
    </ro.bogdanmunteanu.custompicker.CustomPicker>

</RelativeLayout>
```
Activity example :
```
The activity must implement PickerViewActions
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
		//here you can put what do you want to happen on picker expanded , thinks like animations , hide other elements or so.
    }
}
```

Adapter example :
```
The adapter must implement PickerActions
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
```
## Custom attributes
```
picker:resultsListHeight -  set the spinner height. The list is scrollable, so don't worry if you have more elements in the spinner. Just make it look good
picker:resultsViewTextColor - if you want to set the text colour without using the android:theme attribute. That works also, don't worry.
picker:spinnerBackground - set the background color of the spinner
```
### Contributions

Feel free to create issues and pull requests.
When creating pull requests, more is more: I'd like to see ten small pull requests separated by feature rather than all those combined into a huge one.

### License
```
CustomPicker library for Android
Copyright (c) 2017 Bogdan Munteanu (http://github.com/bogdanmunteanu).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
