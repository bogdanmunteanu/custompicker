package ro.bogdanmunteanu.custompicker.interfaces;

/**
 * Created by Bogdan on 7/23/2017.
 */

public interface PickerActions {

    /**
     * Checks if the selected position respects some given constraints
     *
     * @param position
     */
    boolean isValid(int position);

    /**
     * Returns the next valid position that respects the constraints
     *
     * @param position
     */
    int getValidPosition(int position);

    /**
     * Get a string result from a list item
     *
     * @param position
     */
    String getResultforItem(int position);
}
