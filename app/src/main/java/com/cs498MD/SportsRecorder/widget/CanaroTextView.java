package com.cs498MD.SportsRecorder.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cs498MD.SportsRecorder.App;

/**
 * Copied from github
 * For original author, go to: https://github.com/Yalantis/GuillotineMenu-Android
 */
@SuppressLint("AppCompatCustomView")
public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.canaroExtraBold);
    }

}