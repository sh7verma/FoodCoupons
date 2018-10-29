package app.com.foodcoupons.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Applify on 9/21/2016.
 */
public class SemiboldTextView extends TextView {

    public SemiboldTextView(Context context) {
        super(context);
        init();
    }

    public SemiboldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SemiboldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext()
                    .getAssets(), "fonts/Brandon_med.otf");
            setTypeface(typeface);
        }

    }

}