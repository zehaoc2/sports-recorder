package com.cs498MD.SportsRecorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class SettingActivity extends Activity implements View.OnClickListener {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        findViewById(R.id.back_image_btn).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_image_btn:
                //manage my team
                Log.e("TEST", "back btn clicked");
                startActivity(new Intent(this, MainActivity.class));
                break;


            default:
                break;

        }
    }
}
