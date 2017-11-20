package com.zonk.fbtest.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Ribbi on 5/25/2017.
 */
public class LoopTextView extends TextView {

    public LoopTextView(Context context) {
        super(context);
        init(context);
    }

    public LoopTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoopTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode())
            setTypeface(TypefaceLoader.get(context, "SouciSansNF.ttf"));
    }
}