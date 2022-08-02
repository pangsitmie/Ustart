package com.example.ustart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ustart.R;

import java.util.List;

public class SwipeAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> list;

    public SwipeAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 10;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if(convertView == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_koloda, viewGroup, false);
        }
        else
        {
            view = convertView;
        }
        return  view;
    }
}
