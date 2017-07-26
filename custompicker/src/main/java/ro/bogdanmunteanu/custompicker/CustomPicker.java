package ro.bogdanmunteanu.custompicker;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import ro.bogdanmunteanu.custompicker.interfaces.PickerActions;
import ro.bogdanmunteanu.custompicker.interfaces.PickerViewActions;
import ro.bogdanmunteanu.custompicker.listeners.CompositeClickListener;
import android.support.design.widget.TextInputLayout;

/**
 * Created by Bogdan on 7/23/2017.
 */

public class CustomPicker<T> extends FrameLayout {

    private static final String TAG = CustomPicker.class.getSimpleName();
    private static final Integer ANIMATION_DELAY = 400;
    private EditText resultViewer;
    private ListView itemPicker;
    private PickerActions pickerActions;
    private Animation slideIn, slideOut;
    private boolean isPickerVisible;
    private int heightListView;
    private LinearLayout container;
    private PickerViewActions pickerViewAction;
    private boolean itemWasSelected;
    private CompositeClickListener itemClickListener;
    private AdapterView.OnItemClickListener internalOnItemClickListener;


    private int currentlyCheckedPosition = 0;

    public CustomPicker(Context context) {
        super(context);
        init(context);
    }

    public CustomPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * @param adapter Adapter must implement PickerActions interface because otherwise we can't set
     *                a String result in the editText
     */
    public void setAdapter(ArrayAdapter<T> adapter) {
        itemPicker.setAdapter(adapter);

        if (adapter instanceof PickerActions) {
            pickerActions = (PickerActions) adapter;
            final int validPos = pickerActions.getValidPosition(0);
            itemPicker.setItemChecked(validPos, true);
            itemPicker.setSelection(validPos);
            resultViewer.setText(pickerActions.getResultforItem(validPos));
            adjustListHeight();
        } else
            Log.d(TAG, "Adapter must implement PickerActions interface");

    }

    public void setSelectedItemPosition(int position) {
        itemPicker.setItemChecked(position, true);
        itemPicker.setSelection(position);
        resultViewer.setText(pickerActions.getResultforItem(position));
    }

    public void deselectWithText(String text) {
        itemPicker.setItemChecked(currentlyCheckedPosition, false);
        itemPicker.clearChoices();
        itemPicker.deferNotifyDataSetChanged();
        resultViewer.setText(text);
    }

    public int getSelectedItemPosition() {
        return itemPicker.getCheckedItemPosition();
    }

    public int getSelectedItemPositionOrDefault() {
        if (getSelectedItemPosition() < 0) {
            return 0;
        }
        return getSelectedItemPosition();
    }

    public String getResult() {
        return resultViewer.getText().toString();
    }

    public void setOnItemClickedListener(AdapterView.OnItemClickListener listener) {
        itemClickListener.registerListener(listener);
    }

    public void hidePicker() {
        isPickerVisible = false;
        resultViewer.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_down), null);
        itemPicker.startAnimation(slideOut);
        itemWasSelected = false;
    }

    public boolean isPickerVisible() {
        return isPickerVisible;
    }

    public void setHint(String hint) {
        TextInputLayout resultLayout = (TextInputLayout) findViewById(R.id.result_viewer_layout);
        resultLayout.setHint(hint);
    }

    public Rect getRectForListOnly() {
        Rect r = new Rect();
        itemPicker.getGlobalVisibleRect(r);
        return r;
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addView(inflater.inflate(R.layout.picker_base, this, false));
        pickerViewAction = (PickerViewActions) context;
        container = (LinearLayout) findViewById(R.id.picker_container);
        resultViewer = (EditText) findViewById(R.id.result_viewer);
        itemPicker = (ListView) findViewById(R.id.item_picker);
        resultViewer.setKeyListener(null);
        container.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        createAnimations();
        setupEvents();
        heightListView = itemPicker.getLayoutParams().height;
        itemWasSelected = false;

        /** used because the editText doesn't perform click when gaining focus
         *for first time
         */
        resultViewer.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    hidePicker();
                }
            }
        });
    }

    private void showPicker() {
        ViewGroup.LayoutParams params = itemPicker.getLayoutParams();
        isPickerVisible = true;
        itemPicker.setVisibility(View.VISIBLE);
        params.height = heightListView;
        itemPicker.setLayoutParams(params);
        resultViewer.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_up), null);
        itemPicker.startAnimation(slideIn);
    }

    private void adjustListHeight() {
        if (itemPicker.getCount() < 3) {
            ViewGroup.LayoutParams params = itemPicker.getLayoutParams();
            params.height = params.height / 3 * itemPicker.getCount();
            itemPicker.setLayoutParams(params);
            heightListView = params.height;
        }
    }

    private void setupEvents() {

        internalOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Handler handler = new Handler();
                resultViewer.requestFocus();
                if (pickerActions != null) {
                    itemWasSelected = true;
                    if (pickerActions.isValid(position)) {
                        itemPicker.setItemChecked(position, true);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultViewer.setText(pickerActions.getResultforItem(position));
                                hidePicker();
                            }
                        }, ANIMATION_DELAY);
                    } else {
                        final int validPos = pickerActions.getValidPosition(position);
                        itemPicker.setItemChecked(validPos, true);
                        itemPicker.setSelection(validPos);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultViewer.setText(pickerActions.getResultforItem(validPos));
                                hidePicker();
                            }
                        }, ANIMATION_DELAY);
                    }
                }
            }
        };

        itemClickListener = new CompositeClickListener();
        itemClickListener.registerListener(internalOnItemClickListener);
        itemPicker.setOnItemClickListener(itemClickListener);

        itemPicker.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                view.onTouchEvent(event);
                return true;
            }
        });

        resultViewer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getPointerCount() == 1)
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        resultViewer.requestFocus();
                        //KeyboardUtils.hideKeyboard(findViewById(R.id.picker_container));
                        if (isPickerVisible) {
                            hidePicker();
                        } else {
                            showPicker();
                        }
                    }
                return false;
            }
        });

        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                itemPicker.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pickerViewAction.onPickerExpanded(CustomPicker.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void createAnimations() {
        slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        slideIn.setFillEnabled(true);
        slideIn.setFillAfter(true);

        slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        slideOut.setFillEnabled(true);
        slideOut.setFillAfter(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (itemWasSelected) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
