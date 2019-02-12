package com.trile.flagv12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class scoreAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Integer> arrayscore;

    public scoreAdapter(Context context, int layout, ArrayList<Integer> arrayscore) {
        this.context = context;
        this.layout = layout;
        this.arrayscore = arrayscore;
    }

    @Override
    public int getCount() {
        return arrayscore.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate( layout,null);

        TextView row = (TextView) view.findViewById(R.id.rowscore);

        row.setText(arrayscore.get(i)+"");

        return view;
    }
}
