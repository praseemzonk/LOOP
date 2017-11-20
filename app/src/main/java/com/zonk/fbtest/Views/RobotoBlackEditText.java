package com.zonk.fbtest.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class RobotoBlackEditText extends EditText {
    public RobotoBlackEditText(Context context) {
        super(context);
        init(context);
    }

    public RobotoBlackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RobotoBlackEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode())
            setTypeface(TypefaceLoader.get(context, "AlegreyaSans_Bold.ttf"));
    }
}


