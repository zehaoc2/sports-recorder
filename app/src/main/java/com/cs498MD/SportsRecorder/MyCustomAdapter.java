package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.view.menu.ActionMenuItemView;
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
    private ArrayList<String> list;
    private ArrayList<String> matchIdList;
    private Context context;
    private Activity activity;

    private String MATCH = "match";

    public MyCustomAdapter(ArrayList<String> list, ArrayList<String> matchIdList, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.matchIdList = matchIdList;
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

    public String getMatchId(int pos) {
        return matchIdList.get(pos);
    }
    
    public void clear() {
        list.clear();
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
        Button delete_btn = (Button)view.findViewById(R.id.delete_btn);
        Button viewBtn = (Button)view.findViewById(R.id.view_btn);

        delete_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPreferences sharedPreferences = context.getSharedPreferences(MATCH, MODE_PRIVATE);
                                sharedPreferences.edit().remove(getMatchId(position)).apply();

                                list.remove(position); //or some other task
                                matchIdList.remove(position);
                                notifyDataSetChanged();

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this match?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            }
        });
        viewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameStats.class);
                intent.putExtra("matchId", matchIdList.get(position));
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}