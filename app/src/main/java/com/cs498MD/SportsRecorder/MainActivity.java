package com.cs498MD.SportsRecorder;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<String> matchNameArray = new ArrayList<>();
    private ArrayList<String> matchIdArray = new ArrayList<>();
    private MyCustomAdapter adapter;

    //bottom sheet
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayoutBSheet;
    private ToggleButton tbUpDown;
    private ListView listView;
    private TextView txtCantante, txtCancion;
    private ContentLoadingProgressBar progbar;

    private Button createBtn;
    private EditText userInputMatchName;
    private EditText userInputKidName;

    private String MATCH = "match";

    private boolean isNumeric(String num) {
        return num.matches("[0-9]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        init_bottomsheet();

        tbUpDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    tbUpDown.setChecked(true);
                }else if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    tbUpDown.setChecked(false);
                }
            }

            @Override
            public void onSlide(View view, float v) {

            }
        });

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Matches");

        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        Gson gson = new Gson();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String matchId = entry.getKey();

            if (!isNumeric(matchId)) {
                continue;
            }

            String matchJson = sharedPreferences.getString(matchId, "");
            Match match = gson.fromJson(matchJson, Match.class);

            matchNameArray.add(match.getName());
            matchIdArray.add(matchId);
        }

        adapter = new MyCustomAdapter(matchNameArray, matchIdArray, this, MainActivity.this);

        ListView listView = (ListView) findViewById(R.id.matchList);
        listView.setEmptyView(findViewById(R.id.noMatches));
        listView.setAdapter(adapter);

        createBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("match", MODE_PRIVATE);
        int matchCount = sharedPreferences.getInt("matchCount", -1);

        matchCount++;
        String matchName = "Match " + matchCount;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("matchCount", matchCount);
        editor.putString("matchId", matchName);
        editor.apply();



        if(v.getId() == R.id.btnCreate){

            Log.e("TEST_SHEET", "Create Match Clicked");
            Log.e("TEST_SHEET", "Match: " +userInputMatchName.getText().toString());
            Log.e("TEST_SHEET", "Kid: " + userInputKidName.getText().toString());

            //TODO: Change match info details. But right now can be used for testing purpose
            matchNameArray.add(matchName);

            matchIdArray.add("" + matchCount);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(this, InputActivity.class);
            intent.putExtra("matchId", Integer.toString(matchCount));
            startActivity(intent);
        }
    }



    //bottom sheet
    private void init_bottomsheet() {
        this.linearLayoutBSheet = findViewById(R.id.bottomSheet);
        this.bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBSheet);
        this.tbUpDown = findViewById(R.id.toggleButton);

        this.txtCantante = findViewById(R.id.txtCantante);
        this.txtCancion = findViewById(R.id.txtCancion);
        this.progbar = findViewById(R.id.progbar);

        this.createBtn = findViewById(R.id.btnCreate);
        this.userInputKidName = findViewById(R.id.kidNameText);
        this.userInputMatchName = findViewById(R.id.matchNameText);

    }




    private SimpleAdapter getAdapterListViewCT(ArrayList<Map<String, Object>> lista) {
        return new SimpleAdapter(this, lista,
                android.R.layout.simple_list_item_2, new String[]{"Cantante", "Titulo"},
                new int[]{android.R.id.text1, android.R.id.text2}) {

            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView txtNombre = view.findViewById(android.R.id.text1);
                txtNombre.setTypeface(Typeface.DEFAULT_BOLD);

                TextView txtCorreo = view.findViewById(android.R.id.text2);
                txtCorreo.setTextColor(Color.DKGRAY);

                return view;
            }

        };
    }

    private int getRandom() {
        return (int) Math.floor(Math.random() * 100);
    }
}