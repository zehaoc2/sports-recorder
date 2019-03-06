package com.cs498MD.SportsRecorder;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Copied from github
 * For original author, go to: https://github.com/Yalantis/GuillotineMenu-Android
 */
public class App extends Application {
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

    }
}
