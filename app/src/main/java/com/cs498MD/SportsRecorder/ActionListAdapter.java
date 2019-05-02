package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ActionListAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private ArrayList<String> matchIdList;
    private Context context;
    private Activity activity;

    private String MATCH = "match";

    public ActionListAdapter(ArrayList<String> list, ArrayList<String> matchIdList, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.matchIdList = matchIdList;
    }

    public ActionListAdapter(ArrayList<String> list,  Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;

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
            view = inflater.inflate(R.layout.action_list_layout, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.action_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button delete_btn = (Button)view.findViewById(R.id.action_delete_btn);

        delete_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //delete action
                notifyDataSetChanged();



            }
        });
        return view;
    }


}