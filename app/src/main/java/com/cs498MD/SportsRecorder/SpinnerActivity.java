package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Log.d("SPINNER DEBUG", "GOT HERE");
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
