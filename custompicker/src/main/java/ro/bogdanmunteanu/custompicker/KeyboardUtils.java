package ro.bogdanmunteanu.custompicker;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Bogdan on 7/23/2017.
 */

public class KeyboardUtils {

    /**
     * Simple method that hides the soft keyboard when it's called
     * @param view needed to get view token
     * @param ctx needed to provide access to input service
     */
    public static void hideKeyboard(View view,Context ctx) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
