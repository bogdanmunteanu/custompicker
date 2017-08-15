package bogdanmunteanu.ro.custompicker;

/**
 * Created by Bogdan on 8/13/2017.
 */

public class Planet {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                '}';
    }

    public Planet(String name) {
        this.name = name;
    }
}
