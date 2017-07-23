package ro.bogdanmunteanu.custompicker.interfaces;

import ro.bogdanmunteanu.custompicker.CustomPicker;

/**
 * Created by Bogdan on 7/23/2017.
 * Interface used to send changes from picker to UI
 */

public interface PickerViewActions {

    /**
     * Used to smooth scroll and see the picker fully
     */
    void onPickerExpanded(CustomPicker picker);
}