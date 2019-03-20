package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyCustomAdapter extends BaseAdapter {
    Activity activity;

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    private String MATCH = "match";

    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

//    SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
//    String matchJson = sharedPreferences.getString(matchId, "");
//
//    Gson gson = new Gson();
//
//        if (matchJson == null || matchJson.equals("")) {
//        match = new Match();
//    } else {
//        match = gson.fromJson(matchJson, Match.class);
//    }

    private Match parseJSON() {
        // TODO: Need to collect matchId from matchArray... change this later
        String matchId = "Match1";

        SharedPreferences sharedPreferences = activity.getSharedPreferences(MATCH, MODE_PRIVATE);
        String matchJson = sharedPreferences.getString(matchId, "");

        Gson gson = new Gson();
        Match match = gson.fromJson(matchJson, Match.class);

//        Log.d("DEBUG", "Something");

        return match;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.match_list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.match_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button shareBtn = (Button)view.findViewById(R.id.share_btn);
        Button viewBtn = (Button)view.findViewById(R.id.view_btn);

        shareBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
//                list.remove(position); //or some other task
//                notifyDataSetChanged();
            }
        });
        viewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameStats.class);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}