package com.zonk.fbtest.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * Created by Ribbi on 5/25/2017.
 */
public class BariolBoldTextView extends TextView {

    public BariolBoldTextView(Context context) {
        super(context);
        init(context);
    }

    public BariolBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BariolBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode())
            setTypeface(TypefaceLoader.get(context, "AlegreyaSans_Regular.ttf"));
    }
}